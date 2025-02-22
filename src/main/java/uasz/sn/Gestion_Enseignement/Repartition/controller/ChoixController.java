package uasz.sn.Gestion_Enseignement.Repartition.controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Formation;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Maquette;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.ECReposiroty;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.FormationRepository;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.MaquetteRepository;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.FormationService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.MaquetteService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.UEService;
import uasz.sn.Gestion_Enseignement.Repartition.modele.Choix;
import uasz.sn.Gestion_Enseignement.Repartition.modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Repartition.repository.Enseignementrepository;
import uasz.sn.Gestion_Enseignement.Repartition.service.Choixservice;
import uasz.sn.Gestion_Enseignement.Repartition.service.Enseignementservice;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.repository.EnseignantRepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.EnseignantService;

import java.security.Principal;
import java.util.List;

@Controller
public class ChoixController {
@Autowired
private Choixservice choixservice;
@Autowired
private Enseignementservice enseignementservice;
@Autowired
private MaquetteService maquetteService;
@Autowired
private UEService ueService;
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
private FormationService formationService;
@Autowired
private UtilisateurService utilisateurService;



    // Récupérer tous les choix enregistrés
    @GetMapping
    public List<Choix> getAllChoix() {
        return choixservice.getAllChoix();
    }

    @GetMapping("/ChefDepartement/Validation")
    public String afficherEnseignements(Model model) {
        List<Enseignement> enseignements = enseignementservice.lister();
        model.addAttribute("enseignements", enseignements);
        return "template_choix";  // Retourne la vue Thymeleaf "enseignements.html"
    }

    @GetMapping("/ChefDepartement/Repartition")
    public String afficherRepartition(Model model,Long id,String semestre,Principal principal) {
        List<Choix> choixes = choixservice.getAllChoix();
        model.addAttribute("choixes", choixes);


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

        // Récupérer les enseignements avec leurs enseignants
        List<Enseignement> enseignements = enseignementrepository.findAllWithEnseignants();
        model.addAttribute("enseignements", enseignements);

        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        ajouterInfosUtilisateur(model, utilisateur);

        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenomInitial", utilisateur.getPrenom().isEmpty() ? ""
                : String.valueOf(utilisateur.getPrenom().charAt(0)));

        return "chefDepartementRepartition";  // Retourne la vue Thymeleaf "enseignements.html"
    }



    @PostMapping("/accepter")
    public String accepterChoix(@RequestParam Long enseignantId,
                                @RequestParam Long enseignementId,
                                RedirectAttributes redirectAttributes) {
        choixservice.accepterChoix(enseignantId, enseignementId);
        redirectAttributes.addFlashAttribute("message", "Choix validé !");
        redirectAttributes.addFlashAttribute("type", "success");
        return "redirect:/ChefDepartement/Validation"; // Redirection vers la page principale
    }

    @PostMapping("/refuser")
    public String refuserChoix(@RequestParam Long enseignantId,
                               @RequestParam Long enseignementId,
                               RedirectAttributes redirectAttributes) {
        choixservice.refuserChoix(enseignantId, enseignementId);
        redirectAttributes.addFlashAttribute("message", "Choix refusé !");
        redirectAttributes.addFlashAttribute("type", "error");
        return "redirect:/ChefDepartement/Validation"; // Redirection vers la page principale
    }


    private void ajouterInfosUtilisateur(Model model, Utilisateur utilisateur) {
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenomInitial",
                utilisateur.getPrenom().isEmpty() ? "" :
                        String.valueOf(utilisateur.getPrenom().charAt(0)));
    }
}
