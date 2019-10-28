package JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Player.AccessDB;
import Player.Player;

class CheckDuplicateTest {
	
	AccessDB  accessTest = new AccessDB();

	//======================== CheckDuplicate from Database =========================
		@Test
		void testCheckDuplicate() {
			Player p = new Player(null, null, null, null, null);
			boolean result = accessTest.checkDuplicate(p);
			assertEquals(true,result);
		}
		
		@Test
		void testCheckDuplicate2() {
			Player p = new Player("name","club","position","marketvalue","nationality");
			boolean result = accessTest.checkDuplicate(p);
			assertEquals(false,result);
		}
		
		@Test
		void testCheckDuplicate3() {
			Player p = new Player("name!","club","position","11","nationality");
			boolean result = accessTest.checkDuplicate(p);
			assertEquals(false,result);
		}
		
		@Test
		void testCheckDuplicate4() {
			Player p = new Player("name!","club","position","11.1","nationality");
			boolean result = accessTest.checkDuplicate(p);
			assertEquals(false,result);
		}
		
		@Test
		void testCheckDuplicate5() {
			Player p = new Player("name","club","","marketvalue","nationality");
			boolean result = accessTest.checkDuplicate(p);
			assertEquals(false,result);
		}
		
		// have a space
		@Test
		void testCheckDuplicate6() {
			Player p = new Player("name ","club","positi on","marketvalue","nationality");
			boolean result = accessTest.checkDuplicate(p);
			assertEquals(false,result);
		}
		
		// Ignore case sensitive
		@Test
		void testCheckDuplicate7() {
			Player p = new Player("Name","Club","Position","Marketvalue","Nationality");
			boolean result = accessTest.checkDuplicate(p);
			assertEquals(false,result);
		}
		
		
		//======================== CheckDuplicate from table filter =========================
		// check with sample ArrayList
		ArrayList<Player> data = new ArrayList<Player>();
		@Test
		void testCheckDuplicateArray() {
			Player p = new Player("...", "...", "...","1.23", "...");
			data.add(p);
			Player p1 = new Player("...", "...", "...","1.23", "...");
			boolean result = accessTest.checkDuplicateArray(data,p1); 
			assertEquals(true,result);
		}
		
		// empty String ""
		@Test
		void testCheckDuplicateArray2() {
			Player p = new Player("...", "...", "...","1.23", "...");
			data.add(p);
			Player p1 = new Player("...", "...", "","1.23", "...");
			boolean result = accessTest.checkDuplicateArray(data,p1); 
			assertEquals(false,result);
		}
		
		// add space in "... "
		@Test
		void testCheckDuplicateArray3() {
			Player p = new Player("...", "...", "...","1.23", "...");
			data.add(p);
			Player p1 = new Player("...", "... ", "...","1.23", "...");
			boolean result = accessTest.checkDuplicateArray(data,p1); 
			assertEquals(false,result);
		}
		
		
		@Test
		void testCheckDuplicateArray4() {
			Player p = new Player("...", "... ", "...","1.23", "...");
			data.add(p);
			Player p1 = new Player("...", "...", "test","1.23", "...");
			boolean result = accessTest.checkDuplicateArray(data,p1); 
			assertEquals(false,result);
		}
		
		// Ignore case sensitive
		@Test
		void testCheckDuplicateArray5() {
			Player p = new Player("name ","club","position","marketvalue","nationality");
			data.add(p);
			Player p1 = new Player("Name ","Club","Position","Marketvalue","Nationality");
			boolean result = accessTest.checkDuplicateArray(data,p1); 
			assertEquals(false,result);
		}

}
