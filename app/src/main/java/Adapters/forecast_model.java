package Adapters;

import android.widget.ArrayAdapter;

/**
 * Created by Liang Zihong on 2018/3/23.
 */

public class forecast_model{

    private String date;
    private String state;
    private String max;
    private String min;

    public forecast_model(String date, String state, String max, String min) {
        this.date = date;
        this.state = state;
        this.max = max;
        this.min = min;
    }

    public String getDate() {
        return date;
    }

    public String getState() {
        return state;
    }

    public String getMax() {
        return max;
    }

    public String getMin() {
        return min;
    }
}
