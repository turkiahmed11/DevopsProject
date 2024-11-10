package tn.esprit.spring.kaddem.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Slf4j
@AllArgsConstructor
@Service
public class EquipeServiceImpl implements IEquipeService {

	private final EquipeRepository equipeRepository;

	public List<Equipe> retrieveAllEquipes() {
		log.info("Retrieving all teams");
		return (List<Equipe>) equipeRepository.findAll();
	}

	public Equipe addEquipe(Equipe e) {
		log.info("Adding a new team: {}", e);
		return equipeRepository.save(e);
	}

	public void deleteEquipe(Integer idEquipe) {
		Equipe e = retrieveEquipe(idEquipe);
		if (e != null) {
			log.info("Deleting team with ID: {}", idEquipe);
			equipeRepository.delete(e);
		} else {
			log.warn("Cannot delete team. Team with ID {} not found", idEquipe);
		}
	}

	public Equipe retrieveEquipe(Integer equipeId) {
		log.info("Retrieving team with ID: {}", equipeId);
		return equipeRepository.findById(equipeId).orElse(null);
	}

	public Equipe updateEquipe(Equipe e) {
		log.info("Updating team: {}", e);
		return equipeRepository.save(e);
	}

	public void evoluerEquipes() {
		List<Equipe> equipes = (List<Equipe>) equipeRepository.findAll();

		for (Equipe equipe : equipes) {
			// Vérifier si l'équipe est éligible pour une évolution
			if (equipe.getNiveau() == Niveau.JUNIOR || equipe.getNiveau() == Niveau.SENIOR) {

				int nbEtudiantsAvecContratsActifs = 0;

				for (Etudiant etudiant : equipe.getEtudiants()) {
					for (Contrat contrat : etudiant.getContrats()) {
						Date dateSysteme = new Date();
						long differenceInTime = dateSysteme.getTime() - contrat.getDateFinContrat().getTime();
						long differenceInYears = differenceInTime / (1000L * 60 * 60 * 24 * 365);

						// Vérifier si le contrat est actif depuis plus d'un an
						if (!contrat.getArchive() && differenceInYears > 1) {
							nbEtudiantsAvecContratsActifs++;
							break; // Un étudiant avec un contrat actif trouvé, passer au suivant
						}
					}

					if (nbEtudiantsAvecContratsActifs >= 3) {
						break; // Critère atteint, on peut arrêter la recherche
					}
				}

				// Évolution de l'équipe si elle remplit les critères
				if (nbEtudiantsAvecContratsActifs >= 3) {
					if (equipe.getNiveau() == Niveau.JUNIOR) {
						equipe.setNiveau(Niveau.SENIOR);
					} else if (equipe.getNiveau() == Niveau.SENIOR) {
						equipe.setNiveau(Niveau.EXPERT);
					}
					equipeRepository.save(equipe); // Sauvegarder les changements
				}
			}
		}
	}

}
