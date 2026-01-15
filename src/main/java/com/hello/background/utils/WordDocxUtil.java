package com.hello.background.utils;

import com.hello.background.common.CommonUtils;
import com.hello.background.domain.Candidate;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class WordDocxUtil {


    /**
     * 下载候选人简历Word文档
     *
     * @param response HTTP响应对象
     * @throws IOException IO异常
     */
    public static void downloadCandidateResumeDocx(HttpServletResponse response, Candidate candidate) throws IOException {
        CommonUtils.calcAge(candidate);
        XWPFDocument document = new XWPFDocument();
        // 通用标题样式
        FontStyle titleFontStyle = new FontStyle(16, null, true);

        // 设置姓名
        addParagraph(document, candidate.getChineseName(), titleFontStyle);
        // 设置年龄
        addParagraph(document, "电话：" + candidate.getPhoneNo(), null);
        addParagraph(document, "邮箱：" + candidate.getEmail(), null);
        addParagraph(document, "年龄：" + candidate.getAge().toString(), null);
        addParagraph(document, "生日：" + candidate.getBirthDay(), null);
        addParagraph(document, "性别：" + candidate.getGender().getDescribe(), null);
        addParagraph(document, "籍贯：" + candidate.getHometown(), null);
        addParagraph(document, "居住地址：" + candidate.getCurrentAddress(), null);
        addParagraph(document, "家庭情况：" + candidate.getFamily(), null);
        // 添加空白段落
        addParagraph(document, "", null);
        // 添加自我介绍
        addParagraph(document, "自我评价：", titleFontStyle);
        addParagraph(document, candidate.getRemark(), null);
        // 添加空白段落
        addParagraph(document, "", null);
        // 设置工作经历标题
        addParagraph(document, "工作经历：", titleFontStyle);
        // 设置工作内容
        addParagraph(document, candidate.getCompanyName(), null);
        // 添加空白段落
        addParagraph(document, "", null);
        // 添加教育经历标题
        addParagraph(document, "教育经历：", titleFontStyle);
        // 添加学校内容
        addParagraph(document, candidate.getSchoolName(), null);

        writeDocumentToResponse(response, candidate.getChineseName(), document);
    }

    private static void addParagraph(XWPFDocument document, String value, FontStyle fontStyle) {
        // 首先将输入值按行换行进行分割
        String[] split = value.split("\n");
        for (int i = 0; i < split.length; i++) {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            if (fontStyle != null) {
                // 设置字体样式
                run.setFontFamily(fontStyle.fontFamily);
                run.setBold(fontStyle.isBold);
                run.setFontSize(fontStyle.fontSize);
            }
            // 添加段落文本
            run.setText(split[i]);
        }
    }

    /**
     * 下载Word文档
     *
     * @param response HTTP响应对象
     * @param fileName 文件名（不包含扩展名）
     * @param content  Word文档内容
     * @throws IOException IO异常
     */
    public static void downloadWordDocx(HttpServletResponse response, String fileName, String content) throws IOException {
        XWPFDocument document = new XWPFDocument();

        // 添加段落内容
        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(content);

        writeDocumentToResponse(response, fileName, document);
    }

    /**
     * 根据模板生成Word文档并下载
     *
     * @param response        HTTP响应对象
     * @param fileName        文件名（不包含扩展名）
     * @param templateContent 模板内容（包含占位符，如${name}）
     * @param data            数据映射，用于替换模板中的占位符
     * @throws IOException IO异常
     */
    public static void downloadWordDocxFromTemplate(HttpServletResponse response, String fileName, String templateContent, Map<String, String> data) throws IOException {
        XWPFDocument document = new XWPFDocument();

        // 替换模板中的占位符
        String processedContent = replacePlaceholders(templateContent, data);

        XWPFParagraph paragraph = document.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText(processedContent);

        writeDocumentToResponse(response, fileName, document);
    }

    /**
     * 生成包含多个段落的Word文档
     *
     * @param response   HTTP响应对象
     * @param fileName   文件名（不包含扩展名）
     * @param paragraphs 段落列表
     * @throws IOException IO异常
     */
    public static void downloadWordDocxWithParagraphs(HttpServletResponse response, String fileName, List<String> paragraphs) throws IOException {
        XWPFDocument document = new XWPFDocument();

        for (String paragraphText : paragraphs) {
            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();
            run.setText(paragraphText);
        }

        writeDocumentToResponse(response, fileName, document);
    }

    /**
     * 生成格式化Word文档（包含标题、段落等）
     *
     * @param response HTTP响应对象
     * @param fileName 文件名（不包含扩展名）
     * @param title    标题
     * @param content  正文内容
     * @throws IOException IO异常
     */
    public static void downloadFormattedWordDocx(HttpServletResponse response, String fileName, String title, String content) throws IOException {
        XWPFDocument document = new XWPFDocument();

        // 添加标题
        XWPFParagraph titleParagraph = document.createParagraph();
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = titleParagraph.createRun();
        titleRun.setText(title);
        titleRun.setBold(true);
        titleRun.setFontSize(16);

        // 添加正文
        XWPFParagraph contentParagraph = document.createParagraph();
        XWPFRun contentRun = contentParagraph.createRun();
        contentRun.setText(content);

        writeDocumentToResponse(response, fileName, document);
    }

    /**
     * 将文档写入HTTP响应
     *
     * @param response HTTP响应对象
     * @param fileName 文件名
     * @param document Word文档对象
     * @throws IOException IO异常
     */
    private static void writeDocumentToResponse(HttpServletResponse response, String fileName, XWPFDocument document) throws IOException {
        response.setContentType("application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".docx");

        ServletOutputStream outputStream = response.getOutputStream();
        document.write(outputStream);
        outputStream.flush();
        document.close();
    }

    /**
     * 替换文本中的占位符
     *
     * @param template 模板文本
     * @param data     数据映射
     * @return 替换后的文本
     */
    private static String replacePlaceholders(String template, Map<String, String> data) {
        String result = template;
        for (Map.Entry<String, String> entry : data.entrySet()) {
            result = result.replace("${" + entry.getKey() + "}", entry.getValue());
        }
        return result;
    }
}
