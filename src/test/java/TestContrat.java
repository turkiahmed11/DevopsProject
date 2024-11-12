import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Etudiant;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.repositories.EtudiantRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


@ExtendWith(MockitoExtension.class)
public class TestContrat {
@Mock
    ContratRepository contratRepository ;
@InjectMocks
    ContratServiceImpl contratService ;

    @Mock
    private EtudiantRepository etudiantRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


Contrat contrat = new Contrat(Specialite.CLOUD,1580);

List<Contrat> listContrat = new ArrayList<Contrat>() {
    {
        add(new Contrat( Specialite.CLOUD , 1800));
        add(new Contrat( Specialite.IA , 1900)) ;
    }
};

@Test
    public void testRetriveContrat(){
    Mockito.when(contratRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(contrat));
    Contrat contrat1 = contratService.retrieveContrat(1);
    Assertions.assertNotNull(contrat1);
}

    @Test
    void testAddContrat() {
        Contrat contrat = new Contrat(Specialite.IA, 300);
        when(contratRepository.save(contrat)).thenReturn(contrat);

        Contrat result = contratService.addContrat(contrat);

        assertNotNull(result);
        assertEquals(Specialite.IA, result.getSpecialite());
        verify(contratRepository, times(1)).save(contrat);
    }

    @Test
    void testRemoveContrat() {
        Contrat contrat = new Contrat();
        contrat.setIdContrat(1);
        when(contratRepository.findById(1)).thenReturn(Optional.of(contrat));

        contratService.removeContrat(1);

        verify(contratRepository, times(1)).delete(contrat);
    }

    @Test
    void testAffectContratToEtudiant() {
        // Set up mock student and contract
        Etudiant etudiant = new Etudiant();
        etudiant.setNomE("John");
        etudiant.setPrenomE("Doe");
        etudiant.setContrats(new HashSet<>());

        Contrat contrat = new Contrat(Specialite.IA, 300);
        contrat.setIdContrat(1);

        when(etudiantRepository.findByNomEAndPrenomE("John", "Doe")).thenReturn(etudiant);
        when(contratRepository.findByIdContrat(1)).thenReturn(contrat);  // Mock `findByIdContrat` here

        Contrat result = contratService.affectContratToEtudiant(1, "John", "Doe");

        assertNotNull(result);
        assertEquals(etudiant, result.getEtudiant());
        verify(contratRepository, times(1)).save(contrat);
    }


    @Test
    void testGetChiffreAffaireEntreDeuxDates() {
        Contrat contrat1 = new Contrat();
        contrat1.setSpecialite(Specialite.IA);

        Contrat contrat2 = new Contrat();
        contrat2.setSpecialite(Specialite.CLOUD);

        List<Contrat> contrats = Arrays.asList(contrat1, contrat2);
        when(contratRepository.findAll()).thenReturn(contrats);

        Date startDate = new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(60));
        Date endDate = new Date();

        float result = contratService.getChiffreAffaireEntreDeuxDates(startDate, endDate);

        assertEquals(1400, result); // Expected result based on specialties and 2 months duration
    }
}

