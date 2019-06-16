package com.godsnet.godsnet.freemark;


import java.util.Map;

public enum ColumnsType {
    INT("INT","Integer"),
    VARCHAR("VARCHAR","String"),
    DECIMAL("DECIMAL","BigDecimal"),
    DATETIME("DATETIME","Date");
    private String code;
    private String value;
    public static Map<String, String> map = null;

    ColumnsType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static Map<String, String> getMap() {
        return map;
    }

    public static void setMap(Map<String, String> map) {
        ColumnsType.map = map;
    }
}
