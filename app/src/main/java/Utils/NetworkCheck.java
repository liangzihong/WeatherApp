package Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.liangzihong.myweather.MyApplication;

/**
 * Created by Liang Zihong on 2018/3/23.
 */

public class NetworkCheck {
    /**
     * 检查网络是否 连接
     * @return
     */
    public static boolean isNetworkConnected() {
        Context context= MyApplication.context;
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

}
