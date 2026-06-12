package university.management_university;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import university.management.ManagementUniversityApplication;

@SpringBootTest(
    classes = ManagementUniversityApplication.class,
    properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
        "spring.datasource.driverClassName=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
        "spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.H2Dialect",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "app.jwt.secret=test_secret_at_least_32_chars_long_xx"
    }
)
class ManagementUniversityApplicationTests {

	@Test
	void contextLoads() {
	}

}
