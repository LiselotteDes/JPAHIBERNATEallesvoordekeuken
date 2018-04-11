package be.vdab.allesvoordekeuken.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import be.vdab.allesvoordekeuken.entities.Artikel;

public interface ArtikelRepository {
	// "Zoeken op nummer"
	Optional<Artikel> read(long id);
	// "Toevoegen"
	void create(Artikel artikel);
	// "Zoeken op naam"
	List<Artikel> findByNaamContains(String bevat);
	// "Prijsverhoging"
	int prijsVerhoging(BigDecimal percentage);
}
