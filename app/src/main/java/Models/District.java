package Models;

import org.litepal.crud.DataSupport;

/**
 * Created by Liang Zihong on 2018/3/21.
 */

public class District extends DataSupport{

    private int city_id;
    private int district_id;
    private String name;
    private String weather_id;

    public District(int city_id, int district_id, String name, String weather_id) {
        this.city_id = city_id;
        this.district_id = district_id;
        this.name = name;
        this.weather_id = weather_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public int getDistrict_id() {
        return district_id;
    }

    public void setDistrict_id(int district_id) {
        this.district_id = district_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(String weather_id) {
        this.weather_id = weather_id;
    }
}
