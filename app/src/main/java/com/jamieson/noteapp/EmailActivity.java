package com.jamieson.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email);

        EditText emailField = findViewById(R.id.emailAddress);
        Button button = findViewById(R.id.buttonReset);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField.getText().toString();
                Model model = Model.getInstance(EmailActivity.this.getApplication());
                model.forgetPassword(email);
                openResetEmail();


            }
        });

    }

    public void openResetEmail() {
        Intent intent = new Intent(this, ResetEmailActivity.class );
        startActivity(intent);
    }
}