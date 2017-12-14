package com.example.rajeevkumar.parkingsolution;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Rajeev Kumar on 23-09-2016.
 */
public class DisplayDetailAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<String> date;
    private ArrayList<Integer> basement;
    private ArrayList<Integer> slots;


    public DisplayDetailAdapter(Context mContext, ArrayList<String> date, ArrayList<Integer> basement, ArrayList<Integer> slots) {
        this.mContext = mContext;
        this.date = date;
        this.basement = basement;
        this.slots = slots;
    }

    public int getCount() {
        return slots.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int pos, View child, ViewGroup parent) {
        Holder mHolder;
        LayoutInflater layoutInflater;
        if (child == null) {
            layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.booking_info_row, null);
            mHolder = new Holder();
            mHolder.txt_date = (TextView) child.findViewById(R.id.txt_date);
            mHolder.txt_basement = (TextView) child.findViewById(R.id.txt_basement);
            mHolder.txt_slot = (TextView) child.findViewById(R.id.txt_slot);
            child.setTag(mHolder);
        } else {
            mHolder = (Holder) child.getTag();
        }
        mHolder.txt_date.setText(date.get(pos));
        mHolder.txt_basement.setText(basement.get(pos).toString());
        mHolder.txt_slot.setText(slots.get(pos).toString());

        return child;
    }

    public class Holder {
        TextView txt_date;
        TextView txt_basement;
        TextView txt_slot;
    }

}

