package javafeatures.collection.test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static javafeatures.collection.test.Countries.DATA;

/**
 * @author panws
 * @since 2017-08-30
 */
public class CollectionMethods {

	public static void main(String[] args) {

		Collection<String> c1 = new ArrayList<>();

		c1.addAll(Countries.names(6));
		c1.add("ten");
		c1.add("eleven");
		System.out.println(c1);

		Object[] objArray = c1.toArray();
		String[] strArray = c1.toArray(new String[0]);
		System.out.println(objArray);
		System.out.println(strArray);

		System.out.println(Collections.max(c1));
		System.out.println(Collections.min(c1));

		Collection<String> c2 = new ArrayList<>();
		c2.addAll(Countries.names(6));
		c1.addAll(c2);
		System.out.println(c1);

		c1.remove(DATA[0][0]);
		System.out.println(c1);
		c1.remove(DATA[1][0]);
		System.out.println(c1);

		c1.removeAll(c2);
		System.out.println(c1);
		c1.addAll(c2);
		System.out.println(c1);

		System.out.println(c1.contains(DATA[3][0]));
		System.out.println(c1.containsAll(c2));

		Collection<String> c3 = ((List<String>) c1).subList(3, 5);
		System.out.println(c3);

		c2.retainAll(c3);
		System.out.println(c2);

		c2.removeAll(c3);
		System.out.println(c2);
		System.out.println(c2.isEmpty());

		Collection<String> c4 = new ArrayList<>();
		c4.addAll(Countries.names(6));
		System.out.println(c4);
		c4.clear();
		System.out.println(c4);

	}
}
