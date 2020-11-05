package guava;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import javafeatures.util.PrintUtil;

/**
 * Table支持row和column,提供多种视图，支持替代Map<key, Map<key, value>>
 *
 * @author panws
 * @since 2017-07-03
 */
public class TableTest {

	public static void main(String[] args) {

		Table<String, String, String> table1 = HashBasedTable.create();

		table1.put("Pan", "Weisheng", "PanWeisheng");
		table1.put("Pan", "Xiaoxiang", "PanXiaoxiang");
		table1.put("Huang", "Xiaomin", "HuangXiaomin");

		PrintUtil.println(table1.row("Pan"));
		PrintUtil.println(table1.row("Huang"));
		PrintUtil.println(table1.column("Weisheng"));
		PrintUtil.println(table1.rowMap());
		PrintUtil.println(table1.columnMap());


	}
}
