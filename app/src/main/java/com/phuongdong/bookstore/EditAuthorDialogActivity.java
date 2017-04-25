package com.phuongdong.bookstore;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.phuongdong.bookstore.database.DBManager;
import com.phuongdong.bookstore.model.Author;
import com.phuongdong.bookstore.util.Constant;

public class EditAuthorDialogActivity extends AppCompatActivity {

    private EditText editTextCodeAuthor, editTextNameAuthor;
    private Button buttonResetAuthor, buttonSavedAuthor;

    private Long _id;

    private DBManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_author_dialog);
        setTitle("Edit Author");
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

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constant.DEFAULT_NAME_BUNDLE);
        Author author = (Author) bundle.getSerializable(Constant.DEFAULT_NAME_AUTHOR);
        _id = Long.parseLong(author.getIdAuthor());
        editTextCodeAuthor.setText(author.getCodeAuthor());
        editTextNameAuthor.setText(author.getNameAuthor());
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
                //update author
                dbManager.updateAuthor(_id, editTextCodeAuthor.getText().toString(), editTextNameAuthor.getText().toString());
                Intent home_intent = new Intent(EditAuthorDialogActivity.this, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home_intent);
            }
        });

    }

    public void reset(){
        editTextCodeAuthor.setText("");
        editTextNameAuthor.setText("");
        editTextCodeAuthor.requestFocus();
    }
}
