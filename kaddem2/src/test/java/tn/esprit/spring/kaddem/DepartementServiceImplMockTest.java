package tn.esprit.spring.kaddem;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;
import tn.esprit.spring.kaddem.services.DepartementServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DepartementServiceImplMockTest {

    @Mock
    private DepartementRepository departementRepository;

    @InjectMocks
    private DepartementServiceImpl departementService;

    private Departement departement;
    private List<Departement> listDepartements;

    @BeforeEach
    public void setUp() {
        departement = new Departement();
        departement.setIdDepart(1);
        departement.setNomDepart("Informatique");

        listDepartements = new ArrayList<>();
        listDepartements.add(departement);
        listDepartements.add(new Departement(2, "Gestion"));
    }

    @Test
    public void testRetrieveAllDepartements() {
        Mockito.when(departementRepository.findAll()).thenReturn(listDepartements);
        List<Departement> retrievedDepartements = departementService.retrieveAllDepartements();
        Assertions.assertEquals(2, retrievedDepartements.size());
    }

    @Test
    public void testAddDepartement() {
        Mockito.when(departementRepository.save(departement)).thenReturn(departement);
        Departement savedDepartement = departementService.addDepartement(departement);
        Assertions.assertNotNull(savedDepartement);
        Assertions.assertEquals("Informatique", savedDepartement.getNomDepart());
    }

    @Test
    public void testUpdateDepartement() {
        Departement updatedDepartement = new Departement(1, "Updated Name");
        Mockito.when(departementRepository.save(updatedDepartement)).thenReturn(updatedDepartement);
        Departement result = departementService.updateDepartement(updatedDepartement);
        Assertions.assertEquals("Updated Name", result.getNomDepart());
    }

    @Test
    public void testRetrieveDepartement() {
        Mockito.when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        Departement retrievedDepartement = departementService.retrieveDepartement(1);
        Assertions.assertNotNull(retrievedDepartement);
        Assertions.assertEquals(1, retrievedDepartement.getIdDepart());
    }

    @Test
    public void testDeleteDepartement() {
        Mockito.when(departementRepository.findById(1)).thenReturn(Optional.of(departement));
        departementService.deleteDepartement(1);
        Mockito.verify(departementRepository).delete(departement);
    }
}
