package com.pws.javafeatures.io.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;

public class FileUtils {

    private static final int B_SIZE = 1024;

    public static void write(String path, String content) throws Exception {
        //write from FileOutputStream
        try (FileChannel fc = new FileOutputStream(path).getChannel()) {
            fc.write(ByteBuffer.wrap(content.getBytes()));
        }
    }

    public static String read(String path) throws Exception {
        //read from FileInputStream
        StringBuilder stringBuilder = new StringBuilder();
        try (FileChannel fc = new FileInputStream(path).getChannel()) {

            //只读访问必须分配ByteBuffer的大小，更高速度可用ByteBuffer.allocateDirect()产生直接缓冲器，但开支更大
            ByteBuffer byteBuffer = ByteBuffer.allocate(B_SIZE);

            //解决编码问题
            CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
            CharBuffer charBuffer = CharBuffer.allocate(B_SIZE);

            while (fc.read(byteBuffer) != -1) {
                //准备缓冲器，以便信息可被write读取
                byteBuffer.flip();

                // Decode in UTF-8
                decoder.decode(byteBuffer, charBuffer, false);
                charBuffer.flip();

                // append to StringBuilder
                stringBuilder.append(charBuffer.toString());

                //对所有内部指针重新安排，以便缓冲器在另一个read操作期间能够做好接受数据的准备
                byteBuffer.clear();
                charBuffer.clear();
            }
        }

        return stringBuilder.toString();
    }
}
