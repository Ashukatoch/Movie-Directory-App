package com.example.moviedirectory.Data;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviedirectory.Activity.Moviedetailsactivity;
import com.example.moviedirectory.Model.Movie;
import com.example.moviedirectory.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieRecycleViewAdapater extends RecyclerView.Adapter<MovieRecycleViewAdapater.ViewHolder> {
    private Context context;
    private List<Movie> movieList;
    public MovieRecycleViewAdapater(Context context, List<Movie> movie) {
this.context=context;
movieList=movie;
    }

    @NonNull
    @Override

    public MovieRecycleViewAdapater.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_row,viewGroup,false);
        return new ViewHolder(view,context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i)
    {
        Movie movie=movieList.get(i);
        String posterlink=movie.getPoster();
        viewHolder.movietitle.setText(movie.getTitle());
        viewHolder.type.setText(movie.getMovietype());
        viewHolder.year.setText("Year:"+movie.getYear());
        Picasso.with(context).load(posterlink).placeholder(android.R.drawable.ic_btn_speak_now).into(viewHolder.poster);



    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView movietitle;
        TextView year;
        TextView type;
        ImageView poster;
        public ViewHolder(@NonNull View v,Context ctx) {
            super(v);
            movietitle=(TextView)v.findViewById(R.id.movietitleid);
            poster=(ImageView)v.findViewById(R.id.movieimageid);
            year=(TextView)v.findViewById(R.id.yearid);
            type=(TextView)v.findViewById(R.id.typeid);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Movie movie=movieList.get(getAdapterPosition());
                    Intent intent =new Intent(context, Moviedetailsactivity.class);
                    intent.putExtra("movie",movie);
                    context.startActivity(intent);
                }
            });
        }

        @Override
        public void onClick(View v) {

        }
    }
}
