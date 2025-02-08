package uasz.sn.Gestion_Enseignement.GestionMaquette.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String semestre;
    @OneToOne
    private Maquette maquette;
    @ManyToOne
    @JoinColumn(name = "formation_id")  // Optional: specifies the join column name
    private Formation formation;
    private boolean archive;
}
