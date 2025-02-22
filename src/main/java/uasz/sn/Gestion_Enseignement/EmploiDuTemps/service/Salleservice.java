package uasz.sn.Gestion_Enseignement.EmploiDuTemps.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.modele.Salle;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.repository.Sallerepository;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.ECReposiroty;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.UEService;

import java.util.List;

@Service
@Transactional
public class Salleservice {

    @Autowired
    private Sallerepository sallerepository;
    @Autowired
    private Batimentservice batimentservice;
    public Salle ajouter_Salle(Salle salle){
        return sallerepository.save(salle);
    }


    public List<Salle> lister(){
        return sallerepository.findAll();
    }
    public Salle rechercher_Salle(Long id){
        return sallerepository.findById(id).get();
    }

    public Salle modifier_Salle(Salle salle ){
        return sallerepository.save(salle);
    }
    public void supprimer_Salle(Long id){
        sallerepository.deleteById(id);
    }

    public List<Salle> listerParBatiment(Long id) {
        // Implémentation de la récupération des ECs associés à l'UE par son ID
        return sallerepository.findByBatimentId(id);  // Exemple avec une méthode dans le repository
    }
}
