package Daos;

import org.litepal.crud.DataSupport;

import java.util.List;

import Models.City;

/**
 * Created by Liang Zihong on 2018/3/21.
 */

public class DaoOfCity {

    //增

    /**
     * 往数据库添加  city的数据
     * @param city
     */
    public static void addCity(City city){
        if(city!=null)
            city.save();
    }


    //查

    /**
     * 根据 省份 id的参数，返回 city的 List
     * @param province_id
     * @return
     */
    public static List<City> getAllCityByProvinceid(int province_id){
        List<City> arr=DataSupport.where("province_id=?",province_id+"")
                .find(City.class);
        if(arr!=null)
            return arr;
        else
            return null;
    }


    /**
     * 根据城市的名字，返回城市
     * @param city_name
     * @return
     */
    public static City getCityByCityName(String city_name){
        return DataSupport.where("name=?",city_name).findFirst(City.class);
    }

}
























