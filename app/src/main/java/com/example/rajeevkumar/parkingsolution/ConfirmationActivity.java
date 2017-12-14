package com.example.rajeevkumar.parkingsolution;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ConfirmationActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button button;
    TextView tv_date, tv_basement, tv_slot;
    DatabaseHelper databaseHelper;
    BookingModel bookingModel;
    ArrayList<BookingModel> model;
    AlertDialog.Builder delete_alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmation);

        databaseHelper = new DatabaseHelper(this);
        model = new ArrayList<BookingModel>();

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Confirmation");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tv_date = (TextView) findViewById(R.id.tv_date_selected);
        tv_basement = (TextView) findViewById(R.id.tv_basement_selected);
        tv_slot = (TextView) findViewById(R.id.tv_slot_selected);


        Intent intent = getIntent();
        final String date = intent.getStringExtra("Date");
        final int basement = intent.getIntExtra("Basement", 0);
        final int slot = intent.getIntExtra("Slot", -1);
        final String userPhone = intent.getStringExtra("Phone");

        tv_date.setText(date);
        tv_basement.setText(String.valueOf(basement));
        tv_slot.setText(String.valueOf(slot + 1));
        for (int i = 0; i < model.size(); i++) {
            bookingModel.setDate(date);
            bookingModel.setBasement(basement);
            bookingModel.setSlot(slot);
            model.add(bookingModel);
        }


        button = (Button) findViewById(R.id.button_done);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final int slot1 = slot + 1;
                delete_alert = new AlertDialog.Builder(ConfirmationActivity.this);
                delete_alert.setTitle("Confirmation : ");
                delete_alert.setMessage("Booking for " + "\n" + date + " Basement " + basement + " Slot " + slot1);
                delete_alert.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseHelper.insertBookingData(userPhone, date, basement, slot);
                        String datas = databaseHelper.getAllData();
                        Log.d("TAG", "All Data: " + datas + "    " + userPhone);

                        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
                        inboxStyle.setBigContentTitle("Booking Confirmation");
                        inboxStyle.addLine("Booking for Date: " + date);
                        inboxStyle.addLine("Basement: " + basement);
                        inboxStyle.addLine("Slot: " + slot1 + " is confirmed.");


                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getBaseContext());
                        builder.setAutoCancel(true);  //cancel itself when notification is deleted of the system
                        builder.setContentTitle("Booking Confirmation"); //this title and text below will bw visible when the the notification have small layout as in lock screen
                        builder.setSmallIcon(R.drawable.icon);
                        builder.setStyle(inboxStyle);

                        Notification notification = builder.build();
                        NotificationManager manager = (NotificationManager) getBaseContext().getSystemService(NOTIFICATION_SERVICE);
                        manager.notify(81, notification);

                        Toast.makeText(getBaseContext(), "Booking Successful", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                        intent.putExtra("Phone", userPhone);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                });
                delete_alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();

                    }
                });
                AlertDialog alertDialog = delete_alert.create();
                alertDialog.show();


            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return true;


    }
}
