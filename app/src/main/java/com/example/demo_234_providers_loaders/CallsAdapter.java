package com.example.demo_234_providers_loaders;


import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.support.v4.widget.CursorAdapter;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CallsAdapter extends CursorAdapter {


    public CallsAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        // inflate the view from XML
        return LayoutInflater.from(context).inflate(R.layout.listitem_call, viewGroup, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {


        long time = cursor.getLong(cursor.getColumnIndex(CallLog.Calls.DATE));
        String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
        int type = cursor.getInt(cursor.getColumnIndex(CallLog.Calls.TYPE));
        String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));



        TextView textNumber = (TextView) view.findViewById(R.id.Number);
        TextView textDate = (TextView) view.findViewById(R.id.Date);
        ImageView imageCallType = (ImageView) view.findViewById(R.id.imageCallType);
        TextView textName = (TextView) view.findViewById(R.id.Name);


        textNumber.setText(number);
        textName.setText(name);
        textDate.setText(DateUtils.getRelativeTimeSpanString(time));


        if (type == CallLog.Calls.OUTGOING_TYPE) {
            imageCallType.setImageResource(R.drawable.call_incoming);
        } else if (type == CallLog.Calls.MISSED_TYPE) {
            imageCallType.setImageResource(R.drawable.call_missed);
        }

    }
}
