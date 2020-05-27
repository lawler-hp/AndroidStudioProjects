package com.example.coolweather.db;

import org.litepal.crud.LitePalSupport;

/**
 * id 是每个实体类中都应该有的字段, provinceName 记录省的名字，provinceCode记录省的代号。
 * 另外，LitePal 中的每一个实体类都是必须要继承自LitePalSupport类的。
 */

public class Province extends LitePalSupport {
    private int id;
    private String provinceName;
    private int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
