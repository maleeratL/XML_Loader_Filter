package JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Player.AccessDB;
import Player.Player;

class SavePlayerInSQLTest {
	
	AccessDB  accessTest = new AccessDB();

	//======================== SavePlayerInSQL =========================
	//Player p = new Player("name","club","position","marketvalue","nationality");
	
	@Test
	void testSavePlayerInSQL() {
		Player p = new Player("name","club","position","23","nationality");
		boolean result = accessTest.savePlayerInSQL(p);
		assertEquals(true,result);
	}
	
	//marketvalue have to be number in String
	@Test
	void testSavePlayerInSQL2() {
		Player p = new Player("name","club","position","...","nationality");
		boolean result = accessTest.savePlayerInSQL(p);
		assertEquals(false,result);
	}
	
	//marketvalue have to be number in String
	@Test
	void testSavePlayerInSQL3() {
		Player p = new Player("...", "...", "...", "...", "...");
		boolean result = accessTest.savePlayerInSQL(p);
		assertEquals(false,result);
	}
	
	@Test
	void testSavePlayerInSQL4() {
		Player p = new Player("Fernandes", "West=====Ham", "CF", "10", "Algeria");
		boolean result = accessTest.savePlayerInSQL(p);
		assertEquals(true,result);
	}
	
	@Test
	void testSavePlayerInSQL5() {
		Player p = new Player("...", "...", "...", "1234", "...");
		boolean result = accessTest.savePlayerInSQL(p);
		assertEquals(true,result);
	}

}
