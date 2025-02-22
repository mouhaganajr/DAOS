package uasz.sn.Gestion_Enseignement.GestionMaquette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.*;

import java.util.List;
import java.util.Optional;

public interface MaquetteRepository extends JpaRepository<Maquette,Long> {
    // Cette méthode recherche les classes associés à l'ID de la formation
    List<Maquette> findByFormationId(Long id);
    @Query("SELECT m.id FROM Maquette m ORDER BY m.id DESC")
    int findLastId();


    Optional<Maquette> findByFormationAndSemestre(Formation formationId, int semestre);

    @Query("SELECT m FROM Maquette m LEFT JOIN FETCH m.ues WHERE m.semestre = :semestre AND m.formation = :formation")
    Maquette findBySemestreAndFormation(@Param("semestre") int semestre, @Param("formation") Formation formation);

}


