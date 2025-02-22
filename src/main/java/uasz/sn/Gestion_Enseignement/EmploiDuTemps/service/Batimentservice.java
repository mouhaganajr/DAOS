package uasz.sn.Gestion_Enseignement.EmploiDuTemps.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.modele.Batiment;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.repository.Batimentrepository;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.UERepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class Batimentservice {
    @Autowired
    private Batimentrepository batimentrepository;
    public Batiment ajouter_Batiment(Batiment batiment ){
        return batimentrepository.save(batiment);
    }
    public List<Batiment> lister(){
        return batimentrepository.findAll();
    }
    public Batiment rechercher_Batiment(Long id){
        return batimentrepository.findById(id).get();
    }
    public Batiment modifier_Batiment(Batiment batiment ){
        return batimentrepository.save(batiment);
    }

    public Batiment getById(Long id) {
        Optional<Batiment> batiment = batimentrepository.findById(id);
        return batiment.orElse(null); // Retourne l'objet UE ou null si non trouvé
    }

}
