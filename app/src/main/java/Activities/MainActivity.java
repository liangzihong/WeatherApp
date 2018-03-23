package Activities;

import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.liangzihong.myweather.R;

import java.util.ArrayList;
import java.util.List;

import Adapters.forecast_adapter;
import Adapters.forecast_model;
import Gsons.All;
import Gsons.Aqi;
import Gsons.Basic;
import Gsons.ForecastDay;
import Gsons.Now;
import Gsons.Suggestion;
import Gsons.Update;
import Models.District;
import Presenters.LoadPicPresenter;
import Presenters.UpdateDataPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ILoadPicView, ICloseDrawerView,IUpdateDataView {



    //控件
    private Button home_button;
    private DrawerLayout drawer;
    private ImageView backGroundImageView;
    private TextView title_cityName;
    private TextView title_updateTime;
    private TextView todayweather_temperature;
    private TextView todayweather_weatherState;
    private ListView forecast_listView;
    private TextView aqi_aqiValue;
    private TextView aqi_PMValue;
    private TextView suggestion_comfortText;
    private TextView suggestion_washCarText;
    private TextView suggestion_sportText;

//    private TextView forecast_item_date;
//    private TextView forecast_item_weatherState;
//    private TextView forecast_item_max;
//    private TextView forecast_item_min;


    //listview的适配器和数据源
    private List<forecast_model> dataSource;
    private forecast_adapter adapter;


    //Presenter
    private LoadPicPresenter loadPicPresenter;
    private UpdateDataPresenter updateDataPresenter;


    //选中要看的区县
    private District selectedDistrict;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_main_layout);
        init();

        //Gson 数据测试
        //GsonTest.testGson();

        loadPicToView();
    }


    private void init(){
        home_button=(Button)findViewById(R.id.title_homeButton);
        backGroundImageView=(ImageView)findViewById(R.id.weather_main_imageView);
        drawer=(DrawerLayout)findViewById(R.id.drawlayout_drawer);

        title_cityName=(TextView)findViewById(R.id.title_cityName);
        title_updateTime=(TextView)findViewById(R.id.title_updateTime);
        todayweather_temperature=(TextView)findViewById(R.id.todayweather_temperature);
        todayweather_weatherState=(TextView)findViewById(R.id.todayweather_weatherState);
        forecast_listView=(ListView)findViewById(R.id.forecast_listView);
        aqi_aqiValue=(TextView)findViewById(R.id.aqi_aqiValue);
        aqi_PMValue=(TextView)findViewById(R.id.aqi_PMValue);
        suggestion_comfortText=(TextView)findViewById(R.id.suggestion_comfortText);
        suggestion_washCarText=(TextView)findViewById(R.id.suggestion_washCarText);
        suggestion_sportText=(TextView)findViewById(R.id.suggestion_sportText);



//        forecast_item_date=(TextView)findViewById(R.id.forecast_item_date);
//        forecast_item_weatherState=(TextView)findViewById(R.id.forecast_item_weatherState);
//        forecast_item_max=(TextView)findViewById(R.id.forecast_item_max);
//        forecast_item_min=(TextView)findViewById(R.id.forecast_item_min);


        dataSource=new ArrayList<>();
        adapter=new forecast_adapter(this,R.layout.forecast_item_layout,dataSource);
        forecast_listView.setAdapter(adapter);
        /**
         * 还剩一个forecast的listview要配置数据
         */








        home_button.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.title_homeButton:
                drawer.openDrawer(GravityCompat.START);

        }
    }




























    //下面是实现MVP接口的方法

    //1 ILoadPicView的接口


    /**
     * 让LoadPicPresenter开始加载图片
     */
    @Override
    public void loadPicToView() {
        loadPicPresenter=new LoadPicPresenter(this);
        loadPicPresenter.LoadPicture();
    }


    /**
     * 返回 backgroundImageView任人宰割
     * @return
     */
    @Override
    public ImageView getImageView() {
        return backGroundImageView;
    }


    /**
     * 返回 Activity
     * @return
     */
    @Override
    public Activity getActivity(){
        return this;
    }




    //2 ICloseDrawerView的接口


    /**
     * 关掉抽屉
     * 并开启 更新数据模式
     * 并选中 区县
     */
    @Override
    public void closeDrawer(District district){
        selectedDistrict=district;
        drawer.closeDrawer(GravityCompat.START);
        startUpdate();
    }



    //3 IUpdateDataView的接口


    /**
     * 调用 updateDataPresenter开始更逊数据
     * 传入 选中区县的 天气id作为参数
     */
    @Override
    public void startUpdate() {
        updateDataPresenter=new UpdateDataPresenter(this);
        updateDataPresenter.UpdateData(selectedDistrict.getWeather_id());
    }


    /**
     * 用一个  All作为参数，通过这个all去更新UI
     * @param allModel
     */
    @Override
    public void changeView(All allModel){
        All.HeWeather heWeather=allModel.getHeWeathers().get(0);
        Basic basic=heWeather.getBasic();
        Update updateTime=heWeather.getUpdate();
        Now now=heWeather.getNow();
        List<ForecastDay> forecastDayList= heWeather.getDaily_forecast();
        Aqi aqi=heWeather.getAqi();
        Suggestion suggestion=heWeather.getSuggestion();

        title_cityName.setText(basic.getParent_city()+"-"+basic.getLocation());
        title_updateTime.setText(updateTime.getLoc());

        todayweather_temperature.setText(now.getTmp());
        todayweather_weatherState.setText("风向："+now.getWind_dir()+"  "+
                                    "风力："+now.getWind_spd()+"  "+
                                    "天气："+now.getCond_txt());


        dataSource.clear();
        for(int i=0;i<forecastDayList.size();i++){
            ForecastDay day=forecastDayList.get(i);
            forecast_model model=new forecast_model(day.getDate(),day.getCond().getTxt_d(),
                    day.getTmp().getMax(), day.getTmp().getMin());
            dataSource.add(model);
        }
        adapter.notifyDataSetChanged();


        aqi_aqiValue.setText(aqi.getAqi_city().getAqi());
        aqi_PMValue.setText(aqi.getAqi_city().getPm25());


        suggestion_comfortText.setText(suggestion.getComf().getBrf()+"\n"+suggestion.getComf().getTxt());
        suggestion_washCarText.setText(suggestion.getCw().getBrf()+"\n"+suggestion.getCw().getTxt());
        suggestion_sportText.setText(suggestion.getSport().getBrf()+"\n"+suggestion.getSport().getTxt());

    }

}






























