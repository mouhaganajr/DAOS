package uasz.sn.Gestion_Enseignement.GestionMaquette.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.ECService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.UEService;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Permanent;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Vacataire;

import java.security.Principal;
import java.util.List;

@Controller
public class UEController {
    @Autowired
    private UEService ueService;
    @Autowired
    private UtilisateurService utilisateurService;



    @RequestMapping(value = "/ChefDepartement/ajouterUE", method = RequestMethod.POST)
    public String ajouter_UE(UE ue) {
        ueService.ajouter_UE(ue);
        return "redirect:/ChefDepartement/Maquette";
    }

    @RequestMapping(value = "/ChefDepartement/modifierUE", method = RequestMethod.POST)
    public String modifier_UE(UE ue) {
        ueService.modifier_UE(ue);
        return "redirect:/ChefDepartement/Maquette";
    }


    @RequestMapping(value = "/ChefDepartement/supprimerUE", method = RequestMethod.POST)
    public String supprimer_UE(UE ue) {
        ueService.supprimer_UE(ue.getId());
        return "redirect:/ChefDepartement/Maquette";
    }

    @RequestMapping(value = "/ChefDepartement/Maquette", method = RequestMethod.GET)
    public String chefDepartement_Maquette(Model model, Principal principal){
        List<UE> ues=ueService.lister();
        model.addAttribute("ues",ues);
        Utilisateur utilisateur=utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom",utilisateur.getNom());
        model.addAttribute("prenom",utilisateur.getPrenom().charAt(0));
        return "chefDepartement_Maquette";
    }


    @RequestMapping(value = "/ChefDepartement/activerUE", method = RequestMethod.POST)
    public String activer_UE(Long id) {
        ueService.activer(id);
        return "redirect:/ChefDepartement/Maquette";
    }

    @RequestMapping(value = "/ChefDepartement/archiverUE", method = RequestMethod.POST)
    public String archiver_UE(Long id) {
        ueService.archiver(id);
        return "redirect:/ChefDepartement/Maquette";
    }
}
