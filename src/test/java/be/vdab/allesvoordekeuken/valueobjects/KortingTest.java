package be.vdab.allesvoordekeuken.valueobjects;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

public class KortingTest {
	private Korting korting1;
	private Korting nogEensKorting1;
	private Korting korting2;
	
	@Before
	public void before() {
		korting1 = new Korting(1, BigDecimal.ONE);
		nogEensKorting1 = new Korting(1, BigDecimal.ONE);
		korting2 = new Korting(2, BigDecimal.TEN);
	}
	
	@Test
	public void tweeKortingenZijnGelijkAlsHunVanafAantallenGelijkZijn() {
		assertEquals(korting1, nogEensKorting1);
	}
	@Test
	public void tweeKortingenZijnVerschillendAlsHunVanafAantalVerschillendIs() {
		assertNotEquals(korting1, korting2);
	}
	@Test
	public void eenKortinIsVerschillendVanNull() {
		assertNotEquals(korting1, null);
	}
	@Test
	public void eenKortingIsVerschillendVanEenObjectVanEenAnderType() {
		assertNotEquals(korting1, "");
	}
	@Test
	public void gelijkeKortingenHebbenDezelfdeHashCode() {
		assertEquals(korting1.hashCode(), nogEensKorting1.hashCode());
	}
}
