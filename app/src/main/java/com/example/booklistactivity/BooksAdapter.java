package com.example.booklistactivity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder>{

    Context mContext;
    ArrayList<Book> mBooks;
    public BooksAdapter(ArrayList<Book> books){
        mBooks = books;
        //mContext = context;
    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.book_list_item,parent,false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = mBooks.get(position);
        holder.bind(book);

    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public class BookViewHolder extends RecyclerView.ViewHolder{
        TextView bookTitle;
        TextView publisher;
        TextView publishedDate;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = (TextView) itemView.findViewById(R.id.book_title);
            publishedDate = (TextView) itemView.findViewById(R.id.book_published_date);
            publisher = (TextView) itemView.findViewById(R.id.book_publisher);

        }

        public void bind(Book book){
            bookTitle.setText(book.title);
            publisher.setText(book.publisher);
            publishedDate.setText(book.publishedDate);
        }
    }
}
