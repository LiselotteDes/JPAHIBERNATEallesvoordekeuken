package be.vdab.allesvoordekeuken.repositories;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class DataSourceTest {
	/*
	@Autowired
	private DataSource dataSource;
	@Test
	public void getConnection() throws SQLException {
		try (Connection connection = dataSource.getConnection()){
		}
	}*/
	
	@Test
	public void initialisatieOK() {
		
	}

}
