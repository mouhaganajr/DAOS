package uasz.sn.Gestion_Enseignement.GestionMaquette.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Formation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String Niveau;
    @OneToMany
    private Collection<Classe> classes;
    @OneToMany
    private Collection<Maquette> maquettes;
    private boolean archive;
}
