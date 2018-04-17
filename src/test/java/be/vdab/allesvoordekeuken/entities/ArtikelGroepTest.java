package be.vdab.allesvoordekeuken.entities;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class ArtikelGroepTest {
	
	private ArtikelGroep artikelGroep1, artikelGroep2;
	private Artikel artikel1;
	
	@Before
	public void before() {
		artikelGroep1 = new ArtikelGroep("test");
		artikelGroep2 = new ArtikelGroep("test2");
		artikel1 = new FoodArtikel("test", BigDecimal.ONE, BigDecimal.TEN, artikelGroep1, 1);
	}
	
	// *** BIDIRECTIONELE ASSOCIATIE VANUIT HET STANDPUNT VAN ARTIKELGROEP ***
	
	@Test
	public void artikel1VerandertVanArtikelGroep1NaarArtikelGroep2() {
		// *** Lukt om het artikel toe te voegen aan een andere artikelGroep ***
		assertTrue(artikelGroep2.add(artikel1));
		// *** De eerste artikelGroep bevat nu geen artikels meer ***
		assertTrue(artikelGroep1.getArtikels().isEmpty());
		// *** De tweede artikelGroep bevat nu het artikel ***
		assertTrue(artikelGroep2.getArtikels().contains(artikel1));
		// *** Het artikel bevat voor de membervariabele artikelGroep een verwijzing naar de nieuwe ArtikelGroep ***
		assertEquals(artikelGroep2, artikel1.getArtikelGroep());
		
	}
}
