package uasz.sn.Gestion_Enseignement.Repartition.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Maquette;
import uasz.sn.Gestion_Enseignement.Repartition.modele.Choix;
import uasz.sn.Gestion_Enseignement.Repartition.modele.Enseignement;
import uasz.sn.Gestion_Enseignement.Repartition.modele.StatutEnseignement;
import uasz.sn.Gestion_Enseignement.Repartition.repository.Choixrepository;
import uasz.sn.Gestion_Enseignement.Repartition.repository.Enseignementrepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.repository.EnseignantRepository;

import java.util.List;

@Transactional
@Service
public class Choixservice {

    @Autowired
    private Choixrepository choixrepository;
    @Autowired
    private Enseignementrepository enseignementrepository;
    @Autowired
    private EnseignantRepository enseignantRepository;


    // Lister les choix enregistrés
    public List<Choix> getAllChoix() {
        return choixrepository.findAll();
    }

    // Accepter un choix
    @Transactional
    public Choix accepterChoix(Long enseignantId, Long enseignementId) {
        Enseignant enseignant = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant non trouvé"));

        Enseignement enseignement = enseignementrepository.findById(enseignementId)
                .orElseThrow(() -> new RuntimeException("Enseignement non trouvé"));
        enseignement.setStatut(StatutEnseignement.ACCEPTE);

        Choix choix = new Choix();
        choix.setEnseignant(enseignant);
        choix.setEnseignement(enseignement);

        return choixrepository.save(choix);
    }



    // Refuser un choix (Supprimer l'entrée si elle existe)
    @Transactional
    public void refuserChoix(Long enseignantId, Long enseignementId) {
        Enseignant enseignant = enseignantRepository.findById(enseignantId)
                .orElseThrow(() -> new RuntimeException("Enseignant non trouvé"));

        Enseignement enseignement = enseignementrepository.findById(enseignementId)
                .orElseThrow(() -> new RuntimeException("Enseignement non trouvé"));
        enseignement.setStatut(StatutEnseignement.REFUSE);
        enseignementrepository.save(enseignement);


        enseignementrepository.deleteById(enseignementId);

    }
}


