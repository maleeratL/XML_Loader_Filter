package JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Player.DataLoader;

class DataLoaderTest {
	
	DataLoader loadtest = new DataLoader();

	@Test
	void testLoad1() {
		int result = loadtest.load("C:\\Users\\Desktop\\playerswrong.xml");
		assertEquals(461,result);
	}
	
	@Test
	void testLoad2() {
		loadtest.load("C:\\Users\\Desktop\\players.html");
		int result = loadtest.getPlayerList().size();
		assertEquals(461,result);
	}
	
	@Test
	void testLoad3() {
		int result = loadtest.load("path");
		assertEquals(0,result);
	}

}
