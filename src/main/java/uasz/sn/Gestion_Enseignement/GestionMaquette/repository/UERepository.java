package uasz.sn.Gestion_Enseignement.GestionMaquette.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;

import java.util.List;

public interface UERepository extends JpaRepository<UE,Long> {
    List<UE> findByMaquettesId(Long maquetteId);

}
