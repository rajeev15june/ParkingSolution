package com.example.rajeevkumar.parkingsolution;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;

public class SignUpActivity extends AppCompatActivity {
    Toolbar toolbar;
    Button signup;

    EditText name_et, phone_et, email_et, password_et, confirmPass_et;

    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.home_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Parking Solution");
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        databaseHelper = new DatabaseHelper(this);

        name_et = (EditText) findViewById(R.id.signup_username_et);
        phone_et = (EditText) findViewById(R.id.signup_phone_et);
        email_et = (EditText) findViewById(R.id.signup_email_et);
        password_et = (EditText) findViewById(R.id.signup_password_et);
        confirmPass_et = (EditText) findViewById(R.id.signup_conf_pass_et);

        signup = (Button) findViewById(R.id.button_creat_account);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = name_et.getText().toString();
                String phone = phone_et.getText().toString();
                String email = email_et.getText().toString();
                String password = password_et.getText().toString();
                String confirmPass = confirmPass_et.getText().toString();

                if (name.equals("") || phone.equals("") || email.equals("") || password.equals("") || confirmPass.equals("")) {

                    Toast.makeText(getApplicationContext(), "Field Vacant", Toast.LENGTH_LONG).show();
                    return;

                }

                if (phone.length() < 10) {

                    Toast.makeText(getApplicationContext(), "Enter a valid Phone Number", Toast.LENGTH_SHORT).show();
                    return;

                }

                if (!password.equals(confirmPass)) {

                    Toast.makeText(getApplicationContext(), "Password does not match", Toast.LENGTH_SHORT).show();
                    return;

                } else {


                    long id = databaseHelper.insertLoginData(name, phone, email, password);
                    if (id < 0) {
                        Toast.makeText(getApplicationContext(), "Account not created", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Account successfully created", Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(v.getContext(), LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }

                }

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return true;
    }
}
