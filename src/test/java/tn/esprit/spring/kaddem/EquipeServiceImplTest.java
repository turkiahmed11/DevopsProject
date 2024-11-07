package tn.esprit.spring.kaddem;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tn.esprit.spring.kaddem.entities.DetailEquipe;
import tn.esprit.spring.kaddem.entities.Equipe;
import tn.esprit.spring.kaddem.entities.Niveau;
import tn.esprit.spring.kaddem.repositories.EquipeRepository;
import tn.esprit.spring.kaddem.services.EquipeServiceImpl;

import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EquipeServiceImplTest {

    @Mock
    EquipeRepository equipeRepository;

    @InjectMocks
    EquipeServiceImpl equipeService ;

    Equipe equipe = new Equipe("Equipe1", Niveau.JUNIOR, new HashSet<>(), new DetailEquipe());

    List<Equipe> listEquipes = new ArrayList<Equipe>() {{

        add(new Equipe("Equipe2", Niveau.SENIOR, new HashSet<>(), new DetailEquipe()));
        add(new Equipe("Equipe3", Niveau.EXPERT, new HashSet<>(), new DetailEquipe()));
    }};
    @Test
    public void testRetrieveEquipe() {
        // Simuler la méthode findById du repository pour qu'elle retourne une équipe spécifique
        Mockito.when(equipeRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(new Equipe("Equipe1", Niveau.JUNIOR, new HashSet<>(), new DetailEquipe())));

        // Appeler le service pour récupérer l'équipe avec un identifiant Integer
        Equipe equipe2 = equipeService.retrieveEquipe(2); // 2 est un Integer ici

        // Vérifier que l'équipe récupérée n'est pas nulle
        Assertions.assertNotNull(equipe2);
    }




}
