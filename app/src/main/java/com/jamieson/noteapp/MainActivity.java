package com.jamieson.noteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText emailField = findViewById(R.id.emailText);
        EditText passwordField = findViewById(R.id.passwordText);
        Button loginBtn = findViewById(R.id.loginBtn);
        Button docBtn = findViewById(R.id.buttonDocument);
        //Button delBtn = findViewById(R.id.deleteButton);
        Button createBtn = findViewById(R.id.newUserBtn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailField.getText().toString();
                String password = passwordField.getText().toString();

                Model model = Model.getInstance(MainActivity.this.getApplication());
                model.login(email,password,MainActivity.this);

            }
        });

        docBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Model model = Model.getInstance(MainActivity.this.getApplication());
                //model.login();
               // model.getDocument("71c966f0-6a59-4174-9a26-bf4bb7ad8a95");
                openEmailActivity();
            }
        });


        /*delBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Model model = Model.getInstance(MainActivity.this.getApplication());
                //model.login();
                model.delDocument("71c966f0-6a59-4174-9a26-bf4bb7ad8a95");
            }
        });
*/
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewUserActivity();
            }
        });


    }

    public void openNewUserActivity() {
        Intent intent = new Intent(this, NewUserActivity.class );
        startActivity(intent);

    }

    public void openDocumentActivity(){
        Intent intent = new Intent(this, DocumentActivity.class );
        startActivity(intent);
    }

    public void openEmailActivity() {
        Intent  intent = new Intent(this, EmailActivity.class);
        startActivity(intent);
    }



}