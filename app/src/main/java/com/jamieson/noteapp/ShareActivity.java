package com.jamieson.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;

import java.util.ArrayList;

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        Button shareBtn = findViewById(R.id.buttonShare1);
        EditText emailField = findViewById(R.id.editTextEmailAddress);
        EditText docIdField = findViewById(R.id.editTextPersonName);


        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<String> emails = new ArrayList<>();
                String email = emailField.getText().toString();
                emails.add(email);
                String[] emailArray = emails.toArray(new String[emails.size()]);
                String docId = docIdField.getText().toString();

                Model model = Model.getInstance(ShareActivity.this.getApplication());
                try {
                    model.setDocumentAccessors(emailArray, docId);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

    }
}