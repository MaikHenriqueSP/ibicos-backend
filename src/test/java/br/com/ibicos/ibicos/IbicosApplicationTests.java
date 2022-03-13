package br.com.ibicos.ibicos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = Object.class)
class IbicosApplicationTests {

	@Test
	public void testt() {
		assertEquals("1", "1");
	}

}
