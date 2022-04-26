package com.jamieson.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewUserActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        EditText emailField = findViewById(R.id.emailText);
        EditText fName = findViewById(R.id.fNameText);
        EditText lName = findViewById(R.id.lNameText);
        Button userBtn = findViewById(R.id.loginBtn);

        userBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField.getText().toString();
                String first = fName.getText().toString();
                String last = lName.getText().toString();

                Model model = Model.getInstance(NewUserActivity.this.getApplication());
                model.createAccount(email,first,last);
                openRegisterActivity();
            }
        });

    }

    public void openRegisterActivity() {
        Intent intent = new Intent(this, RegisterActivity.class );
        startActivity(intent);
    }
}