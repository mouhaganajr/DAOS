package uasz.sn.Gestion_Enseignement.EmploiDuTemps.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.modele.Salle;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.modele.Seance;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.repository.Sallerepository;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.repository.seancerepository;
import uasz.sn.Gestion_Enseignement.Repartition.modele.Choix;
import uasz.sn.Gestion_Enseignement.Repartition.repository.Choixrepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class seanceservice {

    @Autowired
    private seancerepository seancerepository;

    @Autowired
    private Choixrepository choixrepository;

    @Autowired
    private Sallerepository sallerepository;

    private static final List<String> JOURS = Arrays.asList("Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi");

    private static final List<String> CRENEAUX = Arrays.asList("8h-10h", "10h-12h", "15h-17h", "17h-19h");

    public void ajouterseance(List<Long> choixIds, List<Long> salleIds, String Heurededebut, String Heuredefin) {
        List<Choix> choixes = choixrepository.findAllById(choixIds);
        List<Salle> salles = sallerepository.findAllById(salleIds);

        Seance seance = new Seance();
        seance.setChoixes(choixes);
        seance.setSalles(salles);
        seance.setHeureDebut(Heurededebut);
        seance.setHeureFin(Heuredefin);

        seancerepository.save(seance);
    }

    public List<Seance> lister() {
        return seancerepository.findAll();
    }

    public Seance recupererSeanceParId(Long id) {
        return seancerepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Séance non trouvée avec l'ID : " + id));
    }

    public List<Seance> listerAvecRelations() {
        return seancerepository.findAllWithRelations();
    }

    public void creerSeanceComplete(List<Long> choixIds, List<Long> salleIds, String jour, String creneau, String Heurededebut, String Heuredefin) {
        List<Choix> choixes = choixrepository.findAllById(choixIds);
        List<Salle> salles = sallerepository.findAllById(salleIds);

        Seance seance = new Seance();
        seance.setJour(jour);
        seance.setCreneau(creneau);
        seance.setChoixes(choixes);
        seance.setSalles(salles);
        seance.setHeureDebut(Heurededebut);
        seance.setHeureFin(Heuredefin);
        seancerepository.save(seance);
    }

    private void mergeSalles(List<Seance> target, List<Seance> source) {
        Map<Long, List<Salle>> sallesMap = source.stream()
                .collect(Collectors.toMap(Seance::getId, Seance::getSalles));

        target.forEach(seance -> seance.setSalles(sallesMap.getOrDefault(seance.getId(), List.of())));
    }

    public Map<String, Map<String, List<Seance>>> getSeancesStructurees() {
        List<Seance> seances = findAllWithRelations();
        return buildStructure(seances);
    }

    private Map<String, Map<String, List<Seance>>> buildStructure(List<Seance> seances) {
        Map<String, Map<String, List<Seance>>> structure = new LinkedHashMap<>();

        JOURS.forEach(jour -> {
            structure.put(jour, CRENEAUX.stream()
                    .collect(Collectors.toMap(creneau -> creneau, creneau -> new ArrayList<>(), (a, b) -> a, LinkedHashMap::new)));
        });

        seances.forEach(seance -> {
            Optional.ofNullable(structure.get(seance.getJour()))
                    .flatMap(creneauMap -> Optional.ofNullable(creneauMap.get(seance.getCreneau())))
                    .ifPresent(list -> list.add(seance));
        });

        return structure;
    }

    public List<Seance> findAllWithRelations() {
        List<Seance> seancesAvecSalles = seancerepository.findAllWithSalles();
        List<Seance> seancesAvecChoixes = seancerepository.findAllWithChoixes();

        Map<Long, Seance> seanceMap = new HashMap<>();

        for (Seance s : seancesAvecSalles) {
            seanceMap.put(s.getId(), s);
        }

        for (Seance s : seancesAvecChoixes) {
            Seance seanceExistante = seanceMap.get(s.getId());
            if (seanceExistante != null) {
                seanceExistante.getChoixes().addAll(s.getChoixes());
            } else {
                seanceMap.put(s.getId(), s);
            }
        }

        return new ArrayList<>(seanceMap.values());
    }




}
