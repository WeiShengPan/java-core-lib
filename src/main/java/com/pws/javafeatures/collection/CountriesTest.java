package com.pws.javafeatures.collection;

import com.pws.javafeatures.util.PrintUtil;

import java.util.*;

/**
 * @author panws
 * @since 2017-08-30
 */
public class CountriesTest {

	public static void main(String[] args) {

		PrintUtil.println(Countries.capitals());
		PrintUtil.println(Countries.capitals(3));
		PrintUtil.println("**************************************");

		PrintUtil.println(Countries.names());
		PrintUtil.println(Countries.names(3));
		PrintUtil.println("**************************************");

		PrintUtil.println(new HashMap<>(Countries.capitals(3)));
		PrintUtil.println(new LinkedHashMap<>(Countries.capitals(3)));
		PrintUtil.println(new TreeMap<>(Countries.capitals(3)));
		PrintUtil.println(new Hashtable<>(Countries.capitals(3)));
		PrintUtil.println("**************************************");

		PrintUtil.println(new HashSet<>(Countries.names(3)));
		PrintUtil.println(new LinkedHashSet<>(Countries.names(3)));
		PrintUtil.println(new TreeSet<>(Countries.names(3)));
		PrintUtil.println(new ArrayList<>(Countries.names(3)));
		PrintUtil.println(new LinkedList<>(Countries.names(3)));
		PrintUtil.println("**************************************");

		PrintUtil.println(Countries.capitals().get("CHINA"));
		PrintUtil.println("**************************************");

		Map<String, String> countryMap = Countries.capitals();
		for (Map.Entry country : countryMap.entrySet()) {
			PrintUtil.println(country);
		}
		PrintUtil.println("**************************************");

		Iterator<Map.Entry<String, String>> iterator = countryMap.entrySet().iterator();
		while (iterator.hasNext()) {
			PrintUtil.println(iterator.next());
		}
	}
}
