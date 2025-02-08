package uasz.sn.Gestion_Enseignement.GestionMaquette.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.ClasseRepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Permanent;
import uasz.sn.Gestion_Enseignement.Utilisateur.repository.PermanentRepository;

import java.util.List;

@Service
@Transactional
public class ClasseService {
    @Autowired
    private ClasseRepository classeRepository;
    public Classe ajouter_Classe(Classe classe){
        return classeRepository.save(classe);
    }
    public List<Classe> lister(){
        return classeRepository.findAll();
    }
    public Classe rechercher_Classe(Long id){
        return classeRepository.findById(id).get();
    }
    public Classe modifier_Classe(Classe classe){
        return classeRepository.save(classe);
    }
    public void supprimer_Classe(Long id){
        classeRepository.deleteById(id);
    }

    public List<Classe> listerParClasse(Long id) {
        // Implémentation de la récupération des ECs associés à l'UE par son ID
        return classeRepository.findByFormationId(id);  // Exemple avec une méthode dans le repository
    }

    public Classe getById(Long id) {
        // Retourner l'EC si trouvé, sinon null
        return classeRepository.findById(id).orElse(null);
    }

    public void archiver(Long id){
        Classe classe =classeRepository.findById(id).get();
        if (classe.isArchive()==true){
            classe.setArchive(false);
        }
        else {
            classe.setArchive(true);
        }
        classeRepository.save(classe);
    }
}
