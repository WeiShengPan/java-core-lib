package com.pws.jsoup;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class IMoreSpider {

    public static final String PATH = "/Users/xxx";

    public static void main(String[] args) {

        List<String> articles = Lists.newArrayList();
        articles.add("https://www.imore.com/health-fitness/apple-watch/apple-watch-is-getting-a-major-redesign-but-not-this-year");
        articles.add("https://www.imore.com/apple/apple-announces-wwdc-2023-for-june-5-our-biggest-and-most-exciting-yet");
        articles.add("https://www.imore.com/health-fitness/apple-watch/watchos-10-could-be-the-biggest-update-to-the-apple-watch-in-years");
        articles.add("https://www.imore.com/mac/mac-sales-are-so-bad-apple-had-to-stop-making-m2-chips");
        articles.add("https://www.imore.com/iphone/iphone-15/iphone-15s-rumored-usb-c-restrictions-could-land-apple-in-big-trouble");


        List<String> historyArticles = readHistory();

        articles.forEach(s -> {
            if (historyArticles.contains(s)) {
                System.out.println("文章已有处理历史！" + s);
            } else {
                fetchIMore(s);
            }
        });
    }

    private static void fetchIMore(String url) {
        try {
            String articlePath = String.valueOf(System.currentTimeMillis());
            int lastIndexOfSlash = url.lastIndexOf("/");
            if (lastIndexOfSlash != -1 && lastIndexOfSlash != url.length() - 1) {
                articlePath = PATH + url.substring(lastIndexOfSlash);
                File dir = new File(articlePath);
                dir.mkdirs();
                System.out.println("Article path: " + articlePath);
            } else {
                System.out.println("WARN! 截取url失败：" + url);
                return;
            }

            List<String> contents = Lists.newArrayList();

            Document doc = Jsoup.connect(url).get();
            Element article = doc.selectFirst("article");
            Element heading = article.selectFirst("h1");

            contents.add("Title: " + heading.text());
            contents.add("\nContent:");

            Elements allElements = article.select("*");
            for (Element e : allElements) {
                if (e.tagName().equals("p") && StringUtils.isNotBlank(e.text())) {
                    contents.add("\n " + e.text());
                }
                if (e.tagName().equals("h2")) {
                    contents.add("\n\n H2: " + e.text());
                }
            }

            writeContentToFile(contents, articlePath);

            downloadContentImagesV2(url, articlePath);

            writeHistory(url);

            GPTCompletion.translateIntoChinese(articlePath);

            System.out.println("DONE!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeContentToFile(List<String> contentList, String filePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath + "/content.txt", true))) {

            for (String content : contentList) {
                if (StringUtils.contains(content, "Get more iMore in your inbox")) {
                    break;
                }
                StringUtils.replace(content, "(opens in new tab)", "");
                writer.write(content);
                writer.newLine();
            }
        }
    }

    private static void downloadContentImages(String pageUrl, String picPath) {
        try {
            Document document = Jsoup.connect(pageUrl).get();
            Elements images = document.select("article img");
            for (Element image : images) {
                String imageUrl = image.attr("abs:src");
                if (StringUtils.contains(imageUrl, "missing-image")) {
                    imageUrl = image.attr("data-pin-media");
                    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                    doSavePicture(imageUrl, fileName, picPath);
//                    String srcset = image.attr("srcset");
//                    List<String> sourceList = Lists.newArrayList(srcset.split(","));
//                    for (String sourceStr : sourceList) {
//                        String src = sourceStr.trim().split(" ")[0]; // 获取拆分出来的第一个图片地址
//                        String fileName = src.substring(imageUrl.lastIndexOf("/") + 1);; // 通过地址获取文件名
//                        doSavePicture(src, fileName, picPath);
//                    }
                } else {
                    String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                    doSavePicture(imageUrl, fileName, picPath);
                }
            }
            System.out.println("所有图片已下载完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void downloadContentImagesV2(String pageUrl, String picPath) {
        try {
            Document document = Jsoup.connect(pageUrl).get();
            Elements images = document.select("article img");
            for (Element image : images) {
                String imageUrl = image.attr("data-pin-media");
                String fileName = imageUrl.substring(imageUrl.lastIndexOf("/") + 1);
                doSavePicture(imageUrl, fileName, picPath);
            }
            System.out.println("所有图片已下载完成！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doSavePicture(String imageUrl, String fileName, String picPath) {
        if (StringUtils.startsWith(imageUrl, "http")) {
            Path saveFilePath = Paths.get(picPath + File.separator + fileName);
            try (InputStream in = new URL(imageUrl).openStream()) {
                Files.copy(in, saveFilePath, StandardCopyOption.REPLACE_EXISTING);
                System.out.println("已下载图片：" + fileName);
            } catch (IOException e) {
                System.out.println("下载图片失败：" + imageUrl);
                e.printStackTrace();
            }
        }
    }

    public static List<String> readHistory() {
        List<String> strings = Lists.newArrayList();
        File file = new File(PATH + "/history.txt");

        if (!file.exists() || !file.isFile() || !file.canRead()) {
            return Lists.newArrayList();
        }

        try (FileReader fr = new FileReader(file); BufferedReader br = new BufferedReader(fr)) {
            String line;
            while ((line = br.readLine()) != null) {
                strings.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return strings;
    }

    private static void writeHistory(String url) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PATH + "/history.txt", true))) {
            writer.write(url);
            writer.newLine();
        }
    }


}
