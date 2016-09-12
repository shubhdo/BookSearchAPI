package com.cetpainfotech.booksearch;
import cz.msebera.android.httpclient.Header;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class BookListActivity extends AppCompatActivity {
private ListView listView;
    private BookAdapter bookAdapter;
    private BookClient client;
    private ProgressBar progress;
    public static final String BOOK_DETAIL_KEY="book";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);
        listView= (ListView) findViewById(R.id.lvBook);
        progress= (ProgressBar) findViewById(R.id.progress);
        ArrayList<Book> abooks=new ArrayList<>();
        bookAdapter=new BookAdapter(this,abooks);
   listView.setAdapter(bookAdapter);
        setupBookSelectedListener();
    }

    public void setupBookSelectedListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(BookListActivity.this,BookDetailActivity.class);
                intent.putExtra(BOOK_DETAIL_KEY,bookAdapter.getItem(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_book_list, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Fetch the data remotely
                fetchBooks(query);
                // Reset SearchView
                searchView.clearFocus();
                searchView.setQuery("", false);
                searchView.setIconified(true);
                searchItem.collapseActionView();
                // Set activity title to search query
                BookListActivity.this.setTitle(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return true;
    }



    private void fetchBooks(String query) {
        progress.setVisibility(ProgressBar.VISIBLE);

        client=new BookClient();
        client.getBooks(query,new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    progress.setVisibility(View.GONE);
                JSONArray docs=null;

                    if (response!=null) {
                    docs=response.getJSONArray("docs");
                    final ArrayList<Book> books=Book.fromJson(docs);
                    bookAdapter.clear();
                    for (Book book:books) {
                        bookAdapter.add(book);

                    }
                    bookAdapter.notifyDataSetChanged(); }} catch (JSONException e) {
                    Toast.makeText(BookListActivity.this, "e.printStackTrace()", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                progress.setVisibility(ProgressBar.GONE);
                Toast.makeText(BookListActivity.this, "connection error", Toast.LENGTH_SHORT).show();
            }
        });
        }
    }


