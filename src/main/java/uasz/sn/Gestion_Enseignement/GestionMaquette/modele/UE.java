package uasz.sn.Gestion_Enseignement.GestionMaquette.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UE {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String libelle;
    private String code;
    private String description;
    private int credits;
    private int coeff_UE;

    @ManyToMany(mappedBy = "ues")// Relation bidirectionnelle avec Maquette
    private List<Maquette> maquettes = new ArrayList<>();

    @OneToMany(mappedBy = "ue", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<EC> ecs;

    private boolean active;
    private boolean archive;
}
