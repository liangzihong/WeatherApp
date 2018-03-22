package Daos;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import Models.District;

/**
 * Created by Liang Zihong on 2018/3/21.
 */

public class DaoOfDistrict {

    //增

    /**
     * 往数据库添加  district的数据
     * @param district
     */
    public static void addDistrict(District district){
        if(district!=null)
            district.save();
    }


    //查

    /**
     * 根据 城市 id的参数，返回 District的List
     * @param city_id
     * @return
     */
    public static List<District> getAllDistrictByCityId(int city_id){
        List<District> arr=null;
        arr=DataSupport.where("city_id=?",city_id+"")
                .find(District.class);
        return arr;
    }


    /**
     * 根据 区县名字，返回区县
     * @param district_name
     * @return
     */
    public static District getDistrictByDistrictName(String district_name){
        return DataSupport.where("name=?",district_name).findFirst(District.class);
    }
}














