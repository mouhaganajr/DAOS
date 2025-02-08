package uasz.sn.Gestion_Enseignement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import uasz.sn.Gestion_Enseignement.Authentification.modele.Role;
import uasz.sn.Gestion_Enseignement.Authentification.service.UtilisateurService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Classe;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.EC;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.Formation;
import uasz.sn.Gestion_Enseignement.GestionMaquette.modele.UE;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.ClasseService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.ECService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.FormationService;
import uasz.sn.Gestion_Enseignement.GestionMaquette.service.UEService;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Permanent;
import uasz.sn.Gestion_Enseignement.Utilisateur.modele.Vacataire;
import uasz.sn.Gestion_Enseignement.Utilisateur.service.EnseignantService;

import java.util.Date;

@SpringBootApplication
public class GestionEnseignementApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(GestionEnseignementApplication.class, args);
	}
	@Autowired
	private UtilisateurService utilisateurService;
	@Autowired
	private EnseignantService enseignantService;
	@Autowired
	UEService ueService;
	@Autowired
	ECService ecService;
	@Autowired
	private PasswordEncoder passwordEncoder;
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    @Autowired
    FormationService formationService;
	@Autowired
	ClasseService classeService;

	@Override
	public void run(String... args) throws Exception {
		Role permanent = utilisateurService.ajouter_Role(new Role("Permanent"));
		Role vacataire = utilisateurService.ajouter_Role(new Role("Vacataire"));
		Role chefDepartement = utilisateurService.ajouter_Role(new Role("ChefDepartement"));
		String password = passwordEncoder().encode("Passer123");

		Permanent user_1 = new Permanent();
		user_1.setNom("DIOP"); user_1.setPrenom("Ibrahima"); user_1.setUsername("idiop@uasz.sn");
		user_1.setPassword(password); user_1.setDateCreation(new Date()); user_1.setActive(true);
		user_1.setSpecialite("Web Sémantique");
		user_1.setMatricule("ID2024"); user_1.setGrade("Professeur");
		enseignantService.ajouter(user_1);
		utilisateurService.ajouter_UtilisateurRoles(user_1, permanent);

		Vacataire user_2 = new Vacataire();
		user_2.setNom("MALACK"); user_2.setPrenom("Camir"); user_2.setUsername("cmalack@uasz.sn");
		user_2.setPassword(password); user_2.setDateCreation(new Date()); user_2.setActive(true);
		user_2.setSpecialite("Ingénierie de Connaissances");
		user_2.setNiveau("Doctorant");
		enseignantService.ajouter(user_2);
		utilisateurService.ajouter_UtilisateurRoles(user_2, vacataire);

		Permanent user=new Permanent();
		user.setNom("DIAGNE"); user.setPrenom("Serigne"); user.setUsername("sdiagne@uasz.sn");
		user.setPassword(password); user.setDateCreation(new Date()); user.setActive(true);
		user.setSpecialite("Base de donnees");
		user.setMatricule("DS2024");
		user.setGrade("Professeur");
		enseignantService.ajouter(user);
		utilisateurService.ajouter_UtilisateurRoles(user, chefDepartement);


		// Save UE instances
		UE ue1 = new UE();
		ue1.setCode("INF351");
		ue1.setLibelle("Reseaux et Telecoms");
		ue1.setCredits(8);
		ue1.setCoeff_UE(4);
		ueService.ajouter_UE(ue1);  // Assuming this method persists Ue in the database

// Similarly, save other Ue instances
		UE ue2 = new UE();
		ue2.setCode("INF352");
		ue2.setLibelle("Genie Logiciel 1");
		ue2.setCredits(8);
		ue2.setCoeff_UE(4);
		ueService.ajouter_UE(ue2);

		// Similarly, save other Ue instances
		UE ue3 = new UE();
		ue3.setCode("INF353");
		ue3.setLibelle("Base de Donnees");
		ue3.setCredits(6);
		ue3.setCoeff_UE(3);
		ueService.ajouter_UE(ue3);


// Now create EC instances and associate them with the existing Ue instances
		EC ec1 = new EC();
		ec1.setCode("INF3511");
		ec1.setLibelle("Reseau sans fil");
		ec1.setCM(24);
		ec1.setTD(12);
		ec1.setTP(0);
		ec1.setTOTAL(36);
		ec1.setTPE(24);
		ec1.setVHT(60);
		ec1.setUe(ue1);  // Use the existing persisted Ue instance
		ec1.setCoeff(3);
		ecService.ajouter_EC(ec1);

		EC ec2 = new EC();
		ec2.setCode("INF3522");
		ec2.setLibelle("Genie Logiciel 1");
		ec2.setCM(20);
		ec2.setTD(14);
		ec2.setTP(5);
		ec2.setTOTAL(32);
		ec2.setTPE(22);
		ec2.setVHT(50);
		ec2.setUe(ue2);  // Use the existing persisted Ue instance
		ec2.setCoeff(4);
		ecService.ajouter_EC(ec2);


		EC ec3 = new EC();
		ec3.setCode("INF3523");
		ec3.setLibelle("ABD");
		ec3.setCM(18);
		ec3.setTD(14);
		ec3.setTP(12);
		ec3.setTOTAL(23);
		ec3.setTPE(21);
		ec3.setVHT(43);
		ec3.setUe(ue3);  // Use the existing persisted Ue instance
		ec3.setCoeff(2);
		ecService.ajouter_EC(ec3);

    //ajout Formation

        Formation formation = new Formation();
        formation.setNom("INGENIERIE INFORMATIQUE");
		formation.setNiveau("1er Annee");
        formationService.ajouter_Formation(formation);

		Formation formation1 = new Formation();
		formation1.setNom("MPI");
		formation1.setNiveau("2em Annee");
		formationService.ajouter_Formation(formation1);


	}


}
