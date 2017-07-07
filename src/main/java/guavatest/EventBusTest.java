package guavatest;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * EventBus: Guava的事件处理机制，是设计模式中的观察者模式（生产/消费者编程模型）的优雅实现
 *
 * @author panws
 * @since 2017-06-23
 */
public class EventBusTest {

	public static void main(String[] args) {

		EventBus eventBus = new EventBus("simpleTest");
		CustomEventListener eventListener1 = new CustomEventListener("listener1");
		CustomEventListener eventListener2 = new CustomEventListener("listener2");

		eventBus.register(eventListener1);
		eventBus.register(eventListener2);

		eventBus.post(new MessageEvent(1));
		eventBus.post(new MessageEvent(3));
		eventBus.post(new Integer(1));
		eventBus.post(new Integer(2));
		eventBus.post("hahaha");
		eventBus.post("hehehehe");

		eventBus.unregister(eventListener1);

		eventBus.post(new MessageEvent(5));
		eventBus.post(new MessageEvent(7));
		eventBus.post("gagaga");

	}

	/**
	 * 使用Guava之后, 如果要订阅消息, 就不用再继承指定的接口, 只需要在指定的方法上加上 @Subscribe 注解即可
	 */
	static class CustomEventListener {

		private Integer intMessage;

		private String stringMessage;

		private MessageEvent objMessage;

		private String name;

		public CustomEventListener(String name) {
			this.name = name;
		}

		@Subscribe public void listener(MessageEvent event) {
			objMessage = event;
			System.out.printf("[%s] received obj: %s \n", name, objMessage);
		}

		@Subscribe public void listenInt(Integer intMsg) {
			intMessage = intMsg;
			System.out.printf("[%s] received int: %s \n", name, intMessage);
		}

		@Subscribe public void listenString(String stringMsg) {
			stringMessage = stringMsg;
			System.out.printf("[%s] received string: %s \n", name, stringMessage);
		}
	}

	static class MessageEvent {
		private int message;

		public MessageEvent(int message) {
			this.message = message;
		}

		@Override public String toString() {
			return new ToStringBuilder(this).append("message", message).toString();
		}
	}

}

