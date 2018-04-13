package be.vdab.allesvoordekeuken.valueobjects;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Embeddable;

@Embeddable
public class Korting implements Serializable {
	private static final long serialVersionUID = 1L;
	private int vanafAantal;
	private BigDecimal percentage;
	
	public Korting(int vanafAantal, BigDecimal percentage) {
		this.vanafAantal = vanafAantal;
		this.percentage = percentage;
	}
	protected Korting() {
	}
	
	public int getVanafAantal() {
		return vanafAantal;
	}
	public BigDecimal getPercentage() {
		return percentage;
	}
	
	@Override
	public boolean equals(Object object) {
		if (!(object instanceof Korting)) {
			return false;
		}
		Korting korting = (Korting) object;
//		return ((vanafAantal == korting.vanafAantal) && (percentage.compareTo(korting.percentage) == 0));	// "fout"
		/*
		 * Twee kortingen zijn gelijk als hun vanafAantal gelijk is.
		 * In de database zijn echter records te zien met hetzelfde vanafAantal en een verschillend percentage, 
		 * daarom dacht ik dat de gelijkheid moest afhangen van beide private variabelen.
		 * MAAR, de records met hetzelfde vanafAantal en ander percentage, horen ook bij verschillende artikels !!
		 * EN, de kortingen records zijn nooit allemaal geladen, maar enkel PER ENTITY !!
		 * Daarom kan je één van de variabelen kiezen, en is vanafAantal een logische keuze.
		 */
		return vanafAantal == korting.vanafAantal;
	}
	@Override
	public int hashCode() {
//		return vanafAantal * percentage.hashCode();		// (fout)
		return vanafAantal;
	}
}
