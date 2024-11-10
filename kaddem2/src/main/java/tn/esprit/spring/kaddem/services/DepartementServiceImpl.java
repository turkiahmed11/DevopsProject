package tn.esprit.spring.kaddem.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import tn.esprit.spring.kaddem.entities.Departement;
import tn.esprit.spring.kaddem.repositories.DepartementRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Slf4j
@Service
public class DepartementServiceImpl implements IDepartementService {

	private static final Logger logger = LogManager.getLogger(DepartementServiceImpl.class);

	@Autowired
	DepartementRepository departementRepository;

	public List<Departement> retrieveAllDepartements() {
		logger.info("Retrieving all departments");
		List<Departement> departments = StreamSupport
				.stream(departementRepository.findAll().spliterator(), false)
				.collect(Collectors.toList());
		logger.debug("Retrieved departments: {}", departments);
		return departments;
	}

	public Departement addDepartement(Departement d) {
		logger.info("Adding new department: {}", d);
		return departementRepository.save(d);
	}

	public Departement updateDepartement(Departement d) {
		logger.info("Updating department: {}", d);
		return departementRepository.save(d);
	}

	public Departement retrieveDepartement(Integer idDepart) {
		logger.info("Retrieving department with ID: {}", idDepart);
		Optional<Departement> department = departementRepository.findById(idDepart);
		if (department.isPresent()) {
			logger.debug("Department found: {}", department.get());
			return department.get();
		} else {
			logger.warn("Department with ID {} not found", idDepart);
			return null;  // Vous pouvez lancer une exception personnalisée ici si souhaité
		}
	}

	public void deleteDepartement(Integer idDepartement) {
		logger.info("Deleting department with ID: {}", idDepartement);
		Departement d = retrieveDepartement(idDepartement);
		if (d != null) {
			departementRepository.delete(d);
			logger.info("Department deleted successfully.");
		} else {
			logger.warn("Department with ID {} not found, cannot delete.", idDepartement);
		}
	}
}
