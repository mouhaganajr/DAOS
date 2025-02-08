package uasz.sn.Gestion_Enseignement.GestionMaquette.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Formation;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Maquette;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.MaquetteRepository;
import uasz.sn.Gestion_Enseignement.GestionMaquette.repository.UERepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MaquetteService {

    @Autowired
    private MaquetteRepository maquetteRepository;

    @Autowired
    private UERepository ueRepository;

    // Méthode pour ajouter des UEs à une Maquette

    public void ajouterUes(Long formationId, int semestre, List<Long> ueIds, Formation formation) {
        Maquette maquette = maquetteRepository.findByFormationAndSemestre(formation, semestre)
                .orElseGet(() -> {
                    Maquette newMaquette = new Maquette();
                    newMaquette.setFormation(formation);
                    newMaquette.setSemestre(semestre);
                    return maquetteRepository.save(newMaquette);
                });

        List<UE> ues = ueRepository.findAllById(ueIds);

        for (UE ue : ues) {
            if (!maquette.getUes().contains(ue)) {
                Collection<EC> ecs = ue.getEcs(); // Supposons que la classe UE ait une méthode getEcs()
                ue.getEcs().addAll(ecs);
                maquette.getUes().add(ue);
            }
        }

        maquetteRepository.save(maquette);
    }





    public void ajouterUe(Long formationId, int semestre, List<Long> ueIds, Formation formation) {
        // Recherche d'une maquette existante pour la formation et le semestre         // Si la maquette n'existe pas pour ce semestre, créez une nouvelle maquette
            Maquette nouvelleMaquette = new Maquette();
            nouvelleMaquette.setFormation(formation);
            nouvelleMaquette.setSemestre(semestre);



        // Ajout des UEs à la maquette
        List<UE> ues = ueRepository.findAllById(ueIds);
        System.out.println("Ajout de " + ues.size() + " UEs à la maquette pour le semestre " + semestre);

        // Récupérer et associer les ECs liés à ces UEs
        for (UE ue : ues) {
            Collection<EC> ecs = ue.getEcs(); // Supposons que la classe UE ait une méthode getEcs()
            System.out.println("Ajout de " + ecs.size() + " ECs pour l'UE " + ue.getLibelle());
            nouvelleMaquette.getUes().add(ue); // Ajouter l'UE à la maquette
        }

        // Sauvegarder ou mettre à jour la maquette
        maquetteRepository.save(nouvelleMaquette);
    }




    public Maquette ajouter_Maquette(Maquette maquette) {
        return maquetteRepository.save(maquette);
    }

    public List<Maquette> lister() {
        return maquetteRepository.findAll();
    }




    public Maquette rechercherMaquetteParSemestre(Formation formation, int semestre) {
        return maquetteRepository.findByFormationAndSemestre(formation, semestre)
                .orElse(null); // Retourne null si aucune maquette n'est trouvée
    }

    public List<UE> getUEsBySemestre(Formation formation, int semestre) {
        Maquette maquette = maquetteRepository.findByFormationAndSemestre(formation, semestre)
                .orElse(null);
        return (maquette != null) ? new ArrayList<>(maquette.getUes()) : new ArrayList<>();
    }

    public Maquette getMaquettesBySemestre(int semestre) {
        return maquetteRepository.findBySemestre(semestre)
                .orElse(null);
    }




    public Maquette rechercher(Long id){
        List<Maquette> maquettes = maquetteRepository.findByFormationId(id);
        if (maquettes.isEmpty()){
            throw  new EntityNotFoundException(" Aucun maquette");
        }
        return maquettes.get(0);
    }



    public Maquette modifier_Maquette(Maquette maquette) {
        return maquetteRepository.save(maquette);
    }

    public List<Maquette> listerParMaquette(Long id) {
        return maquetteRepository.findByFormationId(id);
    }

    public List<Maquette> listerParUE(Long id) {
        return maquetteRepository.findByFormationId(id);
    }

    public void activer(Long id) {
        Maquette maquette = maquetteRepository.findById(id).get();
        maquette.setActive(!maquette.isActive());
        maquetteRepository.save(maquette);
    }

    public void archiver(Long id) {
        Maquette maquette = maquetteRepository.findById(id).get();
        maquette.setArchive(!maquette.isArchive());
        maquetteRepository.save(maquette);
    }
}
