package Models;

import org.litepal.crud.DataSupport;

/**
 * Created by Liang Zihong on 2018/3/21.
 */

public class City extends DataSupport{

    private int province_id;
    private int city_id;
    private String name;

    public City(int province_id, int city_id, String name) {
        this.province_id = province_id;
        this.city_id = city_id;
        this.name = name;
    }

    public int getProvince_id() {
        return province_id;
    }

    public void setProvince_id(int province_id) {
        this.province_id = province_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}










