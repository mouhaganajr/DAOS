package uasz.sn.Gestion_Enseignement.EmploiDuTemps.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.modele.Salle;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.modele.Seance;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.service.Salleservice;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.service.seanceservice;
import uasz.sn.Gestion_Enseignement.Repartition.modele.Choix;
import uasz.sn.Gestion_Enseignement.Repartition.service.Choixservice;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class seanceController {

    @Autowired private Choixservice choixservice;
    @Autowired private Salleservice salleservice;
    @Autowired private UtilisateurService utilisateurService;
    @Autowired private seanceservice seanceservice;

    // Configuration des constantes pour le tableau
    private static final List<String> JOURS_SEMAINE = Arrays.asList(
            "Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi");

    private static final List<String> CRENEAUX_HORAIRES = Arrays.asList(
            "8h-10h", "10h-12h", "15h-17h", "17h-19h");

    // Formulaire d'ajout de séance
    @RequestMapping(value = "/ChefDepartement/ajouterseance", method = RequestMethod.GET)
    public String afficherFormulaireAjout(Model model, Principal principal) {
        model.addAttribute("jours", JOURS_SEMAINE);
        model.addAttribute("creneaux", CRENEAUX_HORAIRES);
        model.addAttribute("salles", salleservice.lister());
        model.addAttribute("choixes", choixservice.getAllChoix());

        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        ajouterInfosUtilisateur(model, utilisateur);

        return "ajouterseance";
    }

    // Traitement de l'ajout
    @RequestMapping(value = "/ChefDepartement/ajouterseance", method = RequestMethod.POST)
    public String traiterAjoutSeance(
            @RequestParam List<Long> choixIds,
            @RequestParam List<Long> salleIds,
            @RequestParam String jour,
            @RequestParam String creneau,
            @RequestParam String Heurededebut,
            @RequestParam String Heuredefin) {

        seanceservice.creerSeanceComplete(
                choixIds,
                salleIds,
                jour,
                creneau,
                Heurededebut,
                Heuredefin
        );

        return "redirect:/seance";
    }

    @GetMapping("/seance")
    public String afficherEmploiDuTemps(Model model, Principal principal) {
        model.addAttribute("structureSeances", seanceservice.getSeancesStructurees());
        model.addAttribute("jours", JOURS_SEMAINE);
        model.addAttribute("creneaux", CRENEAUX_HORAIRES);
        model.addAttribute("salles", salleservice.lister());
        model.addAttribute("choixes", choixservice.getAllChoix());

        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenomInitial", utilisateur.getPrenom().isEmpty() ? ""
                : String.valueOf(utilisateur.getPrenom().charAt(0)));

        return "emploidutemps";
    }

    private void ajouterInfosUtilisateur(Model model, Utilisateur utilisateur) {
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenomInitial",
                utilisateur.getPrenom().isEmpty() ? "" :
                        String.valueOf(utilisateur.getPrenom().charAt(0)));
    }
}