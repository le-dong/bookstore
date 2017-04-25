package com.phuongdong.bookstore;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.phuongdong.bookstore.adapter.CustomCursorAuthorsAdapter;
import com.phuongdong.bookstore.database.DBManager;
import com.phuongdong.bookstore.database.DatabaseHelper;
import com.phuongdong.bookstore.model.Author;
import com.phuongdong.bookstore.util.Constant;

public class MainActivity extends AppCompatActivity {

    private DBManager dbManager;

    private Button buttonListAuthors, buttonAddAuthor, buttonListBooks;
    private ListView listViewAuthors;

    private CustomCursorAuthorsAdapter adapter;
    private Cursor cursorAuthor = null;
    private Long _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("List Author");
        init();
        updateListView();
        event();
    }

    public void init(){
        buttonListAuthors = (Button) findViewById(R.id.buttonListAuthors);
        buttonAddAuthor = (Button) findViewById(R.id.buttonAddAuthor);
        buttonListBooks = (Button) findViewById(R.id.buttonListBooks);
        listViewAuthors = (ListView) findViewById(R.id.listViewAuthors);

        dbManager = new DBManager(this);
        dbManager.open();
    }

    public void updateListView(){
        cursorAuthor = dbManager.fetchAuthor();
        adapter = new CustomCursorAuthorsAdapter(this, cursorAuthor, 0);
        adapter.notifyDataSetChanged();
        listViewAuthors.setAdapter(adapter);
    }

    public void event(){
        buttonAddAuthor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddAuthorDialogActivity.class);
                startActivity(intent);
            }
        });

        buttonListBooks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ListBooksActivity.class);
                startActivity(intent);
            }
        });

        listViewAuthors.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, EditAuthorDialogActivity.class);
                Bundle bundle = new Bundle();
                String idEdit = ((TextView) view.findViewById(R.id.textViewSTTAuthor)).getText().toString();
                String codeEdit = ((TextView) view.findViewById(R.id.textViewCodeAuthor)).getText().toString();
                String nameEdit = ((TextView) view.findViewById(R.id.textViewNameAuthor)).getText().toString();
                Author author = new Author(idEdit, codeEdit, nameEdit);
                bundle.putSerializable(Constant.DEFAULT_NAME_AUTHOR ,author);
                intent.putExtra(Constant.DEFAULT_NAME_BUNDLE, bundle);
                startActivity(intent);
            }
        });

        listViewAuthors.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String idDelete = ((TextView) view.findViewById(R.id.textViewSTTAuthor)).getText().toString();
                String codeDelete = ((TextView) view.findViewById(R.id.textViewCodeAuthor)).getText().toString();
                String nameDelete = ((TextView) view.findViewById(R.id.textViewNameAuthor)).getText().toString();
                _id = Long.parseLong(idDelete);
                AlertDialog.Builder builder=new AlertDialog.Builder (MainActivity.this);
                builder.setTitle("Question");
                builder.setMessage("Do you want delete author [" + codeDelete + "] " + nameDelete + "?");
                builder.setIcon(android.R.drawable.ic_input_delete);
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        arg0.cancel();
                    }
                });
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dbManager.deleteAuthor(_id);
                        updateListView();
                    }
                });
                builder.show();
                return false;
            }
        });
    }

    public  void fakeDataAuthors(){
        dbManager.insertAuthor("author_01", "Nguyen Nhat Anh");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbManager.close();
    }

}
