package uasz.sn.Gestion_Enseignement.Repartition.modele;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Maquette;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Enseignant;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Enseignement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "maquette_id")  // Optional: specifies the join column name
    private Maquette maquette;

    @ManyToOne
    @JoinColumn(name = "ec_id")  // Optional: specifies the join column name
    private EC ec;

    @ManyToMany
    @JoinTable(
            name = "enseignement_enseignant",  // Le nom de la table d'association
            joinColumns = @JoinColumn(name = "enseignement_id"),  // Colonne pour l'ID de la Maquette
            inverseJoinColumns = @JoinColumn(name = "enseignant_id")   // Colonne pour l'ID de l'UE
    )
    private List<Enseignant> enseignants = new ArrayList<>();

    @Pattern(regexp = "CM|TD|TP", message = "Type d'enseignement invalide !")
    private String typeEnseignement;

    @Enumerated(EnumType.STRING)
    private StatutEnseignement statut = StatutEnseignement.EN_ATTENTE; // Valeur par défaut

    @OneToMany(mappedBy = "enseignement", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Choix> choixes;
}
