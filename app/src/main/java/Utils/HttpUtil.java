package Utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Daos.DaoOfCity;
import Daos.DaoOfDistrict;
import Daos.DaoOfProvince;
import Models.City;
import Models.District;
import Models.Province;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Liang Zihong on 2018/3/21.
 */

public class HttpUtil {

    public static final String UrlOfPlaces="http://guolin.tech/api/china";
    private static List<Province> provinceList=new ArrayList<>();

    public interface callBackListener{
        public void FinishToDo(String content);
        public void ErrorToDo();
    }


    /**
     * 返回  response 的内容
     * 因为send网络请求是耗时操作，所以只能由子线程去执行
     * 如果此时让主线程空转 等待子线程是不明智的，所以只能运用回调参数去解决
     * @param url
     * @return
     */
    public static void sendHttpRequest(final String url, final callBackListener listener){


        new Thread(new Runnable() {
            String content;
            @Override
            public void run() {
                OkHttpClient client=new OkHttpClient();
                Request request=new Request.Builder()
                        .url(url)
                        .build();

                try{
                    content=client.newCall(request).execute().body().string();
                    if(content!=null)
                        listener.FinishToDo(content);
                    else
                        listener.ErrorToDo();

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }








}





















