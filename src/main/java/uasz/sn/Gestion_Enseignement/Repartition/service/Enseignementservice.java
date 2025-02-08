package uasz.sn.Gestion_Enseignement.Repartition.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.repository.UtilisateurRepository;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Maquette;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.ECService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.MaquetteService;
import uasz.sn.Gestion_Enseignement.Repartition.modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Repartition.repository.Enseignementrepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.repository.EnseignantRepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.EnseignantService;

import java.security.Principal;
import java.util.Collection;
import java.util.List;

@Transactional
@Service
public class Enseignementservice {
    @Autowired
    Enseignementrepository enseignementrepository;
    @Autowired
    MaquetteService maquetteService;
    @Autowired
    ECService ecService;
    @Autowired
    EnseignantService enseignantService;
    @Autowired
    UtilisateurService utilisateurService;
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private EnseignantRepository enseignantRepository;

    public List<Enseignement> lister(){
        return enseignementrepository.findAll();
    }
    public List<Enseignement> listerParMaquette(Long id) {
        // Implémentation de la récupération des ECs associés à l'UE par son ID
        return  enseignementrepository.findByMaquetteId(id); // Exemple avec une méthode dans le repository
    }

    public List<Enseignement> listerParEC(Long id) {
        // Implémentation de la récupération des ECs associés à l'UE par son ID
        return  enseignementrepository.findByEcId(id); // Exemple avec une méthode dans le repository
    }

    public Enseignement ajouter(  String type, Maquette maquette, EC ec) {
        // Vérification du type d'enseignement
        if (!type.equals("CM") && !type.equals("TD") && !type.equals("TP")) {
            throw new IllegalArgumentException("Type d'enseignement invalide !");
        }

        // Création de l'enseignement
            Enseignement enseignement = new Enseignement();
            enseignement.setMaquette(maquette);
            enseignement.setEc(ec);
            enseignement.setTypeEnseignement(type);


        // Sauvegarde finale
        return enseignementrepository.save(enseignement);

    }

}
