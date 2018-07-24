package rctest;

import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

public class Utils {

	/**
	 * Question1, sort by firstName + lastName + ext,
	 * if firstName is the same then sort by lastName and
	 * ext, please note lastName and ext can be empty
	 * string or null.
	 **/
	public static List<Extension> sortByName(List<Extension> extensions) {

		Collections.sort(extensions, (e1, e2) -> {

			//first, sort by firstName
			if (e1.getFirstName().compareTo(e2.getFirstName()) > 0) {
				return 1;
			}
			if (e1.getFirstName().compareTo(e2.getFirstName()) < 0) {
				return -1;
			}

			//both lastName are empty or null, then compare the ext
			if (StringUtils.isEmpty(e1.getLastName()) && StringUtils.isEmpty(e2.getLastName())) {
				//both ext are empty or null, then result is equal
				if (StringUtils.isEmpty(e1.getExt()) && StringUtils.isEmpty(e2.getExt())) {
					return 0;
				}
				if (StringUtils.isEmpty(e1.getExt())) {
					return -1;
				}
				if (StringUtils.isEmpty(e2.getExt())) {
					return 1;
				}

				return e1.getExt().compareTo(e2.getExt());
			}

			//one of the last name is not empty or null, then compare the lastName
			if (StringUtils.isEmpty(e1.getLastName())) {
				return -1;
			}
			if (StringUtils.isEmpty(e2.getLastName())) {
				return 1;
			}

			return e1.getLastName().compareTo(e2.getLastName());
		});

		return extensions;
	}

	/**
	 * Question2, sort extType, extType is a string and can
	 * be "User", "Dept", "AO", "TMO", "Other",
	 * sort by User > Dept > AO > TMO > Other;
	 **/
	public static List<Extension> sortByExtType(List<Extension> extensions) {
		return null;
	}

	/**
	 * Question3, sum all sales items by quarter
	 **/
	public static List<QuarterSalesItem> sumByQuarter(List<SaleItem> saleItems) {
		return null;
	}

	/**
	 * Question4, max all sales items by quarter
	 **/
	public static List<QuarterSalesItem> maxByQuarter(List<SaleItem> saleItems) {
		return null;
	}

	/**
	 * We have all Keys: 0-9;
	 * usedKeys is an array to store all used keys like :[2,3,4];
	 * We want to get all unused keys, in this example it would be: [0,1,5,6,7,8,9,]
	 */
	public static int[] getUnUsedKeys(int[] allKeys, int[] usedKeys) {
		return null;
	}

}
