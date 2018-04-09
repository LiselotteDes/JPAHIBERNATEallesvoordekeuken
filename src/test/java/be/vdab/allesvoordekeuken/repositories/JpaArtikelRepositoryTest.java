package be.vdab.allesvoordekeuken.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Optional;

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

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import(JpaArtikelRepository.class)
public class JpaArtikelRepositoryTest {
	@Autowired
	private EntityManager manager;
	@Autowired
	private JpaArtikelRepository repository;
	private Artikel artikel;
	@Before
	public void before() {
		artikel = new Artikel("test", BigDecimal.ONE, BigDecimal.TEN);
	}
	private long idVanNieuwArtikel() {
		manager.createNativeQuery("insert into artikels(naam,aankoopprijs,verkoopprijs) values('test',100,120)")
		.executeUpdate();
		Number numberId = (Number) manager.createNativeQuery("select id from artikels where naam='test'").getSingleResult();
		return numberId.longValue();
	}
	@Test
	public void read() {
		Optional<Artikel> optionalArtikel = repository.read(idVanNieuwArtikel());
		assertTrue(optionalArtikel.isPresent());
		assertEquals("test", optionalArtikel.get().getNaam());
	}
	@Test
	public void create() {
		repository.create(artikel);
		long autoNumberId = artikel.getId();
		assertNotEquals(0, autoNumberId);
		assertEquals("test",
				(String) manager.createNativeQuery("select naam from artikels where id = :id")
				.setParameter("id", autoNumberId)
				.getSingleResult());
	}
}
