package com.cetpainfotech.booksearch;
import cz.msebera.android.httpclient.Header;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {
private ListView listView;
    private BookAdapter bookAdapter;
    private BookClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        listView= (ListView) findViewById(R.id.lvBook);
        ArrayList<Book> abooks=new ArrayList<>();
        bookAdapter=new BookAdapter(this,abooks);
   listView.setAdapter(bookAdapter);
        fetchBooks();
    }
    private void fetchBooks() {
        client=new BookClient();
        client.getBooks("oscar wilde",new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                JSONArray docs=null;
                if (response!=null) {
                    docs=response.getJSONArray("docs");
                    final ArrayList<Book> books=Book.fromJson(docs);
                    bookAdapter.clear();
                    for (Book book:books) {
                        bookAdapter.add(book);

                    }
                    bookAdapter.notifyDataSetChanged(); }} catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            });
        }
    }


