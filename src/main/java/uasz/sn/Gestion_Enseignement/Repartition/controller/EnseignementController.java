package uasz.sn.Gestion_Enseignement.Repartition.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Formation;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Maquette;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.ECReposiroty;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.FormationRepository;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.MaquetteRepository;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.FormationService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.MaquetteService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.UEService;
import uasz.sn.Gestion_Enseignement.Repartition.modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Repartition.repository.Enseignementrepository;
import uasz.sn.Gestion_Enseignement.Repartition.service.Enseignementservice;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.repository.EnseignantRepository;

import java.security.Principal;
import java.util.List;

@Controller
public class EnseignementController {
    @Autowired
    private FormationService formationService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private MaquetteService maquetteService;
    @Autowired
    private UEService ueService;
    @Autowired
    private Enseignementservice enseignementservice;
    @Autowired
    private EnseignantRepository enseignantRepository;
    @Autowired
    private Enseignementrepository enseignementrepository;
    @Autowired
    private MaquetteRepository maquetteRepository;
    @Autowired
    private FormationRepository formationRepository;
    @Autowired
    private ECReposiroty ecReposiroty;


    @RequestMapping(value = "/Enseignement", method = RequestMethod.GET)
    public String chefDepartement_Enseignement(Model model, Principal principal){
        List<Formation> formations=formationService.lister();
        model.addAttribute("formations",formations);

        Utilisateur utilisateur=utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom",utilisateur.getNom());
        model.addAttribute("prenom",utilisateur.getPrenom().charAt(0));
        return "template_enseignement";
    }


    @RequestMapping(value = "/Enseignement/detail/{id}", method = RequestMethod.GET)
    public String Enseignement(
            @PathVariable("id") Long id,
            Model model,
            Principal principal, String semestre) {


        // Récupérer la formation
        Formation formation = formationService.getById(id);
        model.addAttribute("formation", formation);

        try {
            Maquette maquettes = maquetteService.rechercher(id);
            model.addAttribute("maquettes",maquettes != null ? maquettes : new Maquette());
        }catch (EntityNotFoundException e){
            model.addAttribute("maquettes" , new Maquette());
        }

        // Ajouter le semestre actuel au modèle
        model.addAttribute("semestreActuel", "Semestre " + semestre);

        // Récupérer les UEs disponibles
        List<UE> ues = ueService.lister();
        model.addAttribute("ues", ues);


        // Récupérer les informations de l'utilisateur
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));

        return "enseignement";
    }


    @RequestMapping(value = "/ajouterEnseignement", method = RequestMethod.POST)
    public String ajouter(
                          @RequestParam("type") String type,
                          @RequestParam("maquetteId") Long maquetteId,
                          @RequestParam("ecId") Long ecId,Long id) {
        // Vérification du type d'enseignement
        if (!type.equals("CM") && !type.equals("TD") && !type.equals("TP")) {
            throw new IllegalArgumentException("Type d'enseignement invalide !");
        }

        // Récupération des objets Maquette et EC
        Maquette maquette = maquetteRepository.findById(maquetteId)
                .orElseThrow(() -> new IllegalArgumentException("Maquette non trouvée"));
        EC ec = ecReposiroty.findById(ecId)
                .orElseThrow(() -> new IllegalArgumentException("EC non trouvé"));

        // Création de l'enseignement
        Enseignement enseignement = new Enseignement();
        enseignement.setMaquette(maquette);
        enseignement.setEc(ec);
        enseignement.setTypeEnseignement(type);


        enseignementrepository.save(enseignement);
        return "enseignement";
    }
}

