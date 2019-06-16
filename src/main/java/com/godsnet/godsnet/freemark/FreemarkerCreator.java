package com.godsnet.godsnet.freemark;

import com.godsnet.godsnet.enums.GodsErrorCode;
import com.godsnet.godsnet.exception.GodsException;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FreemarkerCreator {
    private static final String TEMPLATE_PATH = "C:\\Program Files\\JetBrains\\godsnet\\src\\main\\java\\com\\godsnet\\godsnet\\freemark";
    private static final String CLASS_PATH = "C:\\Program Files\\JetBrains\\godsnet\\src\\main\\java\\com\\godsnet\\godsnet\\freemark";
    private static Logger logger = LoggerFactory.getLogger(FreemarkerCreator.class);

    /**
     * 创建Entity类
     * @param tableName
     */
    public static Map createEntity(String  tableName) throws GodsException {

        //  创建freeMarker配置实例
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            Map<String,String> tableMap = new HashMap<>();
            try{
                GetEntityOnMysql getEntityOnMysql = new GetEntityOnMysql();
                tableMap = getEntityOnMysql.getTableStructureByName(tableName);
            }catch (Exception e){
                throw new GodsException(GodsErrorCode.SYSTEM_ERROR,e.getMessage());
            }

            //  获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));
            //  创建数据模型
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("classPath", "com.godsnet.godsnet.freemark");
            dataMap.put("tableName",tableName);
            dataMap.put("className", getClassName(tableName));
            dataMap.put("vmap", tableMap);
            //  加载模版文件
            Template template = configuration.getTemplate("entityClass.ftl");
            //  生成数据
            File docFile = new File(CLASS_PATH + "\\" + getClassName(tableName) +".java");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            //  输出文件
            template.process(dataMap, out);
            logger.info("********************Entity文件创建成功 !********************");
            return tableMap;
        } catch (Exception e) {
            throw new GodsException(GodsErrorCode.SYSTEM_ERROR,e.getMessage());
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e) {
                throw new GodsException(GodsErrorCode.SYSTEM_ERROR,e.getMessage());
            }
        }
    }

    /**
     * 创建Controller
     * @param tableName
     * @throws GodsException
     */
    public static void createController(String tableName) throws GodsException {

        //  创建freeMarker配置实例
        Configuration configuration = new Configuration();
        Writer out = null;
        try {
            //  获取模版路径
            configuration.setDirectoryForTemplateLoading(new File(TEMPLATE_PATH));
            //  创建数据模型
            Map<String, Object> dataMap = new HashMap<>();
            dataMap.put("classPath", "com.godsnet.godsnet.freemark");
            dataMap.put("className", getClassName(tableName));
            //  加载模版文件
            Template template = configuration.getTemplate("controllerClass.ftl");
            //  生成数据
            File docFile = new File(CLASS_PATH + "\\" + getClassName(tableName) +".java");
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(docFile)));
            //  输出文件
            template.process(dataMap, out);
            logger.info("********************Controller文件创建成功 !********************");
        } catch (Exception e) {
            throw new GodsException(GodsErrorCode.SYSTEM_ERROR,e.getMessage());
        } finally {
            try {
                if (null != out) {
                    out.flush();
                }
            } catch (Exception e) {
                throw new GodsException(GodsErrorCode.SYSTEM_ERROR,e.getMessage());
            }
        }
    }

    /**
     * 规范化类名称
     * @param tableName
     * @return
     */
    public static String getClassName(String tableName){
        String[] arrStr = tableName.split("_");
        StringBuffer sb = new StringBuffer();
        for(int i = 1 ; i < arrStr.length ; i++){
            char[] cc = arrStr[i].toCharArray();
            cc[0] -= 32;
            sb.append(String.valueOf(cc));
        }
        return String.valueOf(sb);
    }

}
