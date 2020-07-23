package com.example.booklistactivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

    public class BookViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnCreateContextMenuListener{
        TextView bookTitle;
        TextView publisher;
        TextView publishedDate;
        ImageView mBookImg;
        ImageView mItemMenu;

        public BookViewHolder(@NonNull View itemView) {
            super(itemView);
            bookTitle = (TextView) itemView.findViewById(R.id.book_title);
            publishedDate = (TextView) itemView.findViewById(R.id.book_published_date);
            publisher = (TextView) itemView.findViewById(R.id.book_publisher);
            mBookImg = (ImageView) itemView.findViewById(R.id.book_cover_img);
            mItemMenu = (ImageView) itemView.findViewById(R.id.item_menu);
            mItemMenu.setOnCreateContextMenuListener(this);
            itemView.setOnClickListener(this);
        }

        public void bind(Book book){
            bookTitle.setText(book.title);
            publisher.setText(book.publisher);
            publishedDate.setText(book.publishedDate);
            //mBookImg.setImageURI(new Uri());
        }

        @Override
        public void onClick(View view) {
            int position  = getAdapterPosition();
            Book bookSelected = mBooks.get(position);
            Intent intent = new Intent(view.getContext(),BookDetails.class);
            intent.putExtra("Books",bookSelected);
            view.getContext().startActivity(intent);
        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            contextMenu.setHeaderTitle("Book Actions");
            contextMenu.add(this.getAdapterPosition(),121,0,"Share with Friends");
        }
    }
}
