package JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import Player.AccessDB;
import Player.Player;

class FilterCountItemTest {
	AccessDB  accessTest = new AccessDB();

	//======================== FilterCountItem =========================
	// only data on adjust or edit after load file
		@Test
		void testFilterCountItem() {
			String input ="lw";
			ArrayList<Player> result = accessTest.filterCountItem(input,"userExample","passExample");
			assertEquals(37,result.size());
		}
		
		// no any add or change data, use original data from file only
		@Test
		void testFilterCountItem2() {
			String input =" ";
			ArrayList<Player> result = accessTest.filterCountItem(input,"userExample","passExample");
			assertEquals(451,result.size());
		}
		
		@Test
		void testFilterCountItem3() {
			String input ="+";
			ArrayList<Player> result = accessTest.filterCountItem(input,"userExample","passExample");
			assertEquals(196,result.size());
		}
		
		@Test
		void testFilterCountItem4() {
			String input ="'";
			ArrayList<Player> result = accessTest.filterCountItem(input,"userExample","passExample");
			assertEquals(4,result.size());
		}

}
