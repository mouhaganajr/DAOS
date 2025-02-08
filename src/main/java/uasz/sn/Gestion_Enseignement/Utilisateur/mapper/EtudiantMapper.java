package uasz.sn.Gestion_Enseignement.Utilisateur.mapper;

import org.mapstruct.Mapper;
import uasz.sn.Gestion_Enseignement.Utilisateur.dto.EtudiantDTO;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Etudiant;

import java.util.List;

@Mapper
public interface EtudiantMapper {
    EtudiantDTO etudiantToDTO(Etudiant etudiant);
    Etudiant dtoToEtudiant(EtudiantDTO etudiantDTO);
    List<EtudiantDTO> etudiantToDTOs(List<Etudiant> etudiants);
}
