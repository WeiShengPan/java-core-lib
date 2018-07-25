package rctest;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class Utils {

	/**
	 * Question1, sort by firstName + lastName + ext,
	 * if firstName is the same then sort by lastName and
	 * ext, please note lastName and ext can be empty
	 * string or null.
	 *
	 * @param extensions
	 * @return
	 */
	public static List<Extension> sortByName(List<Extension> extensions) {
		// sort by nature order, null value at the end of the list.
		extensions.sort((o1, o2) -> {
			// first, compare firstName.
			if (o1.getFirstName().compareTo(o2.getFirstName()) > 0) {
				return 1;
			}
			if (o1.getFirstName().compareTo(o2.getFirstName()) < 0) {
				return -1;
			}

			// if firstName is equal, compare lastName or ext.
			if (needCompareExt(o1.getLastName(), o2.getLastName())) {
				return sortLastNameOrExt(o1.getExt(), o2.getExt());
			} else {
				return sortLastNameOrExt(o1.getLastName(), o2.getLastName());
			}
		});

		return extensions;
	}

	/**
	 * Question2, sort extType, extType is a string and can
	 * be "User", "Dept", "AO", "TMO", "Other",
	 * sort by User > Dept > AO > TMO > Other;
	 *
	 * @param extensions
	 * @return
	 */
	public static List<Extension> sortByExtType(List<Extension> extensions) {
		extensions.sort(Comparator.comparingInt(o -> ExtTypeEnum.priorityOf(o.getExtType())));
		return extensions;
	}

	/**
	 * Question3, sum all sales items by quarter
	 *
	 * @param saleItems
	 * @return
	 */
	public static List<QuarterSalesItem> sumByQuarter(List<SaleItem> saleItems) {
		return calByQuarter(saleItems, QuarterCalculateStrategy.SUM);
	}

	/**
	 * Question4, max all sales items by quarter
	 *
	 * @param saleItems
	 * @return
	 */
	public static List<QuarterSalesItem> maxByQuarter(List<SaleItem> saleItems) {
		return calByQuarter(saleItems, QuarterCalculateStrategy.MAX);
	}

	/**
	 * We have all Keys: 0-9;
	 * usedKeys is an array to store all used keys like :[2,3,4];
	 * We want to get all unused keys, in this example it would be: [0,1,5,6,7,8,9,]
	 *
	 * @param allKeys
	 * @param usedKeys
	 * @return
	 */
	public static int[] getUnUsedKeys(int[] allKeys, int[] usedKeys) {
		// copy the array to HashSet
		Set<Integer> set = new HashSet<>();
		for (int key : allKeys) {
			set.add(key);
		}

		// loop to delete used keys
		for (int usedKey : usedKeys) {
			set.remove(usedKey);
		}

		int index = 0;
		int[] unUsedKeys = new int[set.size()];
		for (Integer key : set) {
			unUsedKeys[index++] = key;
		}

		return unUsedKeys;
	}

	/**
	 * In 2 cases we need to compare ext:
	 * 1. both lastName are empty string
	 * 2. both lastName are not empty string, and they are equal
	 *
	 * @param lastName1
	 * @param lastName2
	 * @return
	 */
	private static boolean needCompareExt(String lastName1, String lastName2) {
		if (StringUtils.isEmpty(lastName1) && StringUtils.isEmpty(lastName2)) {
			return true;
		}

		if (StringUtils.isNotEmpty(lastName1) && StringUtils.isNotEmpty(lastName2)
				&& lastName1.compareTo(lastName2) == 0) {
			return true;
		}

		return false;
	}

	/**
	 * @param str1
	 * @param str2
	 * @return
	 */
	private static int sortLastNameOrExt(String str1, String str2) {
		// if both string are empty, then they are equal
		if (StringUtils.isEmpty(str1) && StringUtils.isEmpty(str2)) {
			return 0;
		}

		// in order to put empty or null in the last place of nature sorted list, define empty string is greater
		if (StringUtils.isEmpty(str1)) {
			return 1;
		}
		if (StringUtils.isEmpty(str2)) {
			return -1;
		}

		return str1.compareTo(str2);
	}

	/**
	 * Calculate by quarter according to the given strategy
	 *
	 * @param saleItems
	 * @param strategy
	 * @return
	 */
	private static List<QuarterSalesItem> calByQuarter(List<SaleItem> saleItems, QuarterCalculateStrategy strategy) {
		// calculate by strategy
		Map<Integer, Double> quarterMap = strategy.calculate(saleItems);

		List<QuarterSalesItem> quarterList = new ArrayList<>(5);
		for (Map.Entry<Integer, Double> entry : quarterMap.entrySet()) {
			quarterList.add(new QuarterSalesItem(entry.getKey(), entry.getValue()));
		}

		return quarterList;
	}
}
