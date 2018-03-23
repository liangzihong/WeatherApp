package Presenters;

import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.gson.Gson;

import Activities.IUpdateDataView;
import Gsons.All;
import Utils.HttpUtil;

/**
 * Created by Liang Zihong on 2018/3/23.
 */

public class UpdateDataPresenter implements IUpdateData {

    private IUpdateDataView iView;

    public UpdateDataPresenter(IUpdateDataView view){
        iView=view;
    }


    @Override
    public void UpdateData(String weather_id) {
        //  http://guolin.tech/api/weather?cityid=CN101280800&key=7235710173ff472c9f9200508645b96b
        String headUrl="http://guolin.tech/api/weather?cityid=";
        String tailUrl="&key=7235710173ff472c9f9200508645b96b";
        String allUrl=headUrl+weather_id+tailUrl;

        HttpUtil.sendHttpRequest(allUrl, new HttpUtil.callBackListener() {
            @Override
            public void FinishToDo(String content) {
                Gson gson=new Gson();
                final All all=gson.fromJson(content,All.class);

                iView.getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        iView.changeView(all);
                    }
                });
            }

            @Override
            public void ErrorToDo() {
                Toast.makeText(iView.getActivity(), "updateDatePresenter出错了", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
