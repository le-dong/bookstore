package com.phuongdong.bookstore;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.phuongdong.bookstore.database.DBManager;
import com.phuongdong.bookstore.database.DatabaseHelper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddBookDialogActivity extends AppCompatActivity implements TextWatcher{

    private Spinner spinnerAuthors;
    private EditText editTextCodeBook, editTextNameBook;
    static EditText ediTextDatePublished;
    private Button buttonResetBook, buttonSavedBook, chooseDatePublished;

    private DBManager dbManager;
    private Cursor cursor = null;
    HashMap<Integer,String> spinnerMap = new HashMap<Integer, String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book_dialog);
        setTitle("Add Book");
        init();
        getDataToSpinner();
        event();
    }

    public void init(){
        spinnerAuthors = (Spinner) findViewById(R.id.spinnerAuthors);
        editTextCodeBook = (EditText) findViewById(R.id.editTextCodeBook);
        editTextNameBook = (EditText) findViewById(R.id.editTextNameBook);
        ediTextDatePublished = (EditText) findViewById(R.id.ediTextDatePublished);
        buttonResetBook = (Button) findViewById(R.id.buttonResetBook);
        buttonSavedBook = (Button) findViewById(R.id.buttonSavedBook);
        chooseDatePublished = (Button) findViewById(R.id.chooseDatePublished);

        dbManager = new DBManager(this);
        dbManager.open();
    }

    public void getDataToSpinner(){
        cursor = dbManager.fetchAuthor();
        ArrayList<String> spinnerArray = new ArrayList<String>();
        // for list cursor
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
        {
            int indexId = cursor.getColumnIndex(DatabaseHelper._ID_AUTHOR);
            int indexName = cursor.getColumnIndex(DatabaseHelper.NAME_AUTHOR);
            spinnerMap.put(cursor.getInt(indexId), cursor.getString(indexName));
            spinnerArray.add(cursor.getString(indexName));
        }

        // set value to spinner
        ArrayAdapter<String> adapter =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAuthors.setAdapter(adapter);
    }

    public void event(){
        chooseDatePublished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getFragmentManager(), "datePicker");
            }
        });
        buttonResetBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset();
            }
        });
        buttonSavedBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get value spinner
                String authorId = getMapKeyFromValue(spinnerAuthors.getSelectedItem().toString()) + "";
                // insert book
                dbManager.insertBook(editTextCodeBook.getText().toString(), editTextNameBook.getText().toString(), ediTextDatePublished.getText().toString(), authorId);

                Intent intent = new Intent(AddBookDialogActivity.this, ListBooksActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    public void reset(){
        editTextCodeBook.setText("");
        editTextNameBook.setText("");
        ediTextDatePublished.setText("");
        editTextCodeBook.requestFocus();
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            ediTextDatePublished.setText(day+"/"+(month+1)+"/"+year);
        }
    }
}
