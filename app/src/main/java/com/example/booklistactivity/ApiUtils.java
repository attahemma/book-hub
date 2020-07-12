package com.example.booklistactivity;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;

public class ApiUtils {
    private ApiUtils(){}
    public static final String BASE_API_URL = "https://www.googleapis.com/books/v1/volumes";
    public static final String QUERY_PARAMETER_KEY = "q";
    public static final String KEY = "key";
    private static final String API_KEY = "";

    public static URL buildUrl(String title){
        //String fullUrl = BASE_API_URL + "?q=" + title;
        URL url = null;
        Uri uri = Uri.parse(BASE_API_URL).buildUpon().appendQueryParameter(QUERY_PARAMETER_KEY, title).appendQueryParameter(KEY,API_KEY).build();
        try {
            url = new URL(uri.toString());
        }catch(Exception e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getJson(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            InputStream stream = connection.getInputStream();

            //converting the stream data to a string
            Scanner scanner = new Scanner(stream);

            //set delimiter
            scanner.useDelimiter("\\A");

            boolean hasData = scanner.hasNext();

            if (hasData){
                return scanner.next();
            }
            else {
                return null;
            }
        } catch (Exception e) {
            Log.d("Error", e.toString());
            return null;
        }finally {
            connection.disconnect();
        }
    }

    public static ArrayList<Book> getBooksFromJson(String json){
        final String ID = "id";
        final String TITLE = "title";
        final String SUBTITLE = "subtitle";
        final String AUTHORS = "authors";
        final String PUBLISHER = "publisher";
        final String PUBLISHED_DATE = "publishedDate";
        final String ITEMS = "items";
        final String VOLUME_INFO = "volumeInfo";
        String[] noAuthors = {"no authors"};

        ArrayList<Book> books = new ArrayList<>();
        try {
            //first create a json object from the json string fetched
            JSONObject jsonBooks = new JSONObject(json);

            //get all array of books from json object
            JSONArray arrayBooks = jsonBooks.getJSONArray(ITEMS);
            int authorsNum;
            //get count of all books returned from the api
            int numberOfBooks = arrayBooks.length();

            //loop through the returned array
            for (int i=0; i<numberOfBooks;i++){
                JSONObject bookJson = arrayBooks.getJSONObject(i);
                JSONObject volumeInfoJson = bookJson.getJSONObject(VOLUME_INFO);
                authorsNum = (volumeInfoJson.isNull(AUTHORS)?0:volumeInfoJson.getJSONArray(AUTHORS).length());
                if (authorsNum != 0){
                    String[] authors = new String[authorsNum];
                    for (int j = 0; j<authorsNum; j++){
                        authors[j] = volumeInfoJson.getJSONArray(AUTHORS).get(j).toString();
                    }
                    Book book = new Book(
                            bookJson.getString(ID),
                            volumeInfoJson.getString(TITLE),
                            (volumeInfoJson.isNull(SUBTITLE)?"":volumeInfoJson.getString(SUBTITLE)),
                            authors,
                            (bookJson.isNull(PUBLISHER)?"":bookJson.getString(PUBLISHER)),
                            (bookJson.isNull(PUBLISHED_DATE)?"":bookJson.getString(PUBLISHED_DATE))
                    );
                    books.add(book);
                }else{
                    Book book = new Book(
                      bookJson.getString(ID),
                            volumeInfoJson.getString(TITLE),
                            (volumeInfoJson.isNull(SUBTITLE)?"":volumeInfoJson.getString(SUBTITLE)),
                            noAuthors,
                            (bookJson.isNull(PUBLISHER)?"":bookJson.getString(PUBLISHER)),
                            (bookJson.isNull(PUBLISHED_DATE)?"":bookJson.getString(PUBLISHED_DATE))
                    );
                    books.add(book);
                }
            }
        } catch (JSONException e) {
            Log.d("Error", e.toString());
        }
        return books;
    }
}
