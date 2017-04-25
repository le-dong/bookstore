package com.phuongdong.bookstore;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.phuongdong.bookstore.database.DBManager;
import com.phuongdong.bookstore.database.DatabaseHelper;
import com.phuongdong.bookstore.model.Author;
import com.phuongdong.bookstore.model.Book;
import com.phuongdong.bookstore.util.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EditBookDialogActivity extends AppCompatActivity {

    private Spinner spinnerAuthors;
    private EditText editTextCodeBook, editTextNameBook, ediTextDatePublished;
    private Button buttonResetBook, buttonSavedBook;

    private Long _id;

    private DBManager dbManager;
    private Cursor cursor = null;
    private HashMap<Integer, String> spinnerMap = new HashMap<Integer, String>();
    private ArrayList<String> spinnerArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_dialog);
        setTitle("Edit Book");
        init();
        event();
    }

    public void init(){
        spinnerAuthors = (Spinner) findViewById(R.id.spinnerAuthors);
        editTextCodeBook = (EditText) findViewById(R.id.editTextCodeBook);
        editTextNameBook = (EditText) findViewById(R.id.editTextNameBook);
        ediTextDatePublished = (EditText) findViewById(R.id.ediTextDatePublished);
        buttonResetBook = (Button) findViewById(R.id.buttonResetBook);
        buttonSavedBook = (Button) findViewById(R.id.buttonSavedBook);

        // connection database
        dbManager = new DBManager(this);
        dbManager.open();

        // get data to spinner
        getDataToSpinner();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(Constant.DEFAULT_NAME_BUNDLE);
        Book book = (Book) bundle.getSerializable(Constant.DEFAULT_NAME_BOOK);
        _id = Long.parseLong(book.getIdBook());
        editTextCodeBook.setText(book.getCodeBook());
        editTextNameBook.setText(book.getNameBook());
        ediTextDatePublished.setText(book.getDatePublished());
        setSelectionSpinner(book.getAuthorId());
    }

    public void event(){
        buttonResetBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });

        buttonSavedBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get authorId
                String authorId = getMapKeyFromValue(spinnerAuthors.getSelectedItem().toString()) + "";
                //update book
                dbManager.updateBook(_id, editTextCodeBook.getText().toString(), editTextNameBook.getText().toString(), ediTextDatePublished.getText().toString(), authorId);
                Intent home_intent = new Intent(EditBookDialogActivity.this, ListBooksActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(home_intent);
            }
        });

    }

    public void reset(){
        editTextCodeBook.setText("");
        editTextNameBook.setText("");
        ediTextDatePublished.setText("");
        editTextCodeBook.requestFocus();
    }

    public void getDataToSpinner(){
        cursor = dbManager.fetchAuthor();
        // for list cursor
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            int indexId = cursor.getColumnIndex(DatabaseHelper._ID_AUTHOR);
            int indexName = cursor.getColumnIndex(DatabaseHelper.NAME_AUTHOR);
            spinnerMap.put(cursor.getInt(indexId), cursor.getString(indexName));
            spinnerArray.add(cursor.getString(indexName));
        }

        // set value to spinner
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.notifyDataSetChanged();
        spinnerAuthors.setAdapter(adapter);
    }

    public void setSelectionSpinner(String p_authorId){
        String p_value = getMapValueFromKey(Integer.parseInt(p_authorId));
        int index = 0;
        for(int i = 0; i < spinnerArray.size(); i++){
            if(spinnerArray.get(i).equals(p_value)){
                 index = i;
            }
        }
        spinnerAuthors.setSelection(index);
    }

    public int getMapKeyFromValue(String p_value){
        int key = 0;
        for(Map.Entry<Integer, String> entry: spinnerMap.entrySet()){
            if(entry.getValue().equals(p_value.trim())){
                key =  entry.getKey();
            }
        }
        return key;
    }

    public String getMapValueFromKey(int p_key){
        String value = null;
        for(Map.Entry<Integer, String> entry: spinnerMap.entrySet()){
            if(entry.getKey().equals(p_key)){
                value =  entry.getValue();
            }
        }
        return value;
    }
}
