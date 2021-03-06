package com.example.rajeevkumar.parkingsolution;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Rajeev Kumar on 15-09-2016.
 */
public class RecyclerviewAdapter2 extends RecyclerView.Adapter<RecyclerviewAdapter2.ViewHolder> {

    ArrayList<ModelSlot> slots;
    Context context;
    ClickListener clickListener;
    // LongClickListener longClickListener;
    int basement = 2;
    String TAG = "TAG";
    int pos = -1;
    DatabaseHelper databaseHelper;

    public RecyclerviewAdapter2(ArrayList<ModelSlot> slots, Context context) {
        this.slots = slots;
        this.context = context;
    }

    @Override
    public RecyclerviewAdapter2.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        Log.d(TAG, "onCreateViewHolder ");
        return new ViewHolder(view);
    }

    public void setListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

/*
    public void setLongListener(LongClickListener longClickListener){
        this.longClickListener = longClickListener;
    }
*/

    @Override
    public void onBindViewHolder(final RecyclerviewAdapter2.ViewHolder holder, final int position) {
        holder.tv_slot.setText(slots.get(position).getSlot_number());
        holder.date_tv.setText(slots.get(position).getDatetv());
        databaseHelper = new DatabaseHelper(context);

        String date = holder.date_tv.getText().toString();

        final ArrayList<Integer> slotsBooked = databaseHelper.getBookedSlotNumber(date, basement);
        for (final int obj : slotsBooked) {
            if (obj == position) {
                holder.tv_slot.setBackgroundResource(R.color.notavailable);
                holder.tv_slot.setClickable(false);
               /* holder.tv_slot.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        Log.d(TAG, "onLongClick: ");
                        if(longClickListener != null){
                            longClickListener.itemlongClicked(v,obj);
                            Log.d(TAG, "onLongClick: longpressed");
                        }
                        return true;
                    }
                });*/
                return;
            }
        }
        if (pos == position) {
            holder.tv_slot.setBackgroundResource(R.color.booking);
        } else {
            holder.tv_slot.setBackgroundResource(R.color.available);
        }
        holder.tv_slot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = position;
                if (clickListener != null) {
                    clickListener.itemClicked(v, position);
                }
                Log.d(TAG, "onClick:" + position + " " + holder.getAdapterPosition());


                notifyDataSetChanged();
            }

        });

        Log.d(TAG, "onBindViewHolder: " + position);

    }


    @Override
    public int getItemCount() {
        return slots.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_slot;
        TextView date_tv;


        public ViewHolder(View view) {
            super(view);
            tv_slot = (TextView) view.findViewById(R.id.text_slot);
            date_tv = (TextView) view.findViewById(R.id.date_hidden);


        }

    }

    public interface ClickListener {
        public void itemClicked(View v, int id);

    }

   /* public interface LongClickListener{
        public void itemlongClicked(View ve,int ide);
    }*/

}

