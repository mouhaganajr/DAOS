package uasz.sn.Gestion_Enseignement.GestionMaquette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;

import java.util.List;

public interface ECReposiroty extends JpaRepository<EC,Long> {
    // Cette méthode recherche les ECs associés à l'ID de l'UE
    List<EC> findByUeId(Long idUE);
    @Query("SELECT e.id FROM EC e ORDER BY e.id DESC")
    int findLastId();
}
