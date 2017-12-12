import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.Vector;

/**
 * @author panws
 * @since 2017-11-06
 */
public class ParamTestWithTestng {

	@Test(dataProvider = "Data-Provider-Function")
	public void parameterIntTest(Class clzz, String[] number, Date date) {

		System.out.println("Parameterized class is : " + clzz.getSimpleName());
		System.out.println("Parameterized Number is : " + number[0]);
		System.out.println("Parameterized Number is : " + number[1]);
		System.out.println("Parameterized Date is : " + date);

	}

	//This function will provide the patameter data
	@DataProvider(name = "Data-Provider-Function")
	public Object[][] parameterIntTestProvider() {
		return new Object[][] {
				{ Vector.class, new String[] { "java.util.AbstractList", "java.util.AbstractCollection" }, new Date() },
				{ String.class, new String[] { "1", "2" }, new Date() },
				{ Integer.class, new String[] { "3", "4" }, new Date() } };
	}
}
