package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import tn.esprit.spring.kaddem.entities.DetailEquipe;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.services.EquipeServiceImpl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EquipeServiceImplTest {

    @Mock
    EquipeRepository equipeRepository;

    @InjectMocks
    EquipeServiceImpl equipeService;

    Equipe equipe = new Equipe("Equipe1", Niveau.JUNIOR, new HashSet<>(), new DetailEquipe());

    List<Equipe> listEquipes = new ArrayList<Equipe>() {{
        add(new Equipe("Equipe2", Niveau.SENIOR, new HashSet<>(), new DetailEquipe()));
        add(new Equipe("Equipe3", Niveau.EXPERT, new HashSet<>(), new DetailEquipe()));
    }};

    @Test
    public void testRetrieveEquipe() {
        Mockito.when(equipeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(equipe));

        Equipe equipe2 = equipeService.retrieveEquipe(2);

        Assertions.assertNotNull(equipe2);
    }

    @Test
    public void testAddEquipe() {
        Mockito.when(equipeRepository.save(Mockito.any(Equipe.class))).thenReturn(equipe);

        Equipe newEquipe = equipeService.addEquipe(equipe);

        Assertions.assertNotNull(newEquipe);
        Assertions.assertEquals("Equipe1", newEquipe.getNomEquipe());
    }

   /* @Test
    public void testDeleteEquipe() {
        // Arranger : préparer les données
        Integer equipeId = 2;

        // Appeler la méthode à tester
        equipeService.deleteEquipe(equipeId);

        // Vérification : s'assurer que deleteById a bien été appelé sur le repository avec l'ID du client
        Mockito.verify(equipeRepository, Mockito.times(1)).deleteById(equipeId);
    } */

    @Test
    public void testUpdateEquipe() {
        Equipe updatedEquipe = new Equipe("Equipe Updated", Niveau.EXPERT, new HashSet<>(), new DetailEquipe());
        Mockito.when(equipeRepository.save(Mockito.any(Equipe.class))).thenReturn(updatedEquipe);

        Equipe result = equipeService.updateEquipe(updatedEquipe);

        Assertions.assertNotNull(result);
        Assertions.assertEquals("Equipe Updated", result.getNomEquipe());
        Assertions.assertEquals(Niveau.EXPERT, result.getNiveau());
    }

    @Test
    public void testRetrieveAllEquipes() {
        Mockito.when(equipeRepository.findAll()).thenReturn(listEquipes);

        List<Equipe> equipes = equipeService.retrieveAllEquipes();

        Assertions.assertNotNull(equipes);
        Assertions.assertEquals(2, equipes.size());
    }
}
