package com.example.booklistactivity;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import androidx.databinding.BindingAdapter;

import static java.security.AccessController.getContext;

public class Book implements Parcelable {
    public String id;
    public String title;
    public String subTitle;
    public String authors[];
    public String publisher;
    public String publishedDate;
    public String description;
    public String thumbnail;

    public Book(String id, String title, String subTitle, String[] authors, String publisher, String publishedDate, String description, String thumbnail) {
        this.id = id;
        this.title = title;
        this.subTitle = subTitle;
        this.authors = authors;
        this.publisher = publisher;
        this.publishedDate = publishedDate;
        this.description = description;
        this.thumbnail = thumbnail;
    }

    protected Book(Parcel in) {
        id = in.readString();
        title = in.readString();
        subTitle = in.readString();
        authors = in.createStringArray();
        publisher = in.readString();
        publishedDate = in.readString();
        description = in.readString();
        thumbnail = in.readString();
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(subTitle);
        parcel.writeStringArray(authors);
        parcel.writeString(publisher);
        parcel.writeString(publishedDate);
        parcel.writeString(description);
        parcel.writeString(thumbnail);
    }

    @BindingAdapter({"android:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl){
        if (!imageUrl.isEmpty())
            Picasso.get().load(imageUrl).placeholder(R.drawable.ic_baseline_menu_book_24).into(view);
        else{
            //view.setScaleX(25);
            //view.setScaleY(40);
            view.setBackgroundResource(R.drawable.ic_baseline_menu_book_24);
        }

    }
}
