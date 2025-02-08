package uasz.sn.Gestion_Enseignement.GestionMaquette.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.UERepository;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
public class UEService {
    @Autowired
    private UERepository ueRepository;
    public UE ajouter_UE(UE ue){
        return ueRepository.save(ue);
    }
    public List<UE> lister(){
        return ueRepository.findAll();
    }
    public UE rechercher_UE(Long id){
        return ueRepository.findById(id).get();
    }
    public UE modifier_UE(UE ue){
        return ueRepository.save(ue);
    }
    public void supprimer_UE(Long id){
        ueRepository.deleteById(id);
    }


    public List<UE> listerParIds(List<Long> ueIds) {
        return ueRepository.findAllById(ueIds);
    }

    public List<UE> listerParId(List<Long> ueIds) {
        // Récupérer toutes les UEs
        List<UE> toutesLesUes = ueRepository.findAll(); // Méthode JPA pour récupérer toutes les UEs

        // Filtrer les UEs sélectionnées par leur ID
        if (ueIds != null && !ueIds.isEmpty()) {
            return toutesLesUes.stream()
                    .filter(ue -> ueIds.contains(ue.getId())) // Garder les UEs dont l'ID est dans ueIds
                    .collect(Collectors.toList());
        }
        return toutesLesUes; // Si aucune UE n'est sélectionnée, retourner toutes les UEs
    }

    // Méthode pour récupérer l'ID de l'UE s'il existe

    public UE getById(Long id) {
        Optional<UE> ue = ueRepository.findById(id);
        return ue.orElse(null); // Retourne l'objet UE ou null si non trouvé
    }
    public Optional<UE> findOptionalById(Long id) {
        return ueRepository.findById(id);
    }
    public boolean existsById(Long id) {
        return ueRepository.existsById(id);
    }


    public List<UE> listerParMaquette(Long idUE) {
        // Implémentation de la récupération des ECs associés à l'UE par son ID
        return ueRepository.findByMaquettesId(idUE);  // Exemple avec une méthode dans le repository
    }

    public void activer(Long id){
        UE ue=ueRepository.findById(id).get();
        if (ue.isActive()==true){
            ue.setActive(false);
        }
        else {
            ue.setActive(true);
        }
        ueRepository.save(ue);
    }

    public void archiver(Long id){
        UE ue=ueRepository.findById(id).get();
        if (ue.isArchive()==true){
            ue.setArchive(false);
        }
        else {
            ue.setArchive(true);
        }
        ueRepository.save(ue);
    }
}
