package uasz.sn.Gestion_Enseignement.GestionMaquette.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Formation;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.ClasseService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.FormationService;

import java.security.Principal;
import java.util.List;

@Controller
public class ClasseController {
    @Autowired
    private ClasseService classeService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    FormationService formationService;




    @RequestMapping(value = "/ChefDepartement/ajouterClasse", method = RequestMethod.POST)
    public String ajouter_Classe(Classe classe) {
        classeService.ajouter_Classe(classe);
        Long ide = classe.getFormation().getId();
        return "redirect:/detail_formation/" + ide;
    }

    @RequestMapping(value = "/ChefDepartement/modifierClasse", method = RequestMethod.POST)
    public String modifier_Classe(Classe classe) {
        classeService.modifier_Classe(classe);
        Long ide = classe.getFormation().getId();
        return "redirect:/detail_formation/" + ide;
    }


    @RequestMapping(value = "/detail_formation/{id}", method = RequestMethod.GET)
    public String chefDepartement_Maquette(@PathVariable("id") Long id, Model model, Principal principal) {
        // Récupérer la Formation spécifique par son ID
        Formation formation = formationService.getById(id);  // Cette méthode doit récupérer formation par son ID
        model.addAttribute("formation", formation);

        // Récupérer les Classes associés à cette formation
        List<Classe> classes = classeService.listerParClasse(id);  // Cette méthode doit récupérer les Classes par l'ID de la formation
        model.addAttribute("classes", classes);


        // Récupérer les informations de l'utilisateur connecté
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));

        return "detail_formation";  // Retourner la page de détail de la formation
    }

    @GetMapping("/detail_formation")
    public String listerClasse(Model model) {
        List<Classe>  classes = classeService.lister();
        model.addAttribute("classes", classes);
        return "detail_formation";  // Assurez-vous que ce nom correspond à votre page Thymeleaf
    }



    @RequestMapping(value = "/ChefDepartement/archiverClasse", method = RequestMethod.POST)
    public String archiver_Classe(Classe classe) {
        classeService.archiver(classe.getId());
        Long ide = classe.getFormation().getId();
        return "redirect:/detail_formation/" + ide;
    }

}
