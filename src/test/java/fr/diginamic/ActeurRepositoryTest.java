package fr.diginamic;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;

public class ActeurRepositoryTest {

	private static EntityManagerFactory emf;
	private EntityManager em;

	@Test
	public void testExtraireActeursTriesParIdentite() {
		TypedQuery<Acteur> query = em.createQuery("SELECT a FROM Acteur a ORDER BY a.identite", Acteur.class);
		List<Acteur> acteurs = query.getResultList();

		assertEquals(1137, acteurs.size());
		assertEquals("A.J. Danna", acteurs.get(0).getIdentite());
	}

	@Test
	public void testExtraireActeursParIdentite() {
		TypedQuery<Acteur> query = em.createQuery("SELECT a FROM Acteur a WHERE a.identite = :identite", Acteur.class);
		query.setParameter("identite", "Marion Cotillard");
		List<Acteur> acteurs = query.getResultList();

		assertEquals(1, acteurs.size());
		assertEquals("Marion Cotillard", acteurs.get(0).getIdentite());
	}

	@Test
	public void testExtraireActeursParAnneeNaissance() {
		TypedQuery<Acteur> query = em.createQuery("SELECT a FROM Acteur a WHERE YEAR(a.anniversaire) = :annee", Acteur.class);
		query.setParameter("annee", 1985);
		List<Acteur> acteurs = query.getResultList();

		assertEquals(10, acteurs.size());
	}

	@Test
	public void testExtraireActeursParRole() {
		TypedQuery<Acteur> query = em.createQuery("SELECT a FROM Acteur a JOIN a.roles r WHERE r.nom = :role", Acteur.class);
		query.setParameter("role", "Harley Quinn");
		List<Acteur> acteurs = query.getResultList();

		assertEquals(1, acteurs.size());
		assertEquals("Margot Robbie", acteurs.get(0).getIdentite());
	}
	
	@Test
	public void testExtraireActeursParFilmParuAnnee() {
		TypedQuery<Acteur> query = em.createQuery("SELECT DISTINCT a FROM Acteur a JOIN a.roles r JOIN r.film f WHERE f.annee = :annee", Acteur.class);
		query.setParameter("annee", 2015);
		List<Acteur> acteurs = query.getResultList();

		assertEquals(119, acteurs.size());
	}

	@Test
	public void testExtraireActeursParPays() {
		TypedQuery<Acteur> query = em.createQuery("SELECT DISTINCT a FROM Acteur a JOIN a.roles r JOIN r.film p join p.nom WHERE p.nom = :pays", Acteur.class);
		query.setParameter("pays", "France");
		List<Acteur> acteurs = query.getResultList();

		assertEquals(158, acteurs.size());
	}

	@Test
	public void testExtraireActeursParListePaysEtAnnee() {
		TypedQuery<Acteur> query = em.createQuery("SELECT DISTINCT a FROM Acteur a JOIN a.roles r JOIN r.film p JOIN p.nom WHERE p.nom = :pays AND p.annee = :annee", Acteur.class);
		query.setParameter("pays", "France");
		query.setParameter("annee", 2017);
		List<Acteur> acteurs = query.getResultList();

		assertEquals(24, acteurs.size());
	}

	@Test
	public void testExtraireParRealisateurEntreAnnee() {
		TypedQuery<Acteur> query = em.createQuery("SELECT DISTINCT a FROM Acteur a JOIN a.roles r JOIN r.film f JOIN f.realisateurs WHERE f.realisateurs.identite = :realisateur AND f.annee BETWEEN :debut AND :fin", Acteur.class);
		query.setParameter("realisateur", "Ridley Scott");
		query.setParameter("debut", 2010);
		query.setParameter("fin", 2020);
		List<Acteur> acteurs = query.getResultList();

		assertEquals(27, acteurs.size());
	}

	@Test
	public void testExtraireRealisateursParActeur() {
		TypedQuery<Realisateur> query = em.createQuery("SELECT DISTINCT r FROM Realisateur r JOIN r.film f JOIN f.acteurs a WHERE a.identite = :acteur", Realisateur.class);
		query.setParameter("acteur", "Brad Pitt");
		List<Realisateur> realisateurs = query.getResultList();

		assertEquals(6, realisateurs.size());
	}

	@BeforeEach
	public void ouvertureEm() {
		em = emf.createEntityManager();
	}

	@AfterEach
	public void fermetureEm() {
		em.close();
	}

	@BeforeAll
	public static void initDatabase() {
		emf = Persistence.createEntityManagerFactory("movie_db");
		EntityManager em = emf.createEntityManager();

		try {
			if (em.createQuery("FROM Acteur").getResultList().size() == 0) {
				em.getTransaction().begin();
				Path home = Paths.get(ActeurRepositoryTest.class.getClassLoader().getResource("data.sql").toURI());
				String[] queries = Files.readAllLines(home).stream().collect(Collectors.joining("\n")).split(";");
				for (String query : queries) {
					em.createNativeQuery(query).executeUpdate();
				}
				em.getTransaction().commit();
			}
		} catch (IOException | URISyntaxException e) {
			throw new RuntimeException(e);
		}
		em.close();
	}

	@AfterAll
	public static void fermetureRessources() {
		emf.close();
	}
}