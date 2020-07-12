package com.example.booklistactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.booklistactivity.databinding.ActivityBookDetailsBinding;

public class BookDetails extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_details);
        Book book  = getIntent().getParcelableExtra("Books");
        ActivityBookDetailsBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_book_details);
        binding.setBook(book);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.book_detail_menu,menu);
        return true;
    }
}