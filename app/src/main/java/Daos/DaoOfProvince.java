package Daos;

import org.litepal.crud.DataSupport;

import java.util.List;

import Models.Province;

/**
 * Created by Liang Zihong on 2018/3/21.
 */

public class DaoOfProvince {

    //增

    /**
     * 添加 province到数据库中
     * @param province
     */
    public static void addProvince(Province province) {
        if(province!=null)
            province.save();
    }

    //查

    /**
     * 返回所有省份
     * @return
     */
    public static List<Province> getAllProvinces(){
        List<Province> arr=DataSupport.findAll(Province.class);
        if(arr!=null)
            return arr;
        else return null;
    }

    /**
     * 根据 省份的名字，获取省份
     * @param province_name
     * @return
     */
    public static Province getProvinceByProvinceName(String province_name){
        Province province=DataSupport.where("name=?",province_name)
                .findFirst(Province.class);
        return province;
    }


}













