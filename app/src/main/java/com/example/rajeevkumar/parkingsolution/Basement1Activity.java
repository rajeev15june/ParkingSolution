package com.example.rajeevkumar.parkingsolution;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Basement1Activity extends AppCompatActivity implements RecyclerviewAdapter.ClickListener {
    Toolbar toolbar;
    String slot[] = {"Slot 1", "Slot 2", "Slot 3", "Slot 4", "Slot 5", "Slot 6", "Slot 7", "Slot 8"};
    TextView tv_slot, tv_date;
    DatabaseHelper databaseHelper;
    int basement = 1;
    int counter = 0;


    int position = -1;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basement1);

        databaseHelper = new DatabaseHelper(this);

        button = (Button) findViewById(R.id.button_book_1);

        tv_slot = (TextView) findViewById(R.id.text_slot);
        tv_date = (TextView) findViewById(R.id.txt_date);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Basement 1");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        initViews();

        final Intent intent = getIntent();
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

    @Override
    protected void onResume() {
        super.onResume();
        MyApplication.getInstance().trackScreenView("Basement1 Activity");
    }

    private void initViews() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_1);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 2);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<ModelSlot> arrayList = prepareData();
        RecyclerviewAdapter adapter = new RecyclerviewAdapter(arrayList, getApplicationContext());
        adapter.setListener(this);
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
    }

    @Override
    public void itemClicked(View v, int id) {
        position = id;
        counter++;


    }
}

