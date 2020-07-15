package com.example.booklistactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.Context;

import com.google.android.material.snackbar.Snackbar;

import java.net.URL;

public class SearchActivity extends AppCompatActivity {

    private EditText mAdvTitle;
    private EditText mAdvAuthor;
    private EditText mAdvPublisher;
    private EditText mAdvIsbn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mAdvTitle = (EditText) findViewById(R.id.adv_title);
        mAdvAuthor = (EditText) findViewById(R.id.adv_author);
        mAdvPublisher = (EditText) findViewById(R.id.adv_publisher);
        mAdvIsbn = (EditText) findViewById(R.id.adv_isbn);

        Button btnSearch = (Button) findViewById(R.id.adv_btn_search);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = mAdvTitle.getText().toString().trim();
                String author = mAdvAuthor.getText().toString().trim();
                String publisher = mAdvPublisher.getText().toString().trim();
                String isbn = mAdvIsbn.getText().toString().trim();

                if (title.isEmpty() && author.isEmpty() & publisher.isEmpty() && isbn.isEmpty()){
                    Snackbar.make(view, R.string.no_search_data,Snackbar.LENGTH_SHORT).show();
                }else{
                    URL searchUrl  = ApiUtils.buildUrl(title,author,publisher,isbn);

                    Context context = getApplicationContext();
                    int position = SpUtil.getPrefInt(context, SpUtil.POSITION);
                    if (position == 0 || position ==5){
                        position = 1;
                    }else {
                        position++;
                    }

                    String key = SpUtil.QUERY + String.valueOf(position);
                    String value = title+","+author+","+publisher+","+isbn;
                    SpUtil.writeStringData(context,key,value);
                    SpUtil.writeIntData(context, SpUtil.POSITION,position);

                    Intent intent = new Intent(view.getContext(),BookListActivity.class);
                    intent.putExtra(getString(R.string.intent_query_key), searchUrl.toString());
                    startActivity(intent);
                }
            }
        });
    }
}