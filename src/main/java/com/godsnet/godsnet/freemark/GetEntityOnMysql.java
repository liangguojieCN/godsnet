package com.godsnet.godsnet.freemark;

import com.godsnet.godsnet.enums.GodsErrorCode;
import com.godsnet.godsnet.exception.GodsException;
import org.springframework.util.StringUtils;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class GetEntityOnMysql {

    public Map getTableStructureByName(String databaseName) throws IOException {
        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test?characterEncoding=utf8&serverTimezone=UTC";
        String username = "root";
        String password = "";
        Map<String, String> columnNameMap = new HashMap<>();
        //加载驱动
        try {
            Class.forName(driver);
            //获得数据库连接
            Connection connection = DriverManager.getConnection(url, username, password);
            //获得元数据
            DatabaseMetaData metaData = connection.getMetaData();
            //获得表信息
            ResultSet tables = metaData.getTables(null, null, null, new String[]{"TABLE"});
            while (tables.next()) {
                columnNameMap = new HashMap<>(); //保存字段名
                //获得表名
                String table_name = tables.getString("TABLE_NAME");
                if(table_name.equals(databaseName)){
                    //通过表名获得所有字段名
                    ResultSet columns = metaData.getColumns(null, null, table_name, "%");
                    //获得所有字段名
                    while (columns.next()) {
                        //获得字段名
                        String column_name = columns.getString("COLUMN_NAME");
                        column_name = getColumnName(column_name);
                        //获得字段类型
                        String type_name = columns.getString("TYPE_NAME");
                        columnNameMap.put(column_name,ColumnsType.valueOf(type_name).getValue());
                    }
                    break;
                }
            }
            return columnNameMap;
        }catch (ClassNotFoundException e){
            throw new GodsException(GodsErrorCode.SYSTEM_ERROR,e.getMessage());
        }catch (SQLException e){
            throw new GodsException(GodsErrorCode.SYSTEM_ERROR,e.getMessage());
        }
    }


    public String getColumnName(String column_name) throws GodsException{
        if(StringUtils.isEmpty(column_name)){
            throw new GodsException(GodsErrorCode.PARAM_MISSING,"存在表的字段名为空");
        }
        String[] strArr = column_name.split("_");
        StringBuffer columnName = new StringBuffer();
        for(int i = 0 ; i < strArr.length ; i++){
            if(i == 0){
                columnName.append(strArr[i].toLowerCase());
            }else{
                char[] cc = strArr[i].toLowerCase().toCharArray();
                cc[0] -= 32;
                columnName.append(String.valueOf(cc));
            }
        }
        return String.valueOf(columnName);
    }
}