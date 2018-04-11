package be.vdab.allesvoordekeuken.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit4.SpringRunner;

import be.vdab.allesvoordekeuken.entities.Artikel;
import be.vdab.allesvoordekeuken.entities.FoodArtikel;
import be.vdab.allesvoordekeuken.entities.NonFoodArtikel;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JpaArtikelRepository.class)
public class JpaArtikelRepositoryTest {
	@Autowired
	private EntityManager manager;
	@Autowired
	private JpaArtikelRepository repository;
//	private Artikel artikel;
	private FoodArtikel foodArtikel;
	private NonFoodArtikel nonFoodArtikel;
//	@Before
//	public void before() {
//		artikel = new Artikel("test", BigDecimal.ONE, BigDecimal.TEN);
//	}
	@Before
	public void before() {
		foodArtikel = new FoodArtikel("testFood", BigDecimal.ONE, BigDecimal.TEN, 7);
		nonFoodArtikel = new NonFoodArtikel("testNonFood", BigDecimal.ONE, BigDecimal.TEN, 24);
	}
	
//	private long idVanNieuwArtikel() {
//		manager.createNativeQuery("insert into artikels(naam,aankoopprijs,verkoopprijs) values('test',100,120)")
//		.executeUpdate();
//		Number numberId = (Number) manager.createNativeQuery("select id from artikels where naam='test'").getSingleResult();
//		return numberId.longValue();
//	}
	
	// "Inheritance: Table per class"
	
	private long idVanNieuwFoodArtikel() {
		manager.createNativeQuery("insert into artikels(naam,aankoopprijs,verkoopprijs,soort,houdbaarheid) values('testFood',10,12,'F',5)").executeUpdate();
		return ((Number) manager.createNativeQuery("select id from artikels where naam='testFood'").getSingleResult()).longValue();
	}
	private long idVanNieuwNonFoodArtikel() {
		manager.createNativeQuery("insert into artikels(naam,aankoopprijs,verkoopprijs,soort,garantie) values('testNonFood',100,120,'NF',24)").executeUpdate();
		return ((Number) manager.createNativeQuery("select id from artikels where naam='testNonFood'").getSingleResult()).longValue();
	}
//	@Test
//	public void read() {
//		Optional<Artikel> optionalArtikel = repository.read(idVanNieuwArtikel());
//		assertTrue(optionalArtikel.isPresent());
//		assertEquals("test", optionalArtikel.get().getNaam());
//	}
	@Test
	public void readFoodArtikel() {
//		Optional<Artikel> optionalArtikel = repository.read(idVanNieuwFoodArtikel());	// (verbeterd:)
		FoodArtikel artikel = (FoodArtikel) repository.read(idVanNieuwFoodArtikel()).get();
		assertEquals("testFood", artikel.getNaam());
	}
	@Test
	public void readNonFoodArtikel() {
		NonFoodArtikel artikel = (NonFoodArtikel) repository.read(idVanNieuwNonFoodArtikel()).get();
		assertEquals("testNonFood", artikel.getNaam());
	}
	@Test
	public void createFood() {
		repository.create(foodArtikel);
		long autoNumberId = foodArtikel.getId();
		assertNotEquals(0, autoNumberId);
		assertEquals("testFood",
				(String) manager.createNativeQuery("select naam from artikels where id = :id")
				.setParameter("id", autoNumberId)
				.getSingleResult());
	}
	@Test
	public void createNonFood() {
		repository.create(nonFoodArtikel);
		assertEquals("testNonFood", (String) manager.createNativeQuery(
				"select naam from artikels where id = :id").setParameter("id", nonFoodArtikel.getId()).getSingleResult());
	}
	// *** Testen voor findByNaamContains method ***
	@Test
	public void findByNaamContains() {
		idVanNieuwFoodArtikel();
		List<Artikel> artikels = repository.findByNaamContains("e");
		// *** Juist aantal ***
		long aantal = ((Number) (manager.createNativeQuery("select count(*) from artikels where naam like '%e%'").getSingleResult())).longValue();
		assertEquals(aantal, artikels.size());
		// *** Alle namen bevatten de juiste string ***
		artikels.forEach(artikel -> assertTrue(artikel.getNaam().toLowerCase().contains("e")));
		// *** Juiste volgorde ***
		String vorigeNaam = "";
		for (Artikel artikel: artikels) {
			assertTrue(vorigeNaam.compareTo(artikel.getNaam()) <= 0);
			vorigeNaam = artikel.getNaam();
		}
	}
	/*
	 * Volgende tests zijn er terug uitgehaald omdat deze in de tests voor de service layer horen !
	 * De redenering is dat de repository layer niet controleert of de parameter bevat van de method findByNaamContains 
	 * niet leeg is en verschilt van null.
	 * Deze verantwoordelijkheid is voor de services layer.
	 */
//	@Test(expected = IllegalArgumentException.class)
//	public void findByNaamContainsMetLegeStringKanNiet() {
//		idVanNieuwArtikel();
//		repository.findByNaamContains("");
//	}
//	@Test(expected = NullPointerException.class)
//	public void findByNaamContainsMetNullKanNiet() {
//		idVanNieuwArtikel();
//		repository.findByNaamContains(null);
//	}
	// *** einde voor findByNaamContains ***
	@Test
	public void prijsVerhoging() {
		long id = idVanNieuwNonFoodArtikel();
		// Aantal gewijzigde records niet 0 (controle op de return-waarde van de method)
		int aantalAangepast = repository.prijsVerhoging(BigDecimal.TEN);
		assertNotEquals(0, aantalAangepast);
		// Prijs van toegevoegd record (eerste regel) is 132
		BigDecimal nieuwePrijs = BigDecimal.valueOf( 
				((Number) (manager.createNativeQuery("select verkoopprijs from artikels where id = :id")
				.setParameter("id", id)
				.getSingleResult())).doubleValue());
		assertEquals(0, BigDecimal.valueOf(132).compareTo(nieuwePrijs));
	}
}
