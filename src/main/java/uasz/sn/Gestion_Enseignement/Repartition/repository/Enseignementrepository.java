package uasz.sn.Gestion_Enseignement.Repartition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.Repartition.modele.Enseignement;

import java.util.List;
import java.util.Optional;

public interface Enseignementrepository extends JpaRepository<Enseignement,Long> {
    List<Enseignement> findByMaquetteId(Long idUE);
    @Query("SELECT e.id FROM Enseignement e ORDER BY e.id DESC")
    int findLastId();

    List<Enseignement> findByEcId(Long idUE);
    @Query("SELECT e.id FROM Enseignement e ORDER BY e.id DESC")
    int findLastsId();


    Optional<Enseignement> findById(Long aLong);

    Long id(int id);

    @Query("SELECT e FROM Enseignement e JOIN FETCH e.enseignants")
    List<Enseignement> findAllWithEnseignants();

}
