package uasz.sn.Gestion_Enseignement.GestionMaquette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Formation;

public interface FormationRepository extends JpaRepository<Formation,Long> {
}
