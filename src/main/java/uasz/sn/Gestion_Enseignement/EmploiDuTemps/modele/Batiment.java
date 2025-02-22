package uasz.sn.Gestion_Enseignement.EmploiDuTemps.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;

import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Batiment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    @OneToMany(mappedBy = "batiment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Collection<Salle> salles;
}
