package Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liangzihong.myweather.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Activities.ICloseDrawerView;
import Daos.DaoOfCity;
import Daos.DaoOfDistrict;
import Daos.DaoOfProvince;
import Models.City;
import Models.District;
import Models.Province;
import Utils.HttpUtil;

import static Utils.HttpUtil.sendHttpRequest;

/**
 * Created by Liang Zihong on 2018/3/21.
 */

public class ChooseLocationFragment extends Fragment implements AdapterView.OnItemClickListener{

    //控件
    private ListView listView;
    private Button Province_Button;
    private Button City_Button;
    private Button District_Button;
    private View view;


    //选中的 省份，城市，地区
    private Province selectedProvince;
    private City selectedCity;
    private District selectedDistrict;
    private String weather_id;

    //选择的层次
    public static final int ProvinceLevel=0;
    public static final int CityLevel=1;
    public static final int DistrictLevel=2;
    private int NowLevel=0;


    //listView控件的选项，ListViewItem是数据源
    private List<String> ListViewItem=new ArrayList<>();
    private ArrayAdapter<String > adapter;

    //这个是进度条，表示在服务器查找时在主线程显示出来，消灭是 在主线程，顺便 把adapter更新
    private ProgressDialog progressDialog;


    //ICloseDrawerView
    private ICloseDrawerView iCloseDrawerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.chooselocation_layout,container,false);
        iCloseDrawerView=(ICloseDrawerView) getActivity();
        init();
        return view;
    }





    /**
     * 最简单的初始化
     */
    private void init(){
        listView=(ListView)view.findViewById(R.id.chooselocation_listView);
        Province_Button =(Button) view.findViewById(R.id.chooselocation_provinceButton);
        City_Button =(Button) view.findViewById(R.id.chooselocation_cityButton);
        District_Button =(Button) view.findViewById(R.id.chooselocation_districtButton);


        setButtonListener();

        Province_Button.setText(null);
        City_Button.setText(null);
        District_Button.setText(null);


        queryProvince();
        adapter=new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_1,ListViewItem);
        listView.setAdapter(adapter);


    }




    /**
     *onActivityCreated后于 onCreateView调用
     * 在这里设置listView的点击事件
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener(this);
    }




    /**
     * 为  listview设置文字
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(NowLevel){

            /**
             * provincelevel 进行三件事
             * 1、为selectedProvince赋值,listviewitem清空
             * 2、listviewitem通过省份id查询，nowlevel变为城市级别
             * 3、adapter更新
             */
            case ProvinceLevel:
                String province_name=ListViewItem.get(position);

                //Log.i("info", "onItemClick: "+province_name);

                selectedProvince=DaoOfProvince.getProvinceByProvinceName(province_name);

                int province_id=selectedProvince.getProvince_id();
                Province_Button.setText(province_name);


                ListViewItem.clear();
                queryCity(province_id);    //对listviewitem的操作
                adapter.notifyDataSetChanged();
                NowLevel=CityLevel;
                break;


            /**
             * provincelevel 进行三件事
             * 1、为selectedProvince赋值
             * 2、listviewitem通过省份id，城市id查询，nowlevel变为区县级别
             * 3、adapter更新
             */
            case CityLevel:
                String city_name=ListViewItem.get(position);
                selectedCity=DaoOfCity.getCityByCityName(city_name);

                int city_id=selectedCity.getCity_id();
                City_Button.setText(city_name);

                ListViewItem.clear();
                queryDistrict(selectedCity.getProvince_id(),city_id); //对listviewitem的操作
                adapter.notifyDataSetChanged();
                NowLevel=DistrictLevel;
                break;


            case DistrictLevel:
                String district_name=ListViewItem.get(position);
                selectedDistrict=DaoOfDistrict.getDistrictByDistrictName(district_name);

                weather_id=selectedDistrict.getWeather_id();
                District_Button.setText(district_name);

                ListViewItem.clear();
                adapter.notifyDataSetChanged();
                NowLevel=ProvinceLevel;

                iCloseDrawerView.closeDrawer(selectedDistrict);
                /**
                 * 这里以后还要进行  选择  操作
                 */

                break;

        }
    }




    /**
     * 设置三个按钮的点击事件
     *  分三件事
     * 1、按钮文字全部清空
     * 2、level变为相对应的，下级的select变为null
     * 3、listview更新
     */
    private void setButtonListener(){
        Province_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Province_Button.setText(null);
                City_Button.setText(null);
                District_Button.setText(null);

                NowLevel = ProvinceLevel;
                selectedProvince = null;
                selectedCity = null;
                selectedDistrict = null;

                queryProvince();
                adapter.notifyDataSetChanged();
            }
        });


        City_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedProvince==null)
                    return;
                City_Button.setText(null);
                District_Button.setText(null);

                NowLevel = CityLevel;
                selectedCity = null;
                selectedDistrict = null;

                queryCity(selectedProvince.getProvince_id());
                adapter.notifyDataSetChanged();
            }
        });


        District_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                return;
            }
        });

    }






    /**
     * 为 listview上的东西赋值，这里是 赋上省份的字符串
     * 直接 为 listviewitem上赋值，把之前的清空
     * @return
     */
    private void queryProvince(){
        ListViewItem.clear();
        List<Province> arr;
        arr= DaoOfProvince.getAllProvinces();

        if(arr!=null&&arr.size()!=0)
            for(Province province: arr)
                ListViewItem.add(province.getName());
        else
            getAllProvinceByServer();
    }





    /**
     * 为 listview上的东西赋值
     * 这里传入 省份的id，获取 城市的list，把之前的清空
     * 直接 为 listviewitem上赋值
     * @param  province_id
     * @return
     */
    private void queryCity(int province_id){
        ListViewItem.clear();
        List<City> arr=null;
        arr=DaoOfCity.getAllCityByProvinceid(province_id);
        if(arr!=null&&arr.size()!=0)
            for(City city: arr)
                ListViewItem.add(city.getName());
        else
            getAllCityByProvinceIdByServer(province_id);
    }



    /**
     * * 为 listview上的东西赋值
     * 这里传入 省份的id,城市的id，获取 区县的list，把之前的清空
     * @param province_id
     * @param city_id
     * @return
     */
    private void queryDistrict(int province_id,int city_id){
        ListViewItem.clear();
        List<District> arr=null;
        arr= DaoOfDistrict.getAllDistrictByCityId(city_id);

        if(arr!=null&&arr.size()!=0)
            for(District district:arr)
                ListViewItem.add(district.getName());
        else
            getAllDistrictByProvinceIdByCityIdByServer(province_id,city_id);

    }






    /**
     * 如果 数据库中没有省份，那么需要从网上获取
     * 而从网上获取，那么这里返回 List的同时，把每个省份都 放入 数据库中
     * @return
     */

    public void getAllProvinceByServer() {
        showProgressDialog();
        sendHttpRequest(HttpUtil.UrlOfPlaces, new HttpUtil.callBackListener() {
            @Override
            public void FinishToDo(String content) {

                String jsondata=content;
                try {
                    JSONArray array = new JSONArray(jsondata);
                    for(int i=0;i<array.length();i++) {
                        JSONObject object=array.getJSONObject(i);
                        int province_id=object.getInt("id");
                        String name=object.getString("name");

                        Province province=new Province(province_id,name);
                        DaoOfProvince.addProvince(province);

                        ListViewItem.add(province.getName());
                    }
                    dismissProgressDialog();
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void ErrorToDo() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "加载出错", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }






    /**
     * 如果 数据库中没有城市，那么需要从网上获取，参数就是 省份的id
     * 而从网上获取，那么这里返回 List的同时，把每个 城市 都放入数据库中
     * @param province_id
     * @return
     */
    public void getAllCityByProvinceIdByServer(final int province_id){
        String UrlForCity=HttpUtil.UrlOfPlaces+"/"+province_id;

        //Log.i("info", "FinishToDo: "+province_id);

        showProgressDialog();
        sendHttpRequest(UrlForCity, new HttpUtil.callBackListener() {
            @Override
            public void FinishToDo(String content) {
                try{
                    String jsondata=content;
                    //Log.i("info", "FinishToDo: "+jsondata);
                    JSONArray array=new JSONArray(jsondata);
                    for(int i=0;i<array.length();i++){

                        JSONObject object=array.getJSONObject(i);
                        int city_id=object.getInt("id");
                        String cityname=object.getString("name");

                        City city=new City(province_id,city_id,cityname);
                        DaoOfCity.addCity(city);

                        ListViewItem.add(city.getName());
                    }
                    dismissProgressDialog();
                }catch(Exception e){e.printStackTrace();}
            }

            @Override
            public void ErrorToDo() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "加载出错", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }



    /**
     * 如果 数据库中没有区县，那么需要从网上获取，参数就是 省份id和城市id
     * 而从网上获取，那么这里返回 List的同时，把每个 区县 都放入数据库中
     * @param province_id
     * @return
     */
    public void getAllDistrictByProvinceIdByCityIdByServer(final int province_id,final int city_id){
        String UrlForDistrict=HttpUtil.UrlOfPlaces+"/"+province_id+"/"+city_id;

        showProgressDialog();
        sendHttpRequest(UrlForDistrict, new HttpUtil.callBackListener() {
            @Override
            public void FinishToDo(String content) {
                String jsondata=content;

                try{
                    JSONArray array=new JSONArray(jsondata);
                    for(int i=0;i<array.length();i++){

                        JSONObject object=array.getJSONObject(i);
                        int districtid=object.getInt("id");
                        String districtname=object.getString("name");
                        String weatherid=object.getString("weather_id");



                        District district=new District(city_id,districtid,districtname,weatherid);
                        DaoOfDistrict.addDistrict(district);

                        ListViewItem.add(district.getName());
                    }
                    dismissProgressDialog();
                }catch(Exception e){e.printStackTrace();}
            }

            @Override
            public void ErrorToDo() {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getActivity(), "加载出错", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }



    /**
     * 展示对话框，显示在网络中加载
     *
     * 在网络搜索前用这个函数即可，因为都是主线程
     */
    private void showProgressDialog(){

        if(progressDialog==null){
            progressDialog=new ProgressDialog(getActivity());
            progressDialog.setMessage("正在从网上加载");
            progressDialog.setCanceledOnTouchOutside(false);  //不能回到外面
            progressDialog.show();
        }
    }


    /**
     * 取消对话框以及   adapter的更新，因为adapter的更新也需要在  uiThread中进行
     */
    private void dismissProgressDialog(){

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(progressDialog!=null)
                    progressDialog.dismiss();
                progressDialog=null;
                adapter.notifyDataSetChanged();
            }
        });


    }




}






































