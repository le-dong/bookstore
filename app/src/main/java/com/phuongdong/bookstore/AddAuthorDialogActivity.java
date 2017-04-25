package com.phuongdong.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.phuongdong.bookstore.database.DBManager;

public class AddAuthorDialogActivity extends AppCompatActivity {

    private EditText editTextCodeAuthor, editTextNameAuthor;
    private Button buttonResetAuthor, buttonSavedAuthor;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author_dialog);
        setTitle("Add Author");
        init();
        event();
    }

    public void init(){
        editTextCodeAuthor = (EditText) findViewById(R.id.editTextCodeAuthor);
        editTextNameAuthor = (EditText) findViewById(R.id.editTextNameAuthor);
        buttonResetAuthor = (Button) findViewById(R.id.buttonResetAuthor);
        buttonSavedAuthor = (Button) findViewById(R.id.buttonSavedAuthor);

        // connection database
        dbManager = new DBManager(this);
        dbManager.open();
    }

    public void event(){
        buttonResetAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        buttonSavedAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert record
                dbManager.insertAuthor(editTextCodeAuthor.getText().toString(), editTextNameAuthor.getText().toString());

                Intent intent = new Intent(AddAuthorDialogActivity.this, MainActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    public void reset(){
        editTextCodeAuthor.setText("");
        editTextNameAuthor.setText("");
        editTextCodeAuthor.requestFocus();
    }
}
