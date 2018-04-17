package be.vdab.allesvoordekeuken.entities;

import java.math.BigDecimal;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("F")
public class FoodArtikel extends Artikel {
	private static final long serialVersionUID = 1L;
	private int houdbaarheid;
	public FoodArtikel(String naam, BigDecimal aankoopprijs, BigDecimal verkoopprijs, ArtikelGroep artikelGroep, int houdbaarheid) {
		super(naam, aankoopprijs, verkoopprijs, artikelGroep);
		this.houdbaarheid = houdbaarheid;
	}
	protected FoodArtikel() {
	}
	public int getHoudbaarheid() {
		return houdbaarheid;
	}
}
