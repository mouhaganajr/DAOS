package uasz.sn.Gestion_Enseignement.Utilisateur.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;
import uasz.sn.Gestion_Enseignement.Utilisateur.repository.EnseignantRepository;

import java.util.List;

@Service
@Transactional
public class EnseignantService {
    @Autowired
    private EnseignantRepository enseignantRepository;
    public void ajouter(Enseignant enseignant){
        enseignantRepository.save(enseignant);
    }
    public List<Enseignant> lister(){
        return enseignantRepository.findAll();
    }

    public Enseignant rechercher(Long id){
        return enseignantRepository.findById(id).get();
    }

    public void modifier(Enseignant enseignant){
         enseignantRepository.save(enseignant);
    }
    public void activer(Long id){
        Enseignant enseignant=enseignantRepository.findById(id).get();
        if (enseignant.isActive()==true){
            enseignant.setActive(false);
        }
        else {
            enseignant.setActive(true);
        }
        enseignantRepository.save(enseignant);
    }

    public void archiver(Long id){
        Enseignant enseignant=enseignantRepository.findById(id).get();
        if (enseignant.isArchive()==true){
            enseignant.setArchive(false);
        }
        else {
            enseignant.setArchive(true);
        }
        enseignantRepository.save(enseignant);
    }
}
