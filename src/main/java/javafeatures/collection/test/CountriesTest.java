package javafeatures.collection.test;

import java.util.*;

/**
 * @author panws
 * @since 2017-08-30
 */
public class CountriesTest {

	public static void main(String[] args) {

		System.out.println(Countries.capitals());
		System.out.println(Countries.capitals(3));
		System.out.println("**************************************");

		System.out.println(Countries.names());
		System.out.println(Countries.names(3));
		System.out.println("**************************************");

		System.out.println(new HashMap<>(Countries.capitals(3)));
		System.out.println(new LinkedHashMap<>(Countries.capitals(3)));
		System.out.println(new TreeMap<>(Countries.capitals(3)));
		System.out.println(new Hashtable<>(Countries.capitals(3)));
		System.out.println("**************************************");

		System.out.println(new HashSet<>(Countries.names(3)));
		System.out.println(new LinkedHashSet<>(Countries.names(3)));
		System.out.println(new TreeSet<>(Countries.names(3)));
		System.out.println(new ArrayList<>(Countries.names(3)));
		System.out.println(new LinkedList<>(Countries.names(3)));
		System.out.println("**************************************");

		System.out.println(Countries.capitals().get("CHINA"));
		System.out.println("**************************************");

		Map<String, String> countryMap = Countries.capitals();
		for (Map.Entry country : countryMap.entrySet()) {
			System.out.println(country);
		}
		System.out.println("**************************************");

		Iterator<Map.Entry<String, String>> iterator = countryMap.entrySet().iterator();
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
	}
}
