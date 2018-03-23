package Gsons;

import android.util.Log;

import com.google.gson.Gson;

/**
 * Created by Liang Zihong on 2018/3/23.
 */

public class GsonTest {
    private String name;
    private int age;
    private int mark;



    public static void testGson(){
        String jsondata="{\"HeWeather\": [{\"basic\":{\"cid\":\"CN101280800\",\"location\":\"佛山\"," +
                "\"parent_city\":\"佛山\",\"admin_area\":\"广东\",\"cnty\":\"中国\",\"lat\":\"23.02876282\"," +
                "\"lon\":\"113.12271881\",\"tz\":\"+8.00\",\"city\":\"佛山\",\"id\":\"CN101280800\"," +
                "\"update\":{\"loc\":\"2018-03-23 11:47\",\"utc\":\"2018-03-23 03:47\"}}," +
                "\"update\":{\"loc\":\"2018-03-23 11:47\",\"utc\":\"2018-03-23 03:47\"},\"status\":\"ok\"" +
                ",\"now\":{\"cloud\":\"0\",\"cond_code\":\"101\",\"cond_txt\":\"多云\",\"fl\":\"22\",\"hum\":\"51\"" +
                ",\"pcpn\":\"0.0\",\"pres\":\"1019\",\"tmp\":\"22\",\"vis\":\"6\",\"wind_deg\":\"297\"," +
                "\"wind_dir\":\"西北风\",\"wind_sc\":\"1\",\"wind_spd\":\"2\",\"cond\":{\"code\":\"101\"," +
                "\"txt\":\"多云\"}},\"daily_forecast\":[{\"date\":\"2018-03-23\",\"cond\":{\"txt_d\":\"晴\"}," +
                "\"tmp\":{\"max\":\"24\",\"min\":\"15\"}},{\"date\":\"2018-03-24\",\"cond\":{\"txt_d\":\"多云\"}," +
                "\"tmp\":{\"max\":\"24\",\"min\":\"17\"}},{\"date\":\"2018-03-25\",\"cond\":{\"txt_d\":\"小雨\"}," +
                "\"tmp\":{\"max\":\"24\",\"min\":\"18\"}}],\"aqi\":{\"city\":{\"aqi\":\"120\",\"pm25\":\"91\"," +
                "\"qlty\":\"轻度污染\"}},\"suggestion\":{\"comf\":{\"brf\":\"舒适\",\"txt\":\"白天不太热也不太冷，风力不大，" +
                "相信您在这样的天气条件下，应会感到比较清爽和舒适。\",\"type\":\"comf\"},\"sport\":{\"brf\":\"适宜\"," +
                "\"txt\":\"天气较好，赶快投身大自然参与户外运动，尽情感受运动的快乐吧。\",\"type\":\"sport\"}," +
                "\"cw\":{\"brf\":\"较适宜\",\"txt\":\"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。\"," +
                "\"type\":\"cw\"}}}]}";


        All all=new All();
        Gson gson=new Gson();
        all=gson.fromJson(jsondata,All.class);
        All.HeWeather heweather=all.getHeWeathers().get(0);

        Log.i("info", "testGson: "+heweather.getBasic().getCity());
    }

}
