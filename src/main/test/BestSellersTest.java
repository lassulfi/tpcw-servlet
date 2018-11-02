import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

public class BestSellersTest {

	@Test
	public void test1() {

		TPCW_Database.populate(10000, 1000, 1000, 10000, 1000);

		List<Book> b = TPCW_Database.getBestSellers("NON-FICTION");

		assertEquals(50, b.size());

	}

	@Test
	public void test2() {
		TPCW_Database.populate(10000, 1000, 1000, 10000, 1000);

		List<Book> c = TPCW_Database.getBestSellers("ABC");

		assertEquals(0, c.size());

	}
}
