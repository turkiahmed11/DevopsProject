package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Option;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.EtudiantServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EtudiantServiceImplMock {

    // Ou
    EtudiantRepository etudiantRepository = Mockito.mock(EtudiantRepository.class);
    @InjectMocks
    EtudiantServiceImpl etudiantservice;
    Etudiant etudiant=new Etudiant("jawher", "jaziri", Option.GAMIX);
    List<Etudiant> listUsers = new ArrayList<Etudiant>() {
        {
            add(new Etudiant("jawher", "jaziri", Option.GAMIX));
            add(new Etudiant("skander", "jaziri", Option.NIDS));
        }
    };
    @Test
    public void testRetrieveEtudiant() {
        Mockito.when(etudiantRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(etudiant))
        ;
        Etudiant etudiant1 = etudiantservice.retrieveEtudiant(1);
        Assertions.assertNotNull(etudiant1);
    }
}
