package com.example.booklistactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity implements SearchView.OnQueryTextListener{

    private String mJsonResult;
    private ProgressBar mProgressLoading;
    private RecyclerView mRvBooks;
    private BooksAdapter mBooksAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        mRvBooks =(RecyclerView) findViewById(R.id.books_recycle);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRvBooks.setLayoutManager(linearLayoutManager);
        mProgressLoading = (ProgressBar) findViewById(R.id.pb_loading);
        try {
            URL bookUrl = ApiUtils.buildUrl("Android");
            new BookQueryTask().execute(bookUrl);
        } catch (Exception e) {
            Log.d("Error", e.toString());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_list_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.app_bar_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        try {
            URL bookUrl = ApiUtils.buildUrl(s);
            new BookQueryTask().execute(bookUrl);
            //mBooksAdapter.notifyDataSetChanged();
        } catch (Exception e) {
            Log.d("Error", e.toString());
        }
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {

        return false;
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
            //TextView resultView = (TextView) findViewById(R.id.tvResponse);
            TextView tvError = (TextView) findViewById(R.id.tv_error);
            ImageView tvErrorImg = (ImageView) findViewById(R.id.error_image);
            mProgressLoading.setVisibility(View.INVISIBLE );
            if (result  == null){
                tvErrorImg.setVisibility(View.VISIBLE);
                mRvBooks.setVisibility(View.INVISIBLE);
                tvError.setVisibility(View.VISIBLE);
            }else {
                tvErrorImg.setVisibility(View.INVISIBLE);
                mRvBooks.setVisibility(View.VISIBLE);
                //resultView.setText(result);

                Log.d("Output",result);
                ArrayList<Book> books = ApiUtils.getBooksFromJson(result);
                String resultString = "";
                if (books.isEmpty()){


                    //resultString = resultString+"NO BOOK FOUND!";
                }else {
                    mBooksAdapter = new BooksAdapter(books);
                    mRvBooks.setAdapter(mBooksAdapter);
                }

            }

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressLoading.setVisibility(View.VISIBLE);

        }
    }
}