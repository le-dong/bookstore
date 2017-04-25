package com.phuongdong.bookstore;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.phuongdong.bookstore.adapter.CustomCursorBooksAdapter;
import com.phuongdong.bookstore.database.DBManager;
import com.phuongdong.bookstore.model.Book;
import com.phuongdong.bookstore.util.Constant;

public class ListBooksActivity extends AppCompatActivity {

    private Button buttonAddBook;
    private ListView listViewBooks;

    private DBManager dbManager;
    private CustomCursorBooksAdapter adapter;
    private Cursor cursorBook = null;
    private Long _id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_books);
        setTitle("List Book");
        init();
        updateListView();
        event();
    }

    public void init(){
        buttonAddBook = (Button) findViewById(R.id.buttonAddBook);
        listViewBooks = (ListView) findViewById(R.id.listViewBooks);

        dbManager = new DBManager(this);
        dbManager.open();
    }

    public void updateListView(){
        cursorBook = dbManager.fetchBook();
        adapter = new CustomCursorBooksAdapter(this, cursorBook, 0);
        adapter.notifyDataSetChanged();
        listViewBooks.setAdapter(adapter);
    }

    public void event(){
        buttonAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListBooksActivity.this, AddBookDialogActivity.class);
                startActivity(intent);
            }
        });

        listViewBooks.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ListBooksActivity.this, EditBookDialogActivity.class);
                Bundle bundle = new Bundle();
                String idEdit = ((TextView) view.findViewById(R.id.textViewSTTBook)).getText().toString();
                String nameEdit = ((TextView) view.findViewById(R.id.textViewNameBook)).getText().toString();
                String dateEdit = ((TextView) view.findViewById(R.id.textViewDatePublishedBook)).getText().toString();
                String codeEdit = ((TextView) view.findViewById(R.id.textViewCodeBook)).getText().toString();
                String authorIdEdit = ((TextView) view.findViewById(R.id.textViewAuthorId)).getText().toString();
                Book book = new Book();
                book.setIdBook(idEdit);
                book.setNameBook(nameEdit);
                book.setDatePublished(dateEdit);
                book.setCodeBook(codeEdit);
                book.setAuthorId(authorIdEdit);
                bundle.putSerializable(Constant.DEFAULT_NAME_BOOK ,book);
                intent.putExtra(Constant.DEFAULT_NAME_BUNDLE, bundle);
                startActivity(intent);
            }
        });

        listViewBooks.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String idDelete = ((TextView) view.findViewById(R.id.textViewSTTBook)).getText().toString();
                String nameDelete = ((TextView) view.findViewById(R.id.textViewNameBook)).getText().toString();
                _id = Long.parseLong(idDelete);
                AlertDialog.Builder builder=new AlertDialog.Builder (ListBooksActivity.this);
                builder.setTitle("Question");
                builder.setMessage("Do you want delete book " + nameDelete + " ?");
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
                        dbManager.deleteBook(_id);
                        updateListView();
                    }
                });
                builder.show();
                return false;
            }
        });

    }
}
