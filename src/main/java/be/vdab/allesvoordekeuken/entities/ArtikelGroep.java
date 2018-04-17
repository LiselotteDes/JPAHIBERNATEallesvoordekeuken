package be.vdab.allesvoordekeuken.entities;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "artikelgroepen") 
public class ArtikelGroep implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String naam;
	@OneToMany(mappedBy = "artikelGroep")
	@OrderBy("naam")
	private Set<Artikel> artikels;
	
	public ArtikelGroep(String naam) {
		this.naam = naam;
		this.artikels = new LinkedHashSet<>();
	}
	protected ArtikelGroep() {
	}
	
	public long getId() {
		return id;
	}
	public String getNaam() {
		return naam;
	}
	public Set<Artikel> getArtikels() {
		return Collections.unmodifiableSet(artikels);
	}
	public boolean add(Artikel artikel) {
		if (artikel == null) {
			throw new NullPointerException();
		}
		boolean toegevoegd = artikels.add(artikel);
		ArtikelGroep oudeArtikelGroep = artikel.getArtikelGroep();
		if (oudeArtikelGroep != null && oudeArtikelGroep != this) {
			oudeArtikelGroep.artikels.remove(artikel);
		}
		if (oudeArtikelGroep != this) {
			artikel.setArtikelGroep(this);
		}
		return toegevoegd;
	}
}
