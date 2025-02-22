package uasz.sn.Gestion_Enseignement.Repartition.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.Gestion_Enseignement.EmploiDuTemps.modele.Seance;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Choix {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "enseignant_id", nullable = false)
    private Enseignant enseignant;

    @ManyToOne
    @JoinColumn(name = "enseignement_id", nullable = false)
    private Enseignement enseignement;

    @ManyToMany(mappedBy = "choixes")// Relation bidirectionnelle avec Maquette
    private List<Seance> seances = new ArrayList<>();
}
