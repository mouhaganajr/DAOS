package uasz.sn.Gestion_Enseignement.GestionMaquette.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.ECReposiroty;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.UERepository;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.ECService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.UEService;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
public class ECController {
    @Autowired
    private ECService ecService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private UEService ueService;
    @Autowired
    private UERepository ueRepository;
    @Autowired
    private ECReposiroty ecReposiroty;





    @RequestMapping(value = "/ChefDepartement/ajouterEC", method = RequestMethod.POST)
    public String ajouter_Ec( EC ec) {
            // Ajouter l'EC via le service
            ecService.ajouter_EC(ec);
            Long ide = ec.getUe().getId();
            // Rediriger vers la page de la liste des ECs
            return "redirect:/detail_ue/" + ide;

    }



    @RequestMapping(value = "/ChefDepartement/modifierEC", method = RequestMethod.POST)
    public String modifier_EC(EC ec) {
        ecService.modifier_EC(ec);
        Long ide = ec.getUe().getId();
        // Rediriger vers la page de la liste des ECs
        return "redirect:/detail_ue/" + ide;

    }



    @RequestMapping(value = "/detail_ue/{id}", method = RequestMethod.GET)
    public String chefDepartement_Maquette(@PathVariable("id") Long id, Model model, Principal principal) {
        // Récupérer l'UE spécifique par son ID
        UE ue = ueService.getById(id);  // Cette méthode doit récupérer l'UE par son ID
        model.addAttribute("ue", ue);

        // Récupérer les ECs associés à cette UE
        List<EC> ecs = ecService.listerParUE(id);  // Cette méthode doit récupérer les ECs par l'ID de l'UE
        model.addAttribute("ecs", ecs);


        // Récupérer les informations de l'utilisateur connecté
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));

        return "detail_ue";  // Retourner la page de détail de l'UE
    }

    @GetMapping("/detail_ue")
    public String listerEC(Model model) {
        List<EC> ecs = ecService.lister();
        model.addAttribute("ecs", ecs);
        return "detail_ue";  // Assurez-vous que ce nom correspond à votre page Thymeleaf
    }

    @RequestMapping(value = "/ChefDepartement/activerEC", method = RequestMethod.POST)
    public String activer_EC(EC ec) {
        // Activer l'EC via le service
        ecService.activer(ec.getId());

        // Récupérer l'ID de l'UE associée
        Long ide = ec.getUe().getId();

        // Rediriger vers la page de détails de l'UE
        return "redirect:/detail_ue/" + ide;
    }


    @RequestMapping(value = "/ChefDepartement/archiverEC", method = RequestMethod.POST)
    public String archiver_EC(EC ec) {
        ecService.archiver(ec.getId());
        Long ide = ec.getUe().getId();
        return "redirect:/detail_ue/" + ide;
    }
}
