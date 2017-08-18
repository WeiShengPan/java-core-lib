package javafeatures.iotest.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

/**
 * @author panws
 * @since 2017-08-17
 */
public class LockingMappedFile {

	private static int LENGTH = 0x8FFFFFF;

	private static FileChannel fileChannel;

	public static void main(String[] args) throws IOException {

		fileChannel = new RandomAccessFile("src/main/resources/io/output8.txt", "rw").getChannel();

		MappedByteBuffer out = fileChannel.map(FileChannel.MapMode.READ_WRITE, 0, LENGTH);

		for (int i = 0; i < LENGTH; i++) {
			out.put((byte) 'x');
		}

		LockAndModify lockAndModifyA = new LockAndModify(out, 0, 0 + LENGTH / 3, 1);
		LockAndModify lockAndModifyB = new LockAndModify(out, LENGTH / 2, LENGTH / 2 + LENGTH / 4, 2);

		lockAndModifyA.start();
		lockAndModifyB.start();

	}

	static class LockAndModify extends Thread {

		private ByteBuffer byteBuffer;

		private int start;

		private int end;

		private int add;

		LockAndModify(ByteBuffer byteBuffer, int start, int end, int add) {

			this.start = start;
			this.end = end;

			byteBuffer.limit(end);
			byteBuffer.position(start);

			this.byteBuffer = byteBuffer.slice();    //创建用于修改的slice片段

			this.add = add;

		}

		@Override public void run() {
			try {
				FileLock fileLock = fileChannel.tryLock(start, end, false);    //部分加锁

				System.out.println("Locked: " + start + " to " + end);

				while (byteBuffer.position() < byteBuffer.limit() - 1) {
					byteBuffer.put((byte) (byteBuffer.get() + add));
				}

				fileLock.release();    //释放锁

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}
}


