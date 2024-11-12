package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Option;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.EtudiantServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EtudiantTest{
    @Mock
    DepartementRepository departementRepository;
    // Ou
    EtudiantRepository etudiantRepository = Mockito.mock(EtudiantRepository.class);
    @InjectMocks
    EtudiantServiceImpl etudiantservice;
    Departement departement=new Departement("Dep1");
    Etudiant etudiant=new Etudiant("jawher", "jaziri", Option.GAMIX);

    List<Etudiant> listEtudiants = new ArrayList<Etudiant>() {
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
    @Test
    public void testRetrieveAllEtudiants() {
        // Arrange
        List<Etudiant> etudiantList = new ArrayList<>();
        etudiantList.add(etudiant);
        Mockito.when(etudiantRepository.findAll()).thenReturn(etudiantList);

        // Act
        List<Etudiant> result = etudiantservice.retrieveAllEtudiants();

        // Assert
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals("jawher", result.get(0).getNomE());
    }
    @Test
    public void testAddEtudiant() {
        // Arrange
        Mockito.when(etudiantRepository.save(Mockito.any(Etudiant.class))).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantservice.addEtudiant(etudiant);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("jawher", result.getNomE());
    }
    @Test
    public void testUpdateEtudiant() {
        // Arrange
        etudiant.setNomE("updatedName");
        Mockito.when(etudiantRepository.save(Mockito.any(Etudiant.class))).thenReturn(etudiant);

        // Act
        Etudiant result = etudiantservice.updateEtudiant(etudiant);

        // Assert
        Assertions.assertNotNull(result);
        Assertions.assertEquals("updatedName", result.getNomE());
    }

    @Test
    public void testAssignEtudiantToDepartement() {
        // Arrange
        Mockito.when(etudiantRepository.findById(1)).thenReturn(Optional.of(etudiant));
        Mockito.when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        Mockito.when(etudiantRepository.save(Mockito.any(Etudiant.class))).thenReturn(etudiant);

        // Act
        etudiantservice.assignEtudiantToDepartement(1, 1);

        // Assert
        Mockito.verify(etudiantRepository, Mockito.times(1)).save(etudiant);
        Assertions.assertEquals(departement, etudiant.getDepartement());
    }
}
