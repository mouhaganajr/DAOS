package uasz.sn.Gestion_Enseignement.GestionMaquette.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Formation;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.ClasseService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.FormationService;

import java.security.Principal;
import java.util.List;

@Controller
public class FormationController {
    @Autowired
    private FormationService formationService;
    @Autowired
    private UtilisateurService utilisateurService;






    @RequestMapping(value = "/ChefDepartement/ajouterFormation", method = RequestMethod.POST)
    public String ajouter_Formation(Formation formation) {
        formationService.ajouter_Formation(formation);
        return "redirect:/ChefDepartement/Formation";
    }

    @RequestMapping(value = "/ChefDepartement/modifierFormation", method = RequestMethod.POST)
    public String modifier_Formation(Formation formation) {
        formationService.modifier_Formation(formation);
        return "redirect:/ChefDepartement/Formation";
    }


    @RequestMapping(value = "/ChefDepartement/supprimerFormation", method = RequestMethod.POST)
    public String supprimer_Formation(Formation formation) {
        formationService.supprimer_Formation(formation.getId());
        return "redirect:/ChefDepartement/Formation";
    }

    @RequestMapping(value = "/ChefDepartement/Formation", method = RequestMethod.GET)
    public String chefDepartement_Formation(Model model, Principal principal){
        List<Formation> formations=formationService.lister();
        model.addAttribute("formations",formations);
        Utilisateur utilisateur=utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom",utilisateur.getNom());
        model.addAttribute("prenom",utilisateur.getPrenom().charAt(0));
        return "chefDepartement_Formation";
    }



    @RequestMapping(value = "/ChefDepartement/archiverFormation", method = RequestMethod.POST)
    public String archiver_Formation(Long id){
       formationService.archiver(id);
        return "redirect:/ChefDepartement/Formation";
    }

}
