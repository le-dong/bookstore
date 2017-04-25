package com.phuongdong.bookstore.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.phuongdong.bookstore.R;
import com.phuongdong.bookstore.database.DatabaseHelper;

/**
 * Created by phuongdong on 4/13/17.
 */

public class CustomCursorBooksAdapter extends CursorAdapter {

    private final LayoutInflater inflater;
    public CustomCursorBooksAdapter(Context context, Cursor c, int flags) {
        super(context, c , flags);
        this.inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.custom_list_view_books, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewSTTAuthor = (TextView) view.findViewById(R.id.textViewSTTBook);
        TextView textViewNameAuthor = (TextView)view.findViewById(R.id.textViewNameBook);
        TextView textViewDatePublishedBook = (TextView)view.findViewById(R.id.textViewDatePublishedBook);
        TextView textViewCodeBook = (TextView)view.findViewById(R.id.textViewCodeBook);
        TextView textViewAuthorId = (TextView)view.findViewById(R.id.textViewAuthorId);

        int indexId = cursor.getColumnIndex(DatabaseHelper._ID_BOOK);
        int indexName = cursor.getColumnIndex(DatabaseHelper.NAME_BOOK);
        int indexDate = cursor.getColumnIndex(DatabaseHelper.DATE_PUBLISHED);
        int indexCode = cursor.getColumnIndex(DatabaseHelper.CODE_BOOK);
        int indexAuthorId = cursor.getColumnIndex(DatabaseHelper.AUTHOR_ID);
        textViewSTTAuthor.setText(cursor.getString(indexId));
        textViewNameAuthor.setText(cursor.getString(indexName));
        textViewDatePublishedBook.setText(cursor.getString(indexDate));
        textViewCodeBook.setText(cursor.getString(indexCode));
        textViewAuthorId.setText(cursor.getString(indexAuthorId));

    }
}
