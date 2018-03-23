package Presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.LoginFilter;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.liangzihong.myweather.ILoadPicView;
import com.example.liangzihong.myweather.MyApplication;
import com.example.liangzihong.myweather.R;

import Utils.HttpUtil;
import Utils.NetworkCheck;

/**
 * Created by Liang Zihong on 2018/3/22.
 */

public class LoadPicPresenter implements ILoadPic {

    private ILoadPicView iView;
    private final String UrlOfAllPicture="http://guolin.tech/api/bing_pic";


    public LoadPicPresenter(ILoadPicView picView) {
        iView = picView;
    }


    /**
     * 先判断有无网络，如果没网络就加载自己的图片
     * 如果有网络，就通过 HTTP获取 网上的图片
     */
    @Override
    public void LoadPicture() {

        //如果没有网络，就用自己家的图片
        if(NetworkCheck.isNetworkConnected(MyApplication.context)==false) {
            Glide.with(MyApplication.context)
                    .load(R.drawable.defaultbackground)
                    .into(iView.getImageView());
        }


        else
        //因为 UrlOfAllPicture这个URl返回的是一个图片的网址，而不是图片，所以不能直接就用Glide
        //而是要做一次的sendHttpRequest
        //第一次得到网址，然后可以  用Glide加载
        //如果没网络，就用默认的图片
            HttpUtil.sendHttpRequest(UrlOfAllPicture, new HttpUtil.callBackListener() {
                @Override
                public void FinishToDo(final String content) {
                    iView.getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(MyApplication.context)
                                    .load(content)
                                    .error(R.drawable.defaultbackground)
                                    .into(iView.getImageView());
                        }
                    });
                }

                @Override
                public void ErrorToDo() {

                }
            });

    }





}
