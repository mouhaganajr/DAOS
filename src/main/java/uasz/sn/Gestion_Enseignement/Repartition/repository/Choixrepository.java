package uasz.sn.Gestion_Enseignement.Repartition.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.Repartition.modele.Choix;

public interface Choixrepository extends JpaRepository<Choix,Long> {

    }
