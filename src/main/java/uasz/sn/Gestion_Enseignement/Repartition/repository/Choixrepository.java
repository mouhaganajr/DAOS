package uasz.sn.Gestion_Enseignement.Repartition.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.Repartition.modele.Choix;

import java.util.List;

public interface Choixrepository extends JpaRepository<Choix,Long> {
    @Transactional
    void deleteByEnseignantIdAndEnseignementId(Long enseignantId, Long enseignementId);
 }
