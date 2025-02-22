package uasz.sn.Gestion_Enseignement.EmploiDuTemps.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.modele.Batiment;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.modele.Salle;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.service.Batimentservice;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.service.Salleservice;


import java.security.Principal;
import java.util.List;

@Controller
public class SalleController {

    @Autowired
    private Salleservice salleservice;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private Batimentservice batimentservice;
    @RequestMapping(value = "/ChefDepartement/ajouterSalle", method = RequestMethod.POST)
    public String ajouter_Salle( Salle salle) {
        // Ajouter l'EC via le service
        salleservice.ajouter_Salle(salle);
        Long ide = salle.getBatiment().getId();
        // Rediriger vers la page de la liste des ECs
        return "redirect:/detail_emploidutemps/" + ide;

    }




    @RequestMapping(value = "/detail_emploidutemps/{id}", method = RequestMethod.GET)
    public String chefDepartement_Emploidutemps(@PathVariable("id") Long id, Model model, Principal principal) {
        // Récupérer l'UE spécifique par son ID
        Batiment batiment = batimentservice.getById(id);  // Cette méthode doit récupérer l'UE par son ID
        model.addAttribute("batiment", batiment);

        // Récupérer les ECs associés à cette UE
        List<Salle> salles = salleservice.listerParBatiment(id);  // Cette méthode doit récupérer les ECs par l'ID de l'UE
        model.addAttribute("salles", salles);


        // Récupérer les informations de l'utilisateur connecté
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));

        return "detail_emploidutemps";  // Retourner la page de détail de l'UE
    }

}
