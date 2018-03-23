package Activities;

import android.app.Activity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.liangzihong.myweather.R;

import Gsons.GsonTest;
import Presenters.LoadPicPresenter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,ILoadPicView {



    //控件
    private Button home_button;
    private DrawerLayout drawer;
    private ImageView backGroundImageView;

    //Presenter
    private LoadPicPresenter loadPicPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.weather_main_layout);
        init();

        //Gson 数据测试
        GsonTest.testGson();

        loadPicToView();
    }


    private void init(){
        home_button=(Button)findViewById(R.id.title_homeButton);
        backGroundImageView=(ImageView)findViewById(R.id.weather_main_imageView);
        drawer=(DrawerLayout)findViewById(R.id.drawlayout_drawer);
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


}
