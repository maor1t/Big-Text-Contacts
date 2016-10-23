package com.example.Contacts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CallLog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String TAG = "MainActivity";
    private static final int CALLS_LOADER_ID = 1;


    private ListView listCalls;

    private CallsAdapter adapter;

    // ----------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        adapter = new CallsAdapter(this, null);
        listCalls = (ListView) findViewById(R.id.listCalls);
        listCalls.setAdapter(adapter);


        getSupportLoaderManager().initLoader(CALLS_LOADER_ID, null, this);

        Log.d(TAG, "..onCreate method is exiting");
        listCalls.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                Cursor line = (Cursor) adapter.getItem(position);

                String number = line.getString(line.getColumnIndex(CallLog.Calls.NUMBER));
                // Toast.makeText(MainActivity.this,number, Toast.LENGTH_SHORT).show();
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:" + number));

                startActivity(callIntent);
            }


        });
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        Log.d(TAG,"onCreateLoader");

        return new CursorLoader(this,

                CallLog.Calls.CONTENT_URI,

                new String[]{CallLog.Calls._ID, CallLog.Calls.NUMBER, CallLog.Calls.TYPE, CallLog.Calls.DATE,CallLog.Calls.CACHED_NAME},

                CallLog.Calls.TYPE + " = ? OR " + CallLog.Calls.TYPE + " = ?",
                new String[]{CallLog.Calls.OUTGOING_TYPE+ "", CallLog.Calls.MISSED_TYPE + ""},

                CallLog.Calls.DATE + " DESC"
        );
    }

    // ----------------------------------------------------------------------------
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG,"onLoadFinished");

        cursor.setNotificationUri(getContentResolver(), CallLog.Calls.CONTENT_URI);
        adapter.swapCursor(cursor);

    }

    // ----------------------------------------------------------------------------
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG,"onLoaderReset");

        adapter.swapCursor(null);
    }

}
