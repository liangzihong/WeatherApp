package Gsons;

/**
 * Created by Liang Zihong on 2018/3/23.
 */

public class Suggestion {

    private Comf comf;
    private Sport sport;
    private Cw cw;


    public Comf getComf() {
        return comf;
    }

    public Sport getSport() {
        return sport;
    }

    public Cw getCw() {
        return cw;
    }

    public class Comf {
        private String brf;
        private String txt;

        public String getBrf() {
            return brf;
        }

        public String getTxt() {
            return txt;
        }
    }


    public class Sport {
        private String brf;
        private String txt;

        public String getBrf() {
            return brf;
        }

        public String getTxt() {
            return txt;
        }
    }


    public class Cw {
        private String brf;
        private String txt;

        public String getBrf() {
            return brf;
        }

        public String getTxt() {
            return txt;
        }
    }
}
