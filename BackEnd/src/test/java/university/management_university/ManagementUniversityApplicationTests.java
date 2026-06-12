package university.management_university;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import university.management.ManagementUniversityApplication;

@SpringBootTest(
    classes = ManagementUniversityApplication.class,
    properties = {
        "spring.jpa.hibernate.ddl-auto=update"
    }
)
class ManagementUniversityApplicationTests {

	@Test
	void contextLoads() {
	}

}
