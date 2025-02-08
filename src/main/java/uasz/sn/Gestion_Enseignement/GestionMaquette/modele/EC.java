package uasz.sn.Gestion_Enseignement.GestionMaquette.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.Gestion_Enseignement.Repartition.modele.Enseignement;

import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String libelle;
    @Column(unique = true)
    private String code;
    private int TD;
    private int TP;
    private int CM;
    private int TOTAL;
    private int TPE;
    private int VHT;
    private int coeff;
    private  String description;
    @ManyToOne
    @JoinColumn(name = "ue_id")  // Optional: specifies the join column name
    private UE ue;

    @OneToMany(mappedBy = "ec", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Enseignement> enseignements;

    private boolean active;
    private boolean archive;
}
