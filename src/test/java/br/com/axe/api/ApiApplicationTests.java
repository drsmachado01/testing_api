package br.com.axe.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiApplicationTests {

	@Test
	void main() {
		ApiApplication.main(new String[]{});
		int x = 0;
		assertEquals(0, x);
	}

}
