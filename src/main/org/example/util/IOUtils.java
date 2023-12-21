package main.org.example.util;

import java.io.*;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IOUtils {
    public static void printStat(String path) {
        File rootFile = new File(path);
        if (!rootFile.exists()) {
            System.out.println("Invalid file path");
            return;
        }
        if (rootFile.isDirectory()) {
            System.out.println(String.format("FOLDER  <%s><lastModified: %s>",
                    rootFile.getName(), new Date(rootFile.lastModified()).toString()));
            File[] files = rootFile.listFiles();
            if (files != null)
                for (File file : files) {
                    printStat(file.getPath());

                }

        } else {
            // it should be file
            System.out.println(String.format("FILE --->  <%s><Size: %d>",
                    rootFile.getName(), rootFile.length()));
        }
    }

    public static String readFile(String path) {

        StringBuilder sb = new StringBuilder(214748364);
        try {
            Reader reader = new BufferedReader(new FileReader(path), 214748364);// исп-ем try-with-resourses. See AutoCloseable

            int characterCode;
            while ((characterCode = reader.read()) != -1) {
                sb.append((char) characterCode);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Check you file path");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public static String readFileByLines(String path) {

        StringBuilder sb = new StringBuilder(214748364);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path), 214748364);// исп-ем try-with-resourses. See AutoCloseable

            String line;

            reader.readLine();
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (FileNotFoundException e) {
            System.err.println("Check you file path");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    public static void write(String data, String path) {
        write(data, path, false);

    }

    public static void write(String data, String path, boolean append) {
        try (Writer writer = new BufferedWriter(new FileWriter(path, append))) {
            writer.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public static void main(String[] args) {
        try (Resource res = new Resource()) {
            //working with resource
            res.addLine("hello");
            res.addLine("world");
            res.printData();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void copyFiles(String dstPath, String path1, String path2) {
        String content1 = readFile(path1);
        String content2 = readFile(path2);
        write(content1 + content2, dstPath);
    }


    public static void copyFiles(String dstPath, String... paths) {
        StringBuilder sb = new StringBuilder();
        for (String path : paths) {
            sb.append(readFile(path));
        }
        write(sb.toString(), dstPath);

    }

    public static void findKeyWord(String word, String path) {
        String content = readFile(path);
        if (content.toLowerCase().contains(word.toLowerCase())) {
            System.out.println("Search text is found");
        } else {
            System.out.println("Search text is not found");
        }

    }
}


class Resource implements Closeable {

    private String data = new String();

    @Override
    public void close() throws IOException {
        if (data.length() > 0) {
            data = new String();
            System.out.println("Resource data clear");
        }
    }

    public void addLine(String line) {
        data += line;

    }

    public void printData() {
        System.out.println(data);
    }
}

class Tasks {
    static void t1(String path, String resultPath) {

        System.out.println("---------Task #1 Reverse file text");
        //1 step: read content
        String data = IOUtils.readFile(path);

        //2 step: process String value
        String result = new StringBuilder(data).reverse().toString();

        //3 step: write result file
        IOUtils.write(result, resultPath);
    }

    public static void main(String[] args) {
        t1("D:\\io_tests\\tasks\\task#1.txt", "D:\\io_tests\\tasks\\result_task#1.txt");
        t2("D:/io_tests/war_and_peace.ru.txt", "D:/io_tests/result_war_and_peace.ru.txt", "мир");
    }


    static void t2(String filePath, String resultFilePath, String search) {
        //search word : counter
        // "Java"    14
        System.out.println("---------Task #2 Search------------");

        String resultFormat = "\"%s\" : %d";
        int counter = 0;
        String content = IOUtils.readFile(filePath);
        if (content.toLowerCase().contains(search.toLowerCase())) {
            System.out.println("Search text is found");
            Pattern p = Pattern.compile(search.toLowerCase());
            Matcher m = p.matcher(content.toLowerCase());
            while (m.find()) {
                counter++;
            }
        } else {
            System.out.println("Search text is not found");
        }




        IOUtils.write(String.format(resultFormat,search,counter), resultFilePath);

        System.out.println(search + " : " + counter);
    }
}
