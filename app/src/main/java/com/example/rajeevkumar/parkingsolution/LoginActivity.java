package com.example.rajeevkumar.parkingsolution;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    EditText phone_et, password_et;
    Button login, signup;

    DatabaseHelper databaseHelper;
    public static SharedPreferences prefs;
    public static SharedPreferences.Editor toEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        phone_et = (EditText) findViewById(R.id.field_username);
        password_et = (EditText) findViewById(R.id.field_password);

        login = (Button) findViewById(R.id.btn_login);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isLoggedIn = prefs.getBoolean(Constants.IS_LOGGED_IN, false);
        String UserPhone = prefs.getString(Constants.RESULT_OK, null);

        if (isLoggedIn) {
            Intent in = new Intent(this, HomeActivity.class);
            in.putExtra("Phone", UserPhone);
            startActivity(in);
            finish();

        } else {

            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String phone = phone_et.getText().toString();
                    String password = password_et.getText().toString();


                    String storedPassword = databaseHelper.getSingleRow(phone);

                    if (phone.equals("") || password.equals("")) {

                        Toast.makeText(getApplicationContext(), "Field Vacant", Toast.LENGTH_SHORT).show();
                        return;

                    }

                    if (phone.length() < 10) {
                        Toast.makeText(getApplicationContext(), "Wrong phone number", Toast.LENGTH_SHORT).show();
                        return;

                    }

                    if (password.equals(storedPassword)) {

                        SharedPreferences sp = getSharedPreferences("your_prefs", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("mykey", phone);
                        editor.commit();
                        String userId = sp.getString("mykey", null);
                        prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
                        toEdit = prefs.edit();
                        toEdit.putBoolean(Constants.IS_LOGGED_IN, true);
                        toEdit.putString(Constants.RESULT_OK, userId);
                        toEdit.commit();


                        Intent intent = new Intent(v.getContext(), HomeActivity.class);
                        intent.putExtra("Phone", userId);
                        startActivity(intent);

                        Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                        finish();
                        return;

                    } else {
                        Toast.makeText(getApplicationContext(), "User Name or Password does not match", Toast.LENGTH_LONG).show();
                    }


                }
            });
        }


        signup = (Button) findViewById(R.id.btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}
