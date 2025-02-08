package uasz.sn.Gestion_Enseignement.GestionMaquette.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.ECReposiroty;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.UERepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Permanent;
import uasz.sn.Gestion_Enseignement.Utilisateur.repository.PermanentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ECService {
    @Autowired
    private ECReposiroty ecReposiroty;
    @Autowired
    private  UEService ueService;
    public EC ajouter_EC(EC ec){
        return ecReposiroty.save(ec);
    }


    public List<EC> lister(){
        return ecReposiroty.findAll();
    }
    public EC rechercher_EC(Long id){
        return ecReposiroty.findById(id).get();
    }

    public EC modifier_EC(EC ec){
        return ecReposiroty.save(ec);
    }
    public void supprimer_EC(Long id){
        ecReposiroty.deleteById(id);
    }

    public List<EC> listerParUE(Long idUE) {
        // Implémentation de la récupération des ECs associés à l'UE par son ID
        return ecReposiroty.findByUeId(idUE);  // Exemple avec une méthode dans le repository
    }

    public EC getById(Long id) {
        // Retourner l'EC si trouvé, sinon null
        return ecReposiroty.findById(id).orElse(null);
    }

    public void activer(Long id){
        EC ec=ecReposiroty.findById(id).get();
        if (ec.isActive()==true){
            ec.setActive(false);
        }
        else {
            ec.setActive(true);
        }
        ecReposiroty.save(ec);
    }

    public void archiver(Long id){
        EC ec=ecReposiroty.findById(id).get();
        if (ec.isArchive()==true){
            ec.setArchive(false);
        }
        else {
            ec.setArchive(true);
        }
        ecReposiroty.save(ec);
    }
}
