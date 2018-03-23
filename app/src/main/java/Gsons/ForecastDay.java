package Gsons;

/**
 * Created by Liang Zihong on 2018/3/23.
 */

public class ForecastDay {

    private String date;
    private Cond cond;
    private Tmp tmp;

    public String getDate() {
        return date;
    }

    public Cond getCond() {
        return cond;
    }

    public Tmp getTmp() {
        return tmp;
    }

    public class Cond {
        private String txt_d;

        public String getTxt_d() {
            return txt_d;
        }
    }


    public class Tmp {
        private String max;
        private String min;

        public String getMax() {
            return max;
        }

        public String getMin() {
            return min;
        }
    }

}



