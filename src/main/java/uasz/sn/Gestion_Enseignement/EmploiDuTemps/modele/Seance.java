package uasz.sn.Gestion_Enseignement.EmploiDuTemps.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;
import uasz.sn.Gestion_Enseignement.Repartition.modele.Choix;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Seance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String heureDebut;
    private String heureFin;
    private String jour; // Lundi, Mardi, etc.
    private String creneau; // 8h-10h, 10h-12h, etc.

    @ManyToMany(fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    @JoinTable(
            name = "seance_salle",  // Le nom de la table d'association
            joinColumns = @JoinColumn(name = "seance_id"),  // Colonne pour l'ID de la Maquette
            inverseJoinColumns = @JoinColumn(name = "salle_id")   // Colonne pour l'ID de l'UE
    )
    private List<Salle> salles = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @BatchSize(size = 10)
    @JoinTable(
            name = "seance_choix",  // Le nom de la table d'association
            joinColumns = @JoinColumn(name = "seance_id"),  // Colonne pour l'ID de la Maquette
            inverseJoinColumns = @JoinColumn(name = "choix_id")   // Colonne pour l'ID de l'UE
    )
    private List<Choix> choixes = new ArrayList<>();
}
