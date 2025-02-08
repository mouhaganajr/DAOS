package uasz.sn.Gestion_Enseignement.Authentification.service;

import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Role;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.Authentification.repository.RoleRepository;
import uasz.sn.Gestion_Enseignement.Authentification.repository.UtilisateurRepository;

@Service
@Transactional
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    public Utilisateur ajouter_Utilisateur(Utilisateur utilisateur) {
        utilisateurRepository.save(utilisateur);
        return utilisateur;
    }

    public Role ajouter_Role(Role role) {
        roleRepository.save(role);
        return role;
    }

    public void ajouter_UtilisateurRoles(Utilisateur utilisateur, Role role) {
        Utilisateur user = utilisateurRepository.findUtilisateurByUsername(utilisateur.getUsername());
        Role profil = roleRepository.findRoleByRole(role.getRole());
        user.getRoles().add(profil);
    }

    public Utilisateur rechercher_Utilisateur(String username) {
        return utilisateurRepository.findUtilisateurByUsername(username);
    }

    public void modifierUtilisateur(Utilisateur utilisateur) {
        Utilisateur user = utilisateurRepository.findUtilisateurByUsername(utilisateur.getUsername());
        if (user != null) {
            user.setNom(utilisateur.getNom());
            user.setPrenom(utilisateur.getPrenom());
            utilisateurRepository.save(user);
        } else {
            throw new RuntimeException("Inconnu : " + utilisateur.getUsername());
        }
    }
}

