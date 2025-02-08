package uasz.sn.Gestion_Enseignement.Utilisateur.dtoRestController;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uasz.sn.Gestion_Enseignement.Utilisateur.dto.EtudiantDTO;
import uasz.sn.Gestion_Enseignement.Utilisateur.mapper.EtudiantMapper;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Etudiant;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.EtudiantService;

import java.util.List;

@CrossOrigin(origins = "*" , allowedHeaders = "*")
@RestController
@RequestMapping("/apiDTO")
public class EtudiantDTORestController {
    @Autowired
    private EtudiantService etudiantService;
    private EtudiantMapper etudiantMapper = Mappers.getMapper(EtudiantMapper.class);

    @PostMapping(path = "/etudiant")
    public ResponseEntity<EtudiantDTO> ajouter(@RequestBody EtudiantDTO etudiantDTO){
        Etudiant  etudiant = etudiantMapper.dtoToEtudiant(etudiantDTO);
        etudiantService.ajouter(etudiant);
        return ResponseEntity.status(HttpStatus.CREATED).body(etudiantDTO);
    }

    @GetMapping(path = "/etudiant/{id}")
    public ResponseEntity<EtudiantDTO> rechercher(@PathVariable Long id){
        Etudiant etudiant = etudiantService.rechercher(id);
        EtudiantDTO etudiantDTO = etudiantMapper.etudiantToDTO(etudiant);
        return ResponseEntity.ok(etudiantDTO);
    }

    @PutMapping(path = "/etudiant")
    public  ResponseEntity<EtudiantDTO> modifier(@RequestBody EtudiantDTO etudiantDTO){
      Etudiant etudiant = etudiantService.rechercher(etudiantDTO.getId());
      etudiant.setUsername(etudiantDTO.getUsername());
      etudiant.setNom(etudiantDTO.getNom());
      etudiant.setPrenom(etudiantDTO.getPrenom());
      etudiant.setMatricule(etudiantDTO.getMatricule());
      etudiantService.modifier(etudiant);
      return ResponseEntity.status(HttpStatus.ACCEPTED).body(etudiantDTO);
    }

    @DeleteMapping(path = "/etudiant/{id}")
    public ResponseEntity<?> supprimer(@PathVariable Long id){
        etudiantService.supprimer(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
    @GetMapping(path = "/etudiants")
    public ResponseEntity<List<EtudiantDTO>> lister(){
        List<EtudiantDTO> productList = etudiantMapper.etudiantToDTOs(etudiantService.lister());
        return ResponseEntity.ok(productList);
    }

}
