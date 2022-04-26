package com.jamieson.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText password = findViewById(R.id.passwordText);
        EditText tPassword = findViewById(R.id.temp_passowrdText);
        EditText email = findViewById(R.id.emailText);
        Button loginBtn = findViewById(R.id.loginBtn);



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pWord = password.getText().toString();
                String tWord = tPassword.getText().toString();
                String em = email.getText().toString();

                Model model = Model.getInstance(RegisterActivity.this.getApplication());
                model.registerAccount(pWord, tWord, em);

            }
        });


    }
}