package be.vdab.allesvoordekeuken.repositories;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import be.vdab.allesvoordekeuken.entities.Artikel;

@Repository
class JpaArtikelRepository implements ArtikelRepository {
	private final EntityManager manager;
	JpaArtikelRepository(EntityManager entityManager) {
		this.manager = entityManager;
	}
	@Override
	public Optional<Artikel> read(long id) {
		return Optional.ofNullable(manager.find(Artikel.class, id));
	}
	@Override
	public void create(Artikel artikel) {
//		throw new UnsupportedOperationException();
		manager.persist(artikel);
	}
	@Override
	public List<Artikel> findByNaamContains(String bevat) {
//		throw new UnsupportedOperationException();
		/*
		 * Volgende controle is er terug uitgehaald omdat dit in de service layer hoort !
		 * De redenering is dat de repository layer niet controleert of de parameter bevat van de method findByNaamContains 
		 * niet leeg is en verschilt van null.
		 * Deze verantwoordelijkheid is voor de services layer.
		 */
//		if (bevat.isEmpty()) {
//			throw new IllegalArgumentException();
//		}
		return manager.createNamedQuery("Artikel.findByNaamContains", Artikel.class)
				.setParameter("bevat", "%" + bevat + "%")
				.getResultList();
	}
	@Override
	public int prijsVerhoging(BigDecimal percentage) {
//		throw new UnsupportedOperationException();
		BigDecimal factor = BigDecimal.ONE.add(percentage.divide(BigDecimal.valueOf(100)));
		return manager.createNamedQuery("Artikel.prijsVerhoging")
				.setParameter("factor", factor)
				.executeUpdate();
		
	}
}
