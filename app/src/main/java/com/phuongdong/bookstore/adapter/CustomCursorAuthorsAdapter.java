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

public class CustomCursorAuthorsAdapter extends CursorAdapter {

    private final LayoutInflater inflater;
    public CustomCursorAuthorsAdapter(Context context, Cursor c, int flags) {
        super(context, c , flags);
        this.inflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView (Context context, Cursor cursor, ViewGroup parent) {
        return inflater.inflate(R.layout.custom_list_view_authors, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewSTTAuthor = (TextView) view.findViewById(R.id.textViewSTTAuthor);
        TextView textViewCodeAuthor = (TextView)view.findViewById(R.id.textViewCodeAuthor);
        TextView textViewNameAuthor = (TextView)view.findViewById(R.id.textViewNameAuthor);

        int indexId = cursor.getColumnIndex(DatabaseHelper._ID_AUTHOR);
        int indexCode = cursor.getColumnIndex(DatabaseHelper.CODE_AUTHOR);
        int indexName = cursor.getColumnIndex(DatabaseHelper.NAME_AUTHOR);
        textViewSTTAuthor.setText(cursor.getString(indexId));
        textViewCodeAuthor.setText(cursor.getString(indexCode));
        textViewNameAuthor.setText(cursor.getString(indexName));

    }
}
