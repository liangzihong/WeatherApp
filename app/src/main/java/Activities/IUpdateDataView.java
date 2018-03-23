package Activities;

import android.app.Activity;

import Gsons.All;

/**
 * Created by Liang Zihong on 2018/3/23.
 */

public interface IUpdateDataView {

    public void startUpdate();

    public void changeView(All allModel);

    public Activity getActivity();
}
