package Adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.liangzihong.myweather.R;

import java.util.List;

/**
 * Created by Liang Zihong on 2018/3/23.
 */

public class forecast_adapter extends ArrayAdapter<forecast_model> {

    private int resource_id;

    public forecast_adapter(Context context,int resource, @NonNull List<forecast_model> objects) {
        super(context, resource, objects);
        resource_id=resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        forecast_model model=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resource_id,parent,false);

        TextView forecast_item_date=(TextView)view.findViewById(R.id.forecast_item_date);
        TextView forecast_item_weatherState=(TextView)view.findViewById(R.id.forecast_item_weatherState);
        TextView forecast_item_max=(TextView)view.findViewById(R.id.forecast_item_max);
        TextView forecast_item_min=(TextView)view.findViewById(R.id.forecast_item_min);

        forecast_item_date.setText(model.getDate());
        forecast_item_weatherState.setText(model.getState());
        forecast_item_max.setText(model.getMax());
        forecast_item_min.setText(model.getMin());

        return view;
    }


}


















