package com.example.rajeevkumar.parkingsolution;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {
    Button buttonB1, buttonB2;

    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    android.support.v7.widget.Toolbar toolbar;
    TextView textView, textPhone;
    boolean doubleBackToExitPressedOnce = false;
    DatabaseHelper databaseHelper;
    DatePickerDialog datePickerDialog;

    LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Parking Home");


        // getSupportActionBar().setIcon(R.drawable.iconb);

       /* databaseHelper=new DatabaseHelper(this);
        databaseHelper.deleteAll();*/

        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        textView = (TextView) findViewById(R.id.tv_date);
        textPhone = (TextView) findViewById(R.id.text_userphone);
        linearLayout = (LinearLayout) findViewById(R.id.date_ll);


        initNavigationDrawer();

        final String userPhone = gettingIntent();
        Log.i("TAG", "onCreate: Home " + userPhone);

        textPhone.setText(userPhone);


        datePickerDialog = new DatePickerDialog(this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                textView.setText(formatDate(year, monthOfYear, dayOfMonth));
            }
        }, mYear, mMonth, mDay);

        long now = System.currentTimeMillis();
        datePickerDialog.getDatePicker().setMinDate(now);
        datePickerDialog.getDatePicker().setMaxDate(now + (1000 * 24 * 60 * 60 * 6));
        //setMaxDate(new Date().getTime());

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }
        });

        buttonB1 = (Button) findViewById(R.id.button_basement1);
        buttonB2 = (Button) findViewById(R.id.button_basement2);

        databaseHelper = new DatabaseHelper(this);


        buttonB1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Please select date first", Toast.LENGTH_SHORT).show();
                    MyApplication.getInstance().trackEvent("B1 Button","Clicked","Date is not entered");

                } else {
                    MyApplication.getInstance().trackEvent("B1 Button","Clicked","B1 Activity");
                    Intent intent = new Intent(getApplicationContext(), Basement1Activity.class);
                    intent.putExtra("Date_Selected", textView.getText().toString());
                    intent.putExtra("Phone", userPhone);
                    startActivity(intent);
                }

            }
        });

        buttonB2 = (Button) findViewById(R.id.button_basement2);
        buttonB2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textView.getText().toString().equals("")) {
                    MyApplication.getInstance().trackEvent("B2 Button","Clicked","Date is not entered");

                    Toast.makeText(getApplicationContext(), "Please select date first", Toast.LENGTH_SHORT).show();


                } else {
                    MyApplication.getInstance().trackEvent("B2 Button","Clicked","B2 Activity");
                    Intent intent = new Intent(getApplicationContext(), Basement2Activity.class);
                    intent.putExtra("Date_Selected", textView.getText().toString());
                    intent.putExtra("Phone", userPhone);
                    startActivity(intent);
                }

            }
        });
    }


    private String formatDate(int year, int month, int day) {
       /* if (month < 0) {
            month = 0;
        }*/
        String m, d;
        if (month < 9) {
            m = "0" + (month + 1);
        } else {
            m = "" + (month + 1);
        }

        if (day < 10) {
            d = "0" + day;
        } else {
            d = "" + day;
        }
        return "" + d + "-" + m + "-" + year;

    }


   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        switch (item.getItemId()) {
            case R.id.bookingdetail:
                Intent in = new Intent(this, BookingInfo.class);
                in.putExtra("PHONE", textPhone.getText().toString());
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

        }


    }*/

    @Override
    protected void onResume() {
        super.onResume();
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                finish();
                super.onBackPressed();
            } else {
                this.doubleBackToExitPressedOnce = true;
                Toast.makeText(getApplicationContext(), "Press back once more to exit the application", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 3000);


               /* if (navigationView != null) {
                    navigationView.getMenu().getItem(navSelectedPos).setChecked(true);
                }*/
            }
        }

       /* if (doubleBackToExitPressedOnce) {
            finish();
            super.onBackPressed();
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(getApplicationContext(), "Press back once more to exit the application", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 3000);
*/
    }

    public void initNavigationDrawer() {

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.getMenu().getItem(0).setChecked(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();


                if (id == R.id.home) {
                    drawerLayout.closeDrawers();
                } else if (id == R.id.mybookings) {
                    drawerLayout.closeDrawers();
                    Intent in = new Intent(getBaseContext(), BookingInfo.class);
                    in.putExtra("PHONE", textPhone.getText().toString());
                    startActivity(in);

                } else if (id == R.id.logout) {
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                    SharedPreferences.Editor toEdit = prefs.edit();
                    toEdit.putBoolean(Constants.IS_LOGGED_IN, false);
                    toEdit.commit();

                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();

                }

               /* switch (id) {
                    case R.id.home:
                        drawerLayout.closeDrawers();

                        break;
                    case R.id.settings:
                        drawerLayout.closeDrawers();
                        Intent in = new Intent(getBaseContext(), BookingInfo.class);
                        in.putExtra("PHONE", textPhone.getText().toString());
                        startActivity(in);

                        return true;
                    case R.id.logout:
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor toEdit = prefs.edit();
                        toEdit.putBoolean(Constants.IS_LOGGED_IN, false);
                        toEdit.commit();

                        Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();

                }*/
                //drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
        //Intent intent = getIntent();
        //final String userPhone = intent.getStringExtra("Phone");
        final String userPhone = gettingIntent();
        View header = navigationView.getHeaderView(0);
        TextView tv_phone = (TextView) header.findViewById(R.id.tv_phone);
        tv_phone.setText(userPhone);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    public String gettingIntent() {
        Intent intent = getIntent();
        final String userPhone = intent.getStringExtra("Phone");
        return userPhone;

    }


}
