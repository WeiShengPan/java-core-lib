package com.pws.javafeatures.java8;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.pws.javafeatures.util.PrintUtil;

/**
 * 函数式编程
 *
 * @author panws
 * @since 2018-01-02
 */
public class StreamTest {

	private static final Collection<Task> TASKS = Arrays
			.asList(new Task(Status.OPEN, 5), new Task(Status.OPEN, 13), new Task(Status.CLOSE, 8));

	public static void main(String[] args) {

		//根据条件过滤
		int totalPointsOfOpenTask = TASKS
				.stream()
				.filter(task -> task.status == Status.OPEN)
				.mapToInt(Task::getPoints)
				.sum();
		PrintUtil.println(totalPointsOfOpenTask);

		//支持并行处理（parallel processing）
		double totalPointsOfAllTask = TASKS
				.stream()
				.parallel()
				.map(Task::getPoints)
				.reduce(0, Integer::sum);
		PrintUtil.println(totalPointsOfAllTask);

		//分组
		Map<Status, List<Task>> groupMap = TASKS
				.stream()
				.collect(Collectors.groupingBy(Task::getStatus));
		PrintUtil.println(groupMap);

		//计算比例
		Collection<String> proportion = TASKS
				.stream()
				.mapToInt(Task::getPoints)
				.asLongStream()
				.mapToDouble(points -> points / totalPointsOfAllTask)
				.boxed()
				.mapToLong(weight -> (long) (weight * 100))
				.mapToObj(rate -> rate + "%")
				.collect(Collectors.toList());
		PrintUtil.println(proportion);

	}

	private enum Status {
		OPEN, CLOSE
	}

	private static final class Task {
		private Status status;
		private Integer points;

		private Task(Status status, Integer points) {
			this.status = status;
			this.points = points;
		}

		public Status getStatus() {
			return status;
		}

		public Integer getPoints() {
			return points;
		}

		@Override
		public String toString() {
			return "Task{" + "status=" + status + ", points=" + points + '}';
		}
	}
}
