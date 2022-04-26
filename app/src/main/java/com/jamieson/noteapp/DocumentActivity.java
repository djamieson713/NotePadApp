package com.jamieson.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import org.json.JSONException;

public class DocumentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);

        ImageButton setBtn = findViewById(R.id.imageButton);
        ImageButton shareBtn = findViewById(R.id.shareButton);
        EditText textBody = findViewById(R.id.etl);
        EditText textTitle = findViewById(R.id.editTextTitle);


        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tBody = textBody.getText().toString();
                String tTitle = textTitle.getText().toString();

                Model model = Model.getInstance(DocumentActivity.this.getApplication());
                try {
                    model.setDocument(tBody,tTitle);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShareActivity();
            }
        });

    }

    public void openShareActivity(){
        Intent intent = new Intent(this, ShareActivity.class );
        startActivity(intent);
    }
}