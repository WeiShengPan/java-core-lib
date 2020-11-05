package jvm;

import javafeatures.util.PrintUtil;

/**
 * 宣告对象死亡需要两次标记，但finalize方法只会执行一次，第一次标记后可以在finalize方法中拯救自己，但没有第二次。
 * 事实上也不应该在finalize方法中做这种操作，它的运行代价高昂，不确定性大，这里只为演示。
 *
 * @author panws
 * @since 2017-12-22
 */
public class FinalizeEscapeGc {

	public static FinalizeEscapeGc SAVE_HOOK = null;

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
		PrintUtil.println("finalize method executed");
		FinalizeEscapeGc.SAVE_HOOK = this;
	}

	public static void main(String[] args) throws InterruptedException {

		SAVE_HOOK = new FinalizeEscapeGc();

		//第一次自救成功
		SAVE_HOOK = null;
		System.gc();
		//finalize方法优先级低，等待0.5s
		Thread.sleep(500);
		if (SAVE_HOOK != null) {
			PrintUtil.println("alive");
		} else {
			PrintUtil.println("dead");
		}

		//第二次
		SAVE_HOOK = null;
		System.gc();
		//finalize方法优先级低，等待0.5s
		Thread.sleep(500);
		if (SAVE_HOOK != null) {
			PrintUtil.println("alive");
		} else {
			PrintUtil.println("dead");
		}

	}
}
