package Activities;

import android.app.Activity;
import android.widget.ImageView;

/**
 * Created by Liang Zihong on 2018/3/22.
 */

public interface ILoadPicView {

    public void loadPicToView();
    public ImageView getImageView();
    public Activity getActivity();
}
