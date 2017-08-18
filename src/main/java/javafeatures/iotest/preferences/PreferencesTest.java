package javafeatures.iotest.preferences;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

/**
 * Preferences
 *
 * @author panws
 * @since 2017-08-18
 */
public class PreferencesTest {

	public static void main(String[] args) throws BackingStoreException {

		/**
		 * Preferences API利用合适的系统资源完成数据存储，这些资源会随操作系统不同而不同，不需要担心如何运作。例如windows中使用注册表。
		 */
		Preferences preferences = Preferences.userNodeForPackage(PreferencesTest.class);

		preferences.put("username", "pan");
		preferences.putInt("age", 24);
		preferences.putBoolean("married", false);

		int usageCount = preferences.getInt("usageCount", 0);
		usageCount++;

		preferences.putInt("usageCount", usageCount);

		for (String key : preferences.keys()) {
			System.out.println(key + ": " + preferences.get(key, null));
		}
	}
}
