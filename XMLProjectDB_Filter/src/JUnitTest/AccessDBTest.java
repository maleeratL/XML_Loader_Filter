package JUnitTest;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Player.AccessDB;

class AccessDBTest {

	AccessDB  accessTest = new AccessDB();
	
	//======================== AccessSQL =========================
	@Test
	void testAccessSQL() {
		boolean result = accessTest.accessSQL("userExample","passExample");
		assertEquals(true,result);
	}
	
	@Test
	void testAccessSQL2() {
		boolean result = accessTest.accessSQL("userExample","passExample.");
		assertEquals(false,result);
	}
	
	@Test
	void testAccessSQL3() {
		boolean result = accessTest.accessSQL("UserExample","passExample");
		assertEquals(true,result);
	}
	
	@Test
	void testAccessSQL4() {
		boolean result = accessTest.accessSQL("userExample "," passExample");
		assertEquals(false,result);
	}
	
	@Test
	void testAccessSQL5() {
		boolean result = accessTest.accessSQL("use rExample "," passExample");
		assertEquals(false,result);
	}
	
	@Test
	void testAccessSQL6() {
		boolean result = accessTest.accessSQL("userExample123"," passExample");
		assertEquals(false,result);
	}

}
