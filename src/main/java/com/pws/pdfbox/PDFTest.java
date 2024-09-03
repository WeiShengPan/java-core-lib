package com.pws.pdfbox;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.io.RandomAccessReadBufferedFile;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.nio.file.*;

@Slf4j
public class PDFTest {
    public static void main(String[] args) {

        try (PDDocument document = Loader.loadPDF(new RandomAccessReadBufferedFile("src/main/resources/io/pdftest.pdf"))) {

            int count = 1;
//            for (PDPage page : document.getPages()) {
//                InputStream pageStream = page.getContents();
//                String content = convertInputStreamToString(pageStream);
//                saveStringToFile(content, "src/main/resources/io/page" + count++ + ".txt");

//                Path saveFilePath = Paths.get("src/main/resources/io/page" + count++);
//                Files.copy(pageStream, saveFilePath, StandardCopyOption.REPLACE_EXISTING);


//            }

            PDFTextStripper stripper = new PDFTextStripper();
            String content = stripper.getText(document);
            saveStringToFile(content, "src/main/resources/io/pageall.txt");


        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }


    }

    public static String convertInputStreamToString(InputStream inputStream) throws IOException {
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append(System.lineSeparator());
            }
        }
        return resultStringBuilder.toString();
    }

    public static void saveStringToFile(String data, String filePath) throws IOException {
        Files.write(Paths.get(filePath), data.getBytes(), StandardOpenOption.CREATE);
    }

}
