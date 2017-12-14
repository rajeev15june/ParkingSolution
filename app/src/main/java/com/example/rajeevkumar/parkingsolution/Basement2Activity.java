package com.example.rajeevkumar.parkingsolution;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Basement2Activity extends AppCompatActivity implements RecyclerviewAdapter2.ClickListener {

    Toolbar toolbar;
    DatabaseHelper databaseHelper;
    RecyclerviewAdapter2 adapter;
    int counter = 0;
    TextView tv_date;
    AlertDialog.Builder delete_alert;

    int position = -1;
    Button button;

    String slot[] = {"Slot 1", "Slot 2", "Slot 3", "Slot 4", "Slot 5", "Slot 6", "Slot 7", "Slot 8"};

    int basement = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basement2);

        button = (Button) findViewById(R.id.button_book_2);

        databaseHelper = new DatabaseHelper(this);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Basement 2");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tv_date = (TextView) findViewById(R.id.txt_date);

        initViews();

        Intent intent = getIntent();
        final String date = intent.getStringExtra("Date_Selected");
        final String userPhone = intent.getStringExtra("Phone");

        tv_date.setText(date);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (counter == 0) {
                    Toast.makeText(v.getContext(), "Please select one slot first", Toast.LENGTH_SHORT).show();
                } else {
                    Log.d("TAG", "Date: " + date);

                    Intent intent1 = new Intent(v.getContext(), ConfirmationActivity.class);
                    intent1.putExtra("Date", date);
                    intent1.putExtra("Basement", basement);
                    intent1.putExtra("Slot", position);
                    intent1.putExtra("Phone", userPhone);
                    startActivity(intent1);

                }

            }
        });


    }

    private void initViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_2);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<ModelSlot> arrayList = prepareData();
        RecyclerviewAdapter2 adapter = new RecyclerviewAdapter2(arrayList, getApplicationContext());
        adapter.setListener(this);
        // adapter.setLongListener(this);
        recyclerView.setAdapter(adapter);

    }

    private ArrayList<ModelSlot> prepareData() {

        final Intent intent = getIntent();
        final String date = intent.getStringExtra("Date_Selected");

        ArrayList<ModelSlot> slots = new ArrayList<>();
        for (int i = 0; i < slot.length; i++) {
            ModelSlot model = new ModelSlot();
            model.setSlot_number(slot[i]);
            model.setDatetv(date);
            slots.add(model);
        }
        return slots;
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
       /* switch (item.getItemId()) {
            case R.id.bookingdetail:
                Intent in=new Intent(this,BookingInfo.class);
                startActivity(in);
                return true;

            case R.id.logout:
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor toEdit = prefs.edit();
                toEdit.putBoolean(Constants.IS_LOGGED_IN, false);
                toEdit.commit();

                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }*/
    }

    @Override
    public void itemClicked(View v, int id) {
        position = id;
        counter++;
        /*if(position==id){
            v.setBackgroundColor(Color.GREEN);
        }else {
            v.setBackgroundColor(Color.GRAY);
        }*/
    }

    /*@Override
    public void itemlongClicked(View ve, int ide) {
        final int position = ide;
        Intent intent = getIntent();
        final String date = intent.getStringExtra("Date_Selected");
        Log.d("TAG", "onLongClick: ");
        delete_alert = new AlertDialog.Builder(Basement2Activity.this);
        delete_alert.setTitle("Cancelation ");
        delete_alert.setMessage("Are you sure, you want to cancel your booking ?");
        delete_alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

               // databaseHelper.deleteRow(date,basement,position);
                initViews();
                Log.d("TAG", "onClick: deleted  "+date+" "+position);
                // adapter.notifyDataSetChanged();

            }
        });
        delete_alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });
        AlertDialog alertDialog= delete_alert.create();
        alertDialog.show();
    }*/
}
