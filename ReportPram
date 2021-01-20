package com.puer.brand.util;

import lombok.Data;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author shenggongjie
 * @date 2021/1/4 13:28
 */
@Data
public class ReportPram {
    /**
     * Excel的导出的参数
     * @param list 需要导出数据的集合
     * @param response 响应到页面
     * @param fileName 导出默认文件名
     * @param title excel标题
     * @param sheetName 空间名
     * @param clazz 导出格式实体类
     */
    private List list;
    private HttpServletResponse response;
    private String fileName;
    private String title;
    private String sheetName;
    private Class clazz;
}
