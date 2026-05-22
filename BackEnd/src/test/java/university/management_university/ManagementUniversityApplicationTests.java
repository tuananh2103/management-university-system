package university.management_university;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import university.management.ManagementUniversityApplication;

@SpringBootTest(
    classes = ManagementUniversityApplication.class,
    properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
    }
)
class ManagementUniversityApplicationTests {

	@Test
	void contextLoads() {
	}

}
