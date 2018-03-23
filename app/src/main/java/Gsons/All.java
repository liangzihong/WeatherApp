package Gsons;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Liang Zihong on 2018/3/23.
 */

public class All {

    @SerializedName("HeWeather")
    private List<HeWeather> heWeathers;

    public List<HeWeather> getHeWeathers() {
        return heWeathers;
    }

    public class HeWeather {
        private Basic basic;
        private Update update;
        private Now now;
        private List<ForecastDay> daily_forecast;
        private Aqi aqi;
        private Suggestion suggestion;

        public Basic getBasic() {
            return basic;
        }

        public Update getUpdate() {
            return update;
        }

        public Now getNow() {
            return now;
        }

        public List<ForecastDay> getDaily_forecast() {
            return daily_forecast;
        }

        public Aqi getAqi() {
            return aqi;
        }

        public Suggestion getSuggestion() {
            return suggestion;
        }
    }



}
