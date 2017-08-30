package java.features.io.test.baseio;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * @author panws
 * @since 2017-08-15
 */
public class FileTest {

	public static void main(String[] args) {
		File path = new File("./src/main/java/guavatest");

		String regex = "Range.*?";

		String[] list = path.list((dir, name) -> Pattern.compile(regex).matcher(name).matches());
		String[] list2 = path.list(new DirFilter(regex));	//与使用匿名内部类等价

		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);

		for (String dirItem : list) {
			System.out.println(dirItem);
		}
	}
}

/**
 * 可用匿名内部类替代
 */
class DirFilter implements FilenameFilter {

	private Pattern pattern;

	public DirFilter(String regex) {
		pattern = Pattern.compile(regex);
	}

	@Override public boolean accept(File dir, String name) {
		return pattern.matcher(name).matches();
	}
}
