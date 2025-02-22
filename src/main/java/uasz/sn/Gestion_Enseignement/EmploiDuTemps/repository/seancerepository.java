package uasz.sn.Gestion_Enseignement.EmploiDuTemps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.modele.Seance;

import java.util.List;

public interface seancerepository extends JpaRepository<Seance,Long> {
    @Query("SELECT DISTINCT s FROM Seance s " +
            "LEFT JOIN FETCH s.choixes c " +
            "LEFT JOIN FETCH c.enseignement e " +
            "LEFT JOIN FETCH e.ec " +
            "LEFT JOIN FETCH s.salles sa")
    List<Seance> findAllWithRelations();



    @Query("SELECT DISTINCT s FROM Seance s LEFT JOIN FETCH s.salles")
    List<Seance> findAllWithSalles();

    @Query("SELECT DISTINCT s FROM Seance s LEFT JOIN FETCH s.choixes")
    List<Seance> findAllWithChoixes();


}
