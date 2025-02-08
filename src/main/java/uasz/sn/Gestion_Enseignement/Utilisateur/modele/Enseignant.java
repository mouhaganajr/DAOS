package uasz.sn.Gestion_Enseignement.Utilisateur.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Utilisateur;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Maquette;
import uasz.sn.Gestion_Enseignement.Repartition.modele.Enseignement;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "id")
@EqualsAndHashCode(callSuper = true) // Prend en compte la classe parent Utilisateur

@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Enseignant extends Utilisateur {

    private String specialite;
    private boolean archive;

    @ManyToMany(mappedBy = "enseignants")// Relation bidirectionnelle avec Maquette
    private List<Enseignement> enseignements = new ArrayList<>();
}
