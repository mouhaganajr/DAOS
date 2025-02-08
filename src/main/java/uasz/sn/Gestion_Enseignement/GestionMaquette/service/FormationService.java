package uasz.sn.Gestion_Enseignement.GestionMaquette.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Formation;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.ECReposiroty;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.FormationRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FormationService {
    @Autowired
    private FormationRepository formationRepository;
    public Formation ajouter_Formation(Formation formation){
        return formationRepository.save(formation);
    }
    public List<Formation> lister(){
        return formationRepository.findAll();
    }
    public Formation rechercher_Formation(Long id){
        return formationRepository.findById(id).get();
    }
    public Formation modifier_Formation(Formation formation){
        return formationRepository.save(formation);
    }
    public void supprimer_Formation(Long id){
        formationRepository.deleteById(id);
    }

    public Formation getById(Long id) {
        Optional<Formation> formation = formationRepository.findById(id);
        return formation.orElse(null); // Retourne l'objet UE ou null si non trouvé
    }

    public void archiver(Long id){
        Formation formation=formationRepository.findById(id).get();
        if (formation.isArchive()==true){
            formation.setArchive(false);
        }
        else {
            formation.setArchive(true);
        }
        formationRepository.save(formation);
    }
}
