package com.example.moviedirectory.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviedirectory.Data.MovieRecycleViewAdapater;
import com.example.moviedirectory.Model.Movie;
import com.example.moviedirectory.R;
import com.example.moviedirectory.Util.Constants;
import com.example.moviedirectory.Util.Prefs;
import com.squareup.picasso.Downloader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private RecyclerView recyclerView;
private MovieRecycleViewAdapater movieRecycleViewAdapater;
private RequestQueue queue;
private List<Movie> movieList;
private AlertDialog.Builder alert;
private AlertDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
      queue= Volley.newRequestQueue(this);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
 getdialog();
            }
        });
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        movieList=new ArrayList<>();
        Prefs prefs=new Prefs(this);
       String search=prefs.getsearch();
       movieList=getmovies(search);
        movieRecycleViewAdapater=new MovieRecycleViewAdapater(MainActivity.this,movieList);
        recyclerView.setAdapter(movieRecycleViewAdapater);
        movieRecycleViewAdapater.notifyDataSetChanged();
    }

    public List<Movie> getmovies(String searchterm)
    {
        movieList.clear();
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Constants.url_left +
                searchterm + Constants.url_right + Constants.api_key, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
try
{
    JSONArray moviearray=response.getJSONArray("Search");
    for(int i=0;i<moviearray.length();i++)
    {
        JSONObject movieObj=moviearray.getJSONObject(i);

        Movie movie = new Movie();
        movie.setTitle(movieObj.getString("Title"));
        movie.setYear("Year Released: " + movieObj.getString("Year"));
        movie.setMovietype("Type: " + movieObj.getString("Type"));
        movie.setPoster(movieObj.getString("Poster"));
        movie.setImdbID(movieObj.getString("imdbID"));
        movie.setYear(movieObj.getString("Year"));
        movieList.add(movie);

    }
    movieRecycleViewAdapater.notifyDataSetChanged();
}
catch(JSONException e)
{
    e.printStackTrace();
}
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
queue.add(jsonObjectRequest);
return movieList;
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        getdialog();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void getdialog()
    {
        alert=new AlertDialog.Builder(this);
        View v=getLayoutInflater().inflate(R.layout.dialog_view,null);
        final EditText newSearchEdt = (EditText)v.findViewById(R.id.searchEdt);
        Button submitButton = (Button) v.findViewById(R.id.submitButton);

        alert.setView(v);
        dialog=alert.create();
        dialog.show();
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Prefs prefs=new Prefs(MainActivity.this);
                if(!newSearchEdt.getText().toString().equals(""))
                {
                    prefs.setsearch(newSearchEdt.getText().toString());
                    movieList.clear();
                    getmovies(newSearchEdt.getText().toString());
                }
                else
                    Toast.makeText(MainActivity.this, "Enter a moviename to search", Toast.LENGTH_SHORT).show();
                dialog.dismiss();

            }
        });


    }
}
