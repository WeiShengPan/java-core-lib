package javafeatures.io.test.baseio;

import javafeatures.util.PrintUtil;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;

/**
 * File 类可以用于表示文件和目录的信息，但是它不表示文件的内容。
 *
 * @author panws
 * @since 2017-08-15
 */
public class FileTest {

	public static void main(String[] args) {

		filterFileName();

		PrintUtil.println();

		recursiveShowAllFile(new File("./src/main/java/javafeatures/io/test"), 0);

	}

	/**
	 * 递归列出目录下所有文件
	 */
	private static void recursiveShowAllFile(File path, int hierarchy) {

		if (path == null || !path.exists()) {
			return;
		}

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < hierarchy; i++) {
			sb.append("--");
		}
		sb.append(path.getName());
		PrintUtil.println(sb.toString());

		if (path.isFile()) {
			return;
		}

		for (File child : path.listFiles()) {
			recursiveShowAllFile(child, hierarchy + 1);
		}
	}

	/**
	 * 过滤文件名
	 */
	private static void filterFileName() {
		File path = new File("./src/main/java/guava/test");

		String regex = "Range.*?";

		String[] list = path.list((dir, name) -> Pattern.compile(regex).matcher(name).matches());
		//与使用匿名内部类等价
		String[] list2 = path.list(new DirFilter(regex));

		if (ArrayUtils.isNotEmpty(list2)) {
			Arrays.sort(list2, String.CASE_INSENSITIVE_ORDER);

			for (String dirItem : list) {
				PrintUtil.println(dirItem);
			}
		}
	}

	/**
	 * 文件名过滤
	 */
	private static class DirFilter implements FilenameFilter {

		private Pattern pattern;

		public DirFilter(String regex) {
			pattern = Pattern.compile(regex);
		}

		@Override
		public boolean accept(File dir, String name) {
			return pattern.matcher(name).matches();
		}
	}

}
