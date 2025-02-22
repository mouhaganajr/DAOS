package uasz.sn.Gestion_Enseignement.EmploiDuTemps.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.modele.Batiment;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.service.Batimentservice;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.UEService;

import java.security.Principal;
import java.util.List;

@Controller
public class BatimentController {

    @Autowired
    private Batimentservice batimentservice;
    @Autowired
    private UtilisateurService utilisateurService;



    @RequestMapping(value = "/ChefDepartement/ajouterBatiment", method = RequestMethod.POST)
    public String ajouter_Batiment(Batiment batiment) {
        batimentservice.ajouter_Batiment(batiment);
        return "redirect:/ChefDepartement/Emploidutemps";
    }



    @RequestMapping(value = "/ChefDepartement/Emploidutemps", method = RequestMethod.GET)
    public String chefDepartement_Emploidutemps(Model model, Principal principal){
        List<Batiment> batiments=batimentservice.lister();
        model.addAttribute("batiments",batiments);
        Utilisateur utilisateur=utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom",utilisateur.getNom());
        model.addAttribute("prenom",utilisateur.getPrenom().charAt(0));
        return "chefDepartement_Emploidutemps";
    }

}
