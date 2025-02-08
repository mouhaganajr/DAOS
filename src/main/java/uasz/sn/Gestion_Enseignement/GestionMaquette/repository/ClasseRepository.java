package uasz.sn.Gestion_Enseignement.GestionMaquette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;

import java.util.List;

public interface ClasseRepository extends JpaRepository<Classe,Long> {
    // Cette méthode recherche les classes associés à l'ID de la formation
    List<Classe> findByFormationId(Long id);
    @Query("SELECT c.id FROM Classe c ORDER BY c.id DESC")
    int findLastId();
}
