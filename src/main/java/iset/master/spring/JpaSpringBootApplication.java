package iset.master.spring;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import iset.master.spring.model.Produit;
import iset.master.spring.repository.ProduitRepository;

@SpringBootApplication
public class JpaSpringBootApplication {

	public static void main(String[] args) throws Exception {

		// 1. Référencer le contexte Spring
		ApplicationContext contexte =
				SpringApplication.run(JpaSpringBootApplication.class, args);

		// 2. Récupérer une instance de ProduitRepository par injection de dépendance
		ProduitRepository produitRepos =
				contexte.getBean(ProduitRepository.class);

		// 3. Insérer 3 produits
		produitRepos.save(new Produit("Yaourt", 0.400, 20));
		produitRepos.save(new Produit("Farine", 1.200, 30));
		produitRepos.save(new Produit("Chocolat", 2000.0, 5));


		List<Produit> lp = produitRepos.findAll();
		System.out.println("****** Liste des produits ****");
		for (Produit p : lp) {
			System.out.print("Designation: " + p.getDesignation() + " , ");
			System.out.println("Prix: " + p.getPrix());
		}
		System.out.println("-----------------------");

		// Lister les produits dont la désignation contient "h"
		System.out.println("****** Produits contenant 'h' ****");
		List<Produit> lp2 = produitRepos.findByDesignation("h");
		for (Produit p : lp2) {
			System.out.print("Designation: " + p.getDesignation() + " , ");
			System.out.println("Prix: " + p.getPrix());
		}
		System.out.println("-----------------------");

		// Produits contenant "o" avec prix > 1.0
		System.out.println("****** Produits contenant 'o' avec prix > 1.0 ****");
		List<Produit> lp3 = produitRepos.findByDesignationAndPrix("o", 1.0);
		for (Produit p : lp3) {
			System.out.print("Designation: " + p.getDesignation() + " , ");
			System.out.println("Prix: " + p.getPrix());
		}
		System.out.println("-----------------------");

		// Mettre à jour la désignation du produit id=2 (Farine → Pain)
		produitRepos.mettreAJourDesignation("Pain", 2L);

// Vérifier la modification
		Produit pm = produitRepos.findById(2L).get();
		if (pm != null) {
			System.out.println("Désignation mise à jour : " + pm.getDesignation());
		} else {
			System.out.println("Produit non existant...");
		}
		System.out.println("-----------------------");

		// Produits ayant un prix > 1.5
		System.out.println("****** Produits avec prix > 1.5 ****");
		List<Produit> lp4 = produitRepos.findByPrixGreaterThan(1.5);
		for (Produit p : lp4) {
			System.out.print("Designation: " + p.getDesignation() + " , ");
			System.out.println("Prix: " + p.getPrix());
		}
		System.out.println("-----------------------");

		// Tri par prix croissant
		System.out.println("****** Produits triés par prix croissant ****");
		List<Produit> lpTri = produitRepos.findAllByOrderByPrixAsc();
		for (Produit p : lpTri) {
			System.out.print("Designation: " + p.getDesignation() + " , ");
			System.out.println("Prix: " + p.getPrix());
		}
		System.out.println("-----------------------");

// ── Étape 11b : findByDateAchatAfter ─────────────────────────────
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

// 1. Insérer produits AVEC dateAchat EN PREMIER
		Produit p1 = new Produit("Lait", 0.900, 50);
		p1.setDateAchat(sdf.parse("2021-05-10"));   // après 2020 ✔
		produitRepos.save(p1);

		Produit p2 = new Produit("Beurre", 3.500, 15);
		p2.setDateAchat(sdf.parse("2019-01-20"));   // avant 2020 ✘
		produitRepos.save(p2);

// 2. Chercher APRÈS insertion
		Date dateRef = sdf.parse("2020-03-15");
		System.out.println("****** Produits achetés après 2020-03-15 ****");
		List<Produit> lpDate = produitRepos.findByDateAchatAfter(dateRef);
		for (Produit p : lpDate) {
			System.out.println("Designation: " + p.getDesignation()
					+ " , Date: " + p.getDateAchat());
		}
		System.out.println("-----------------------");

	}
}
