package uasz.sn.Gestion_Enseignement.EmploiDuTemps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.modele.Salle;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;

import java.util.List;

public interface Sallerepository extends JpaRepository<Salle,Long> {
    // Cette méthode recherche les ECs associés à l'ID de l'UE
    List<Salle> findByBatimentId(Long id);
    @Query("SELECT s.id FROM Salle s ORDER BY s.id DESC")
    int findLastId();
}
