package com.example.booklistactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

public class BookListActivity extends AppCompatActivity {

    private String mJsonResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        try {
            URL bookUrl = ApiUtils.buildUrl("cooking");
            new BookQueryTask().execute(bookUrl);
        } catch (Exception e) {
            Log.d("Error", e.toString());
        }


    }

    public class BookQueryTask extends AsyncTask<URL,Void,String>{

        @Override
        protected String doInBackground(URL... urls) {
            URL searchURL = urls[0];
            String result = null;
            try {
                result = ApiUtils.getJson(searchURL);
            } catch (IOException e) {
                Log.d("Error", e.toString());
                //return null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            TextView resultView = (TextView) findViewById(R.id.tvResponse);

            if (result != null){
                resultView.setText(result);
            }
        }
    }
}