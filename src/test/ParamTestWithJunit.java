import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.runners.Parameterized.Parameters;

/**
 * @author panws
 * @since 2017-11-06
 */
@RunWith(value = Parameterized.class)
public class ParamTestWithJunit {

	private int number;

	public ParamTestWithJunit(int number) {
		this.number = number;
	}

	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { { 1 }, { 2 }, { 3 }, { 6 } };
		return Arrays.asList(data);
	}

	@Test
	public void pushTest() {
		System.out.println("Parameterized Number is : " + number);
	}
}
