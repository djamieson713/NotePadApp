package com.jamieson.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class ResetEmailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_email);

        EditText email = findViewById(R.id.editEmail);
        EditText tempWord = findViewById(R.id.editTemp);
        EditText nWord = findViewById(R.id.editNewPWord);

        Button rBtn = findViewById(R.id.buttonReset);

        rBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email1 = email.getText().toString();
                String tempWord1 = tempWord.getText().toString();
                String nWord1 = nWord.getText().toString();

                Model model = Model.getInstance(ResetEmailActivity.this.getApplication());
                model.registerAccount(nWord1,tempWord1,email1);
            }
        });

    }
}