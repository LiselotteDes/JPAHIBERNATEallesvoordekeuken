package be.vdab.allesvoordekeuken.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Optional;

import javax.persistence.EntityManager;

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
	private EntityManager entityManager;
	@Autowired
	private JpaArtikelRepository repository;
	private long idVanNieuwArtikel() {
		entityManager.createNativeQuery("insert into artikels(naam,aankoopprijs,verkoopprijs) values('test',100,120)")
		.executeUpdate();
		Number numberId = (Number) entityManager.createNativeQuery("select id from artikels where naam='test'").getSingleResult();
		return numberId.longValue();
	}
	@Test
	public void read() {
		Optional<Artikel> optionalArtikel = repository.read(idVanNieuwArtikel());
		assertTrue(optionalArtikel.isPresent());
		assertEquals("test", optionalArtikel.get().getNaam());
	}

}
