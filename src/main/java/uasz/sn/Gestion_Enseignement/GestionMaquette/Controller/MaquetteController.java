package uasz.sn.Gestion_Enseignement.GestionMaquette.Controller;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.*;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.MaquetteRepository;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.UERepository;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.*;

import java.security.Principal;
import java.util.*;
@Controller
public class MaquetteController {

    @Autowired
    private MaquetteService maquetteService;
    @Autowired
    private UtilisateurService utilisateurService;
    @Autowired
    private FormationService formationService;
    @Autowired
    private UEService ueService;
    @Autowired
    private ECService ecService;
    @Autowired
    private UERepository ueRepository;
    @Autowired
    private  MaquetteRepository maquetteRepository;

    // Méthode pour ajouter des UEs à une Maquette pour un semestre spécifique

    @RequestMapping(value = "/ChefDepartement/ajouterMaquette", method = RequestMethod.POST)
    public String ajouterMaquette(@RequestParam Long id,
                                  @RequestParam("ueIds") List<Long> ueIds,
                                  @RequestParam("semestre") int semestre,  // Récupère le semestre envoyé par le formulaire
                                  Model model) {

        Formation formation = formationService.getById(id);

        if (semestre != 1 && semestre != 2) {
            return "redirect:/ChefDepartement/Formation";
        }

        // Vérifier si une maquette existe déjà pour ce semestre
        Maquette maquette = maquetteService.rechercherMaquetteParSemestre(formation, semestre);
        if (maquette == null) {
            maquette = new Maquette();
            maquette.setFormation(formation);
            maquette.setSemestre(semestre);  // Assigner le bon semestre
            maquette = maquetteRepository.save(maquette); // Sauvegarder immédiatement pour obtenir un ID
        }

        // Charger toutes les UE disponibles pour l'affichage
        List<UE> ues = ueService.lister();
        model.addAttribute("ues", ues);
        model.addAttribute("maquette", maquette);

        // Trouver les UE sélectionnées
        List<UE> selectedUes = ueRepository.findAllById(ueIds);

        // Vérifier et ajouter les UE sélectionnées
        for (UE ue : selectedUes) {
            if (!maquette.getUes().contains(ue)) {
                maquette.getUes().add(ue);
            }
        }

        // Sauvegarder la maquette mise à jour
        maquetteRepository.save(maquette);

        // Rediriger vers la page de la maquette après l'ajout
        return "redirect:/maquette/" + formation.getId() + "?semestre=" + semestre;
    }




    // Méthode pour afficher la maquette spécifique d'une formation et d'un semestre
    @RequestMapping(value = "/maquette/{id}", method = RequestMethod.GET)
    public String chefDepartement_Maquette(@PathVariable("id") Long id,
                                           @RequestParam(value = "semestre", required = false, defaultValue = "1") int semestre,
                                           Model model,
                                           Principal principal) {
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
        model.addAttribute("semestreActuel", semestre);

        // Récupérer les UEs disponibles
        List<UE> ues = ueService.lister();
        model.addAttribute("ues", ues);

        // Récupérer les informations de l'utilisateur connecté
        Utilisateur utilisateur = utilisateurService.rechercher_Utilisateur(principal.getName());
        model.addAttribute("nom", utilisateur.getNom());
        model.addAttribute("prenom", utilisateur.getPrenom().charAt(0));

        return "maquette";
    }


}







