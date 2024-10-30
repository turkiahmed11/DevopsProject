import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.kaddem.entities.Contrat;
import tn.esprit.spring.kaddem.entities.Specialite;
import tn.esprit.spring.kaddem.repositories.ContratRepository;
import tn.esprit.spring.kaddem.services.ContratServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class TestContrat {
@Mock
    ContratRepository contratRepository ;
@InjectMocks
    ContratServiceImpl contratService ;

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

}