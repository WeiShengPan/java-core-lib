package rctest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Define strategy for calculating by quarter
 *
 * @author panws
 * @since 2018-07-25
 */
public interface QuarterCalculateStrategy {

	Map<Integer, Double> calculate(List<SaleItem> saleItems);

	QuarterCalculateStrategy SUM = saleItems -> {
		// map key is quarter, value is total sale numbers
		Map<Integer, Double> quarterMap = new HashMap<>(7);

		// group saleItems by quarter
		for (SaleItem saleItem : saleItems) {
			int quarter = getQuarterByMonth(saleItem.getMonth());
			if (quarterMap.containsKey(quarter)) {
				quarterMap.put(quarter, quarterMap.get(quarter) + saleItem.getSaleNumbers());
			} else {
				quarterMap.put(quarter, saleItem.getSaleNumbers());
			}
		}
		return quarterMap;
	};

	QuarterCalculateStrategy MAX = saleItems -> {
		// map key is quarter, value is total sale numbers
		Map<Integer, Double> quarterMap = new HashMap<>(7);

		// group saleItems by quarter
		for (SaleItem saleItem : saleItems) {
			int quarter = getQuarterByMonth(saleItem.getMonth());
			if (quarterMap.containsKey(quarter)) {
				double base = quarterMap.get(quarter);
				double greater = base > saleItem.getSaleNumbers() ? base : saleItem.getSaleNumbers();
				quarterMap.put(quarter, greater);
			} else {
				quarterMap.put(quarter, saleItem.getSaleNumbers());
			}
		}
		return quarterMap;
	};

	/**
	 * Get quarter by month, set unrecognized quarter to 0
	 *
	 * @param month
	 * @return
	 */
	static int getQuarterByMonth(int month) {
		switch (month) {
			case 1:
			case 2:
			case 3:
				return 1;
			case 4:
			case 5:
			case 6:
				return 2;
			case 7:
			case 8:
			case 9:
				return 3;
			case 10:
			case 11:
			case 12:
				return 4;
			default:
				return 0;
		}
	}

}
