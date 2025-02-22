package uasz.sn.Gestion_Enseignement.Repartition.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.repository.UtilisateurRepository;
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
import uasz.sn.Gestion_Enseignement.Utilisateur.service.EnseignantService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    @Autowired
    private UtilisateurRepository utilisateurRepository;
    @Autowired
    private EnseignantService enseignantService;


    @RequestMapping(value = "/Enseignement", method = RequestMethod.GET)
    public String chefDepartement_Enseignement(Model model, Principal principal){
        List<Formation> formations=formationService.lister();
        model.addAttribute("formations",formations);

        Utilisateur utilisateur=utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom",utilisateur.getNom());
        model.addAttribute("prenom",utilisateur.getPrenom().charAt(0));
        return "template_enseignement";
    }

    @RequestMapping(value = "/EnseignementVac", method = RequestMethod.GET)
    public String chefDepartement_EnseignementVac(Model model, Principal principal){
        List<Formation> formations=formationService.lister();
        model.addAttribute("formations",formations);

        Utilisateur utilisateur=utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom",utilisateur.getNom());
        model.addAttribute("prenom",utilisateur.getPrenom().charAt(0));
        return "template_enseignementVac";
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
            // Récupérer les maquettes des deux semestres
            Maquette maquetteSemestre1 = maquetteService.rechercherParSemestre(id, 1);
            Maquette maquetteSemestre2 = maquetteService.rechercherParSemestre(id, 2);

            model.addAttribute("maquetteSemestre1", maquetteSemestre1 != null ? maquetteSemestre1 : new Maquette());
            model.addAttribute("maquetteSemestre2", maquetteSemestre2 != null ? maquetteSemestre2 : new Maquette());
        } catch (EntityNotFoundException e) {
            model.addAttribute("maquetteSemestre1", new Maquette());
            model.addAttribute("maquetteSemestre2", new Maquette());
        }

        // Ajouter le semestre actuel au modèle
        model.addAttribute("semestreActuel", "Semestre " + semestre);

        // Récupérer les UEs disponibles
        List<UE> ues = ueService.lister();
        model.addAttribute("ues", ues);

        // Récupérer les enseignements avec leurs enseignants
        List<Enseignement> enseignements = enseignementrepository.findAllWithEnseignants();
        model.addAttribute("enseignements", enseignements);


        // Récupérer les informations de l'utilisateur
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));

        return "enseignement";
    }

    @RequestMapping(value = "/EnseignementVac/detail/{id}", method = RequestMethod.GET)
    public String EnseignementVac(
            @PathVariable("id") Long id,
            Model model,
            Principal principal, String semestre) {


        // Récupérer la formation
        Formation formation = formationService.getById(id);
        model.addAttribute("formation", formation);

        try {
            // Récupérer les maquettes des deux semestres
            Maquette maquetteSemestre1 = maquetteService.rechercherParSemestre(id, 1);
            Maquette maquetteSemestre2 = maquetteService.rechercherParSemestre(id, 2);

            model.addAttribute("maquetteSemestre1", maquetteSemestre1 != null ? maquetteSemestre1 : new Maquette());
            model.addAttribute("maquetteSemestre2", maquetteSemestre2 != null ? maquetteSemestre2 : new Maquette());
        } catch (EntityNotFoundException e) {
            model.addAttribute("maquetteSemestre1", new Maquette());
            model.addAttribute("maquetteSemestre2", new Maquette());
        }

        // Ajouter le semestre actuel au modèle
        model.addAttribute("semestreActuel", "Semestre " + semestre);

        // Récupérer les UEs disponibles
        List<UE> ues = ueService.lister();
        model.addAttribute("ues", ues);

        // Récupérer les enseignements avec leurs enseignants
        List<Enseignement> enseignements = enseignementrepository.findAllWithEnseignants();
        model.addAttribute("enseignements", enseignements);


        // Récupérer les informations de l'utilisateur
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));

        return "enseignementVac";
    }


    @RequestMapping(value = "/ajouterEnseignement", method = RequestMethod.POST)
    public String ajouter(
            @RequestParam("type") String type,
            @RequestParam("maquetteId") Long maquetteId,
            @RequestParam("ecId") Long ecId,
            Model model,
            Principal principal) {

        // Vérification du type d'enseignement
        if (!type.equals("CM") && !type.equals("TD") && !type.equals("TP")) {
            throw new IllegalArgumentException("Type d'enseignement invalide !");
        }

        // Récupération des objets Maquette et EC
        Maquette maquette = maquetteRepository.findById(maquetteId)
                .orElseThrow(() -> new IllegalArgumentException("Maquette non trouvée"));
        EC ec = ecReposiroty.findById(ecId)
                .orElseThrow(() -> new IllegalArgumentException("EC non trouvé"));

        // Récupération de l'utilisateur connecté
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());

        // Vérification si l'utilisateur est bien un Enseignant
        if (!(utilisateur instanceof Enseignant)) {
            throw new IllegalArgumentException("L'utilisateur connecté n'est pas un enseignant !");
        }

        Enseignant enseignant = (Enseignant) utilisateur;

        // Ajouter les informations de l'utilisateur dans le modèle
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));
        model.addAttribute("idEnseignant", enseignant.getId()); // Ajout de l'ID de l'enseignant connecté

        // Création de l'enseignement
        Enseignement enseignement = new Enseignement();
        enseignement.setMaquette(maquette);
        enseignement.setEc(ec);
        enseignement.setTypeEnseignement(type);

        // Ajout de l'enseignant connecté à la liste des enseignants
        List<Enseignant> enseignants = new ArrayList<>();
        enseignants.add(enseignant);
        enseignement.setEnseignants(enseignants);

        // Sauvegarde de l'enseignement
        enseignementrepository.save(enseignement);

        return "redirect:/Enseignement";
    }
    @RequestMapping(value = "/listeEnseignantsEnseignements", method = RequestMethod.GET)
    public String listeEnseignantsEnseignements(Model model) {
        List<Enseignement> enseignements = enseignementrepository.findAllWithEnseignants();
        System.out.println("Nombre d'enseignements récupérés : " + enseignements.size());
        for (Enseignement e : enseignements) {
            System.out.println("Enseignement ID : " + e.getId());
            System.out.println("Type : " + e.getTypeEnseignement());
            System.out.println("Nombre d'enseignants associés : " + e.getEnseignants().size());
            for (Enseignant ens : e.getEnseignants()) {
                System.out.println("  - Enseignant : " + ens.getNom() + " " + ens.getPrenom());
            }
        }
        model.addAttribute("enseignements", enseignements);
        return "template_choix"; // Assurez-vous que cette vue existe
    }


    @GetMapping("/enseignements")
    public String afficherEnseignements(Model model) {
        List<Enseignement> enseignements = enseignementservice.lister();
        model.addAttribute("enseignements", enseignements);
        return "etatenseignement";  // Retourne la vue Thymeleaf "enseignements.html"
    }


    @RequestMapping(value = "/Chefdepartement/Enseignement/detail/{id}", method = RequestMethod.GET)
    public String EnseignementDep(
            @PathVariable("id") Long id,
            Model model,
            Principal principal, String semestre) {


        // Récupérer la formation
        Formation formation = formationService.getById(id);
        model.addAttribute("formation", formation);

        try {
            // Récupérer les maquettes des deux semestres
            Maquette maquetteSemestre1 = maquetteService.rechercherParSemestre(id, 1);
            Maquette maquetteSemestre2 = maquetteService.rechercherParSemestre(id, 2);

            model.addAttribute("maquetteSemestre1", maquetteSemestre1 != null ? maquetteSemestre1 : new Maquette());
            model.addAttribute("maquetteSemestre2", maquetteSemestre2 != null ? maquetteSemestre2 : new Maquette());
        } catch (EntityNotFoundException e) {
            model.addAttribute("maquetteSemestre1", new Maquette());
            model.addAttribute("maquetteSemestre2", new Maquette());
        }

        // Ajouter le semestre actuel au modèle
        model.addAttribute("semestreActuel", "Semestre " + semestre);

        // Récupérer les UEs disponibles
        List<UE> ues = ueService.lister();
        model.addAttribute("ues", ues);

        // Récupérer les enseignements avec leurs enseignants
        List<Enseignement> enseignements = enseignementrepository.findAllWithEnseignants();
        model.addAttribute("enseignements", enseignements);


        // Récupérer les informations de l'utilisateur
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));

        return "chefdepchoix";
    }

}

