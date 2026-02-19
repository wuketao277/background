package com.hello.background;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.io.*;

/**
 * @author wuketao
 * @date 2019/11/24
 * @Description
 */
@EnableScheduling
@EnableSwagger2
@SpringBootApplication
//@EnableRedisHttpSession
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    /**
     * 拆到db文件
     */
//    public static void main(String[] args) {
//        splitDBFile();
//    }
    public static void splitDBFile() {
        try {
            // 行号
            Integer rowNo = 1;
            // 文件后缀
            Integer fileNo = 1;
            // 原始数据文件
            File fileSource = new File("/Users/wuketao/Downloads/db_dump.sql");
            FileReader reader = new FileReader(fileSource);
            BufferedReader breader = new BufferedReader(reader);
            // 临时存储
            String context = "";
            while (true) {
                // 读取一行内容存入临时存储变量中
                context += breader.readLine();
                if (null == context) {
                    // 如果已经读到文件结尾，就返回。
                    break;
                } else if (context.endsWith(");")) {
                    // 如果读完一条完整sql就写入到新文件中
                    System.out.println(rowNo);
                    writeDataToFile(context, fileNo);
                    // 清空临时变量
                    context = "";
                    // 每次读取一行数据，行号自动加一
                    rowNo++;
                    // 控制一个新文件中存储的内容不要太多
                    if (rowNo > 40000) {
                        rowNo = 1;
                        fileNo++;
                    }
                }
            }
            breader.close();
            reader.close();
        } catch (Exception ex) {
        }
    }

    /**
     * 将数据写入文件
     *
     * @param content 数据
     * @param fileNo  文件编号
     */
    private static void writeDataToFile(String content, Integer fileNo) {
        try {
            // 组装文件名
            String fileName = String.format("/Users/wuketao/Downloads/db%s.sql", fileNo);
            // 文件不存在就先创建
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            // 将数据写入文件
            FileWriter fileWriter = new FileWriter(fileName, true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.newLine();
            bufferedWriter.write(content);
            bufferedWriter.close();
            fileWriter.close();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }
}
