package uasz.sn.Gestion_Enseignement.GestionMaquette.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.Gestion_Enseignement.Repartition.modele.Enseignement;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Maquette {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int semestre;

    @ManyToMany
    @JoinTable(
            name = "maquette_ue",  // Le nom de la table d'association
            joinColumns = @JoinColumn(name = "maquette_id"),  // Colonne pour l'ID de la Maquette
            inverseJoinColumns = @JoinColumn(name = "ue_id")   // Colonne pour l'ID de l'UE
    )
    private List<UE> ues = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "formation_id") // Associe une formation à une maquette
    private Formation formation;


    @OneToMany(mappedBy = "maquette", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Enseignement> enseignements;

    private boolean active;
    private boolean archive;
}
