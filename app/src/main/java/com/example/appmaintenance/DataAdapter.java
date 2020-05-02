package com.example.appmaintenance;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The type Data adapter.
 */
public class DataAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> tv1;
    private final ArrayList<String>tv2;
    private final ArrayList<Boolean>check1;
    private final ArrayList<Boolean>check2;

    /**
     * Instantiates a new Data adapter.
     *
     * @param context the context
     * @param tv1     the tv 1
     * @param tv2     the tv 2
     * @param check1  the check 1
     * @param check2  the check 2
     */
    public DataAdapter(Activity context, ArrayList<String> tv1, ArrayList<String>tv2,ArrayList<Boolean>check1,ArrayList<Boolean>check2) {
        super(context,R.layout.listview_com,tv1);

        this.context=context;
        this.tv1=tv1;
        this.tv2=tv2;
        this.check1=check1;
        this.check2=check2;
    }

    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.listview_com, null,true);

        TextView titleText = (TextView) rowView.findViewById(R.id.tv1);
        TextView subtitleText = (TextView) rowView.findViewById(R.id.tv2);
        ProgressBar pb = (ProgressBar) rowView.findViewById(R.id.progressBar);

        titleText.setText(tv1.get(position));
        subtitleText.setText(tv2.get(position));

        if (!check1.get(position)){
            pb.getProgressDrawable().setColorFilter(
                    Color.parseColor("#FF6347"), android.graphics.PorterDuff.Mode.SRC_IN);
        }else{
            pb.getProgressDrawable().setColorFilter(
                    Color.parseColor("#7fcd91"), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        if (check2.get(position)){
            pb.incrementProgressBy(50);
        }else {
            pb.incrementProgressBy(-50);
        }

        return rowView;

    };

}
