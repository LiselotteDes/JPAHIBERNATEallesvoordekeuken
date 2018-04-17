package be.vdab.allesvoordekeuken.entities;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class ArtikelTest {
	private Artikel artikel1, nogEensArtikel1, artikel2;
	private ArtikelGroep artikelGroep1, artikelGroep2;
	
	@Before
	public void before() {
		artikelGroep1 = new ArtikelGroep("test");
		artikelGroep2 = new ArtikelGroep("test2");
		artikel1 = new FoodArtikel("test", BigDecimal.ONE, BigDecimal.TEN, artikelGroep1, 1);
		nogEensArtikel1 = new FoodArtikel("test", BigDecimal.ONE, BigDecimal.TEN, artikelGroep1, 7);
		artikel2 = new FoodArtikel("test2", BigDecimal.ONE, BigDecimal.TEN, artikelGroep1, 1);
	}
	// *** EQUALS & HASHCODE ***
	
	@Test
	public void tweeArtikelsZijnGelijkAlsHunNamenGelijkZijn() {
		assertEquals(artikel1, nogEensArtikel1);
	}
	@Test
	public void tweeArtikelsZijnVerschillendAlsHunNamenVerschillen() {
		assertNotEquals(artikel1, artikel2);
	}
	@Test
	public void eenArtikelIsVerschillendVanNull() {
		assertNotEquals(artikel1, null);
	}
	@Test
	public void eenArtikelIsVerschillendVanEenObjectVanEenAnderType() {
		assertNotEquals(artikel1, "");
	}
	@Test
	public void tweeGelijkeArtikelsHebbenDezelfdeHashCode() {
		assertEquals(artikel1.hashCode(), nogEensArtikel1.hashCode());
	}
	
	@Test
	public void eenArtikelGroepKanMeerdereArtikelsBevatten() {
		assertTrue(artikelGroep1.getArtikels().contains(artikel1));
		assertTrue(artikelGroep1.getArtikels().contains(artikel2));
	}
	
	// *** BIDIRECTIONELE ASSOCIATIE VANUIT HET STANDPUNT VAN ARTIKEL ***
	
	@Test
	public void artikelGroep1EnArtikel1ZijnGoedVerbonden() {
		// *** het juiste aantal artikels is verbonden met artikelGroep1 ***
		assertEquals(2, artikelGroep1.getArtikels().size());
		// *** artikel1 zit in de set artikels van artikelGroep1 ***
		assertTrue(artikelGroep1.getArtikels().contains(artikel1));
		// *** de private variabele artikelGroep van artikel1 verwijst naar artikelGroep1 ***
		assertEquals(artikelGroep1, artikel1.getArtikelGroep());
	}
	@Test
	public void artikel1VerandertVanArtikelGroep1NaarArtikelGroep2() {
		artikel1.setArtikelGroep(artikelGroep2);
		assertEquals(1, artikelGroep1.getArtikels().size());
		assertTrue(artikelGroep2.getArtikels().contains(artikel1));
		assertEquals(artikelGroep2, artikel1.getArtikelGroep());
	}
}
