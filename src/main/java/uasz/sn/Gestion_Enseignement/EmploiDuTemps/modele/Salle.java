package uasz.sn.Gestion_Enseignement.EmploiDuTemps.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Salle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String numero;

    @ManyToOne
    @JoinColumn(name = "batiment_id")  // Optional: specifies the join column name
    private Batiment batiment;

    @ManyToMany(mappedBy = "salles")// Relation bidirectionnelle avec Maquette
    private List<Seance> seances = new ArrayList<>();
}
