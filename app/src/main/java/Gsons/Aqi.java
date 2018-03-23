package Gsons;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Liang Zihong on 2018/3/23.
 */

public class Aqi {

    @SerializedName("city")
    private Aqi_City aqi_city;

    public Aqi_City getAqi_city() {
        return aqi_city;
    }

    public class Aqi_City {
        private String aqi;
        private String pm25;
        private String qlty;

        public String getAqi() {
            return aqi;
        }

        public String getPm25() {
            return pm25;
        }

        public String getQlty() {
            return qlty;
        }
    }

}
