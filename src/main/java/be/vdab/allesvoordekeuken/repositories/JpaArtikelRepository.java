package be.vdab.allesvoordekeuken.repositories;

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
		if (bevat.isEmpty()) {
			throw new IllegalArgumentException();
		}
		return manager.createNamedQuery("Artikel.findByNaamContains", Artikel.class)
				.setParameter("bevat", "%" + bevat + "%")
				.getResultList();
	}
}
