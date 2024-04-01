package com.hello.background.utils;

import com.alibaba.excel.EasyExcel;
import com.mysql.jdbc.StringUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * easy excel 工具类
 *
 * @author wuketao
 * @date 2022/9/1
 * @Description
 */
public class EasyExcelUtil {
    /**
     * 下载excel文件
     */
    public static <E> void downloadExcel(HttpServletResponse response, String fileName, String sheetName, List<E> list, Class<E> eClass) throws IOException {
        ServletOutputStream outputStream = response.getOutputStream();
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Content-disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx");
        EasyExcel.write(outputStream, eClass).sheet(StringUtils.isNullOrEmpty(sheetName) ? "sheet1" : sheetName).doWrite(list);
        outputStream.flush();
    }
}
