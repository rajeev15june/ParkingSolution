package com.example.rajeevkumar.parkingsolution;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BookingInfo extends AppCompatActivity {
    Toolbar toolbar;
    DatabaseHelper databaseHelper;
    AlertDialog.Builder delete_alert;
    TextView tv;

    private ArrayList<String> dateArray = new ArrayList<String>();
    private ArrayList<Integer> basementArray = new ArrayList<Integer>();
    private ArrayList<Integer> slotArray = new ArrayList<Integer>();

    private ListView bookingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_info);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Bookings");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        bookingList = (ListView) findViewById(R.id.list_view);

        Intent intent = getIntent();
        final String phoneNumber = intent.getStringExtra("PHONE");

        tv = (TextView) findViewById(R.id.tv);
        tv.setText(phoneNumber);
        bookingList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final String phone = tv.getText().toString();
                final String s = dateArray.get(position).toString();
                final int i = basementArray.get(position);
                final int sl = slotArray.get(position);
                Log.d("TAG", "onItemClick: " + s + " " + i + " " + sl);
                delete_alert = new AlertDialog.Builder(BookingInfo.this);
                delete_alert.setTitle("Cancellation : ");
                delete_alert.setMessage("Are you sure, you want to cancel your booking ?" + "\n" + "( " + s + " Basement " + i + " Slot " + sl + " )");
                delete_alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseHelper.deleteRow(phone, s, i, sl - 1);
                        Toast.makeText(getBaseContext(), "Booking for " + s + " Basement " + i + " Slot " + sl + " has been cancled successfully", Toast.LENGTH_LONG).show();
                        displayData();

                        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                        inboxStyle.setBigContentTitle("Booking Cancellation");
                        inboxStyle.addLine("Booking for Date: " + s);
                        inboxStyle.addLine("Basement: " + i);
                        inboxStyle.addLine("Slot: " + sl + " has been canceled successfully.");


                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
                        builder.setAutoCancel(true);  //cancel itself when notification is deleted of the system
                        builder.setContentTitle("Booking Cancellation"); //this title and text below will bw visible when the the notification have small layout as in lock screen
                        builder.setSmallIcon(R.drawable.icon);
                        builder.setStyle(inboxStyle);

                        Notification notification = builder.build();
                        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(NOTIFICATION_SERVICE);
                        manager.notify(81, notification);

                    }
                });
                delete_alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                AlertDialog alertDialog = delete_alert.create();
                alertDialog.show();
                return true;
            }
        });

        bookingList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
               /* final String phone = tv.getText().toString();
                final String s = dateArray.get(position).toString();
                final int i = basementArray.get(position);
                final int sl = slotArray.get(position);
                Log.d("TAG", "onItemClick: " + s + " " + i + " " + sl);
                delete_alert = new AlertDialog.Builder(BookingInfo.this);
                delete_alert.setTitle("Cancellation : ");
                delete_alert.setMessage("Are you sure, you want to cancel your booking ?" + "\n" + "( " + s + " Basement " + i + " Slot " + sl + " )");
                delete_alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        databaseHelper.deleteRow(phone, s, i, sl - 1);
                        Toast.makeText(getBaseContext(), "Booking for " + s + " Basement " + i + " Slot " + sl + " has been cancled successfully", Toast.LENGTH_LONG).show();
                        displayData();

                        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                        inboxStyle.setBigContentTitle("Booking Cancellation");
                        inboxStyle.addLine("Booking for Date: " + s );
                        inboxStyle.addLine("Basement: " + i);
                        inboxStyle.addLine("Slot: " + sl + " is canceled successfully.");


                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
                        builder.setAutoCancel(true);  //cancel itself when notification is deleted of the system
                        builder.setContentTitle("Booking Cancellation"); //this title and text below will bw visible when the the notification have small layout as in lock screen
                        builder.setSmallIcon(R.drawable.icon);
                        builder.setStyle(inboxStyle);

                        Notification notification = builder.build();
                        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(NOTIFICATION_SERVICE);
                        manager.notify(81, notification);

                    }
                });
                delete_alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                AlertDialog alertDialog = delete_alert.create();
                alertDialog.show();*/
            }
        });
    }

    @Override
    protected void onResume() {
        displayData();
        super.onResume();
    }

    private void displayData() {
        databaseHelper = new DatabaseHelper(this);
        SQLiteDatabase dataBase = databaseHelper.databaseAdapter.getWritableDatabase();
        String columns[] = {DatabaseHelper.DatabaseAdapter.KEY_DATE, DatabaseHelper.DatabaseAdapter.KEY_BASEMENT_ID, DatabaseHelper.DatabaseAdapter.KEY_SLOT_NUMBER};
        /*Cursor mCursor = dataBase.rawQuery("SELECT * FROM "
                + DatabaseHelper.DatabaseAdapter.TABLE_SLOT_BOOKING, null);*/
        Cursor mCursor = dataBase.query(DatabaseHelper.DatabaseAdapter.TABLE_SLOT_BOOKING, columns, DatabaseHelper.DatabaseAdapter.KEY_USERPHONE + "='" + tv.getText().toString() + "'", null, null, null, null);

        dateArray.clear();
        basementArray.clear();
        slotArray.clear();
        if (mCursor.moveToFirst()) {
            do {
                dateArray.add(mCursor.getString(mCursor.getColumnIndex(DatabaseHelper.DatabaseAdapter.KEY_DATE)));
                basementArray.add(mCursor.getInt(mCursor.getColumnIndex(DatabaseHelper.DatabaseAdapter.KEY_BASEMENT_ID)));
                slotArray.add(mCursor.getInt(mCursor.getColumnIndex(DatabaseHelper.DatabaseAdapter.KEY_SLOT_NUMBER)) + 1);

            } while (mCursor.moveToNext());
        }
        DisplayDetailAdapter disadpt = new DisplayDetailAdapter(BookingInfo.this, dateArray, basementArray, slotArray);
        bookingList.setAdapter(disadpt);
        mCursor.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return true;
    }
}
