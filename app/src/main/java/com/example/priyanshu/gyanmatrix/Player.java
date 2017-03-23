package com.example.priyanshu.gyanmatrix;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Player extends AppCompatActivity {
    private TextView tv_runs,tv_match,tv_name,tv_description,tv_country;
private ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player_detail);
        int runs=getIntent().getIntExtra("runs",0);

        int matches=getIntent().getIntExtra("matches",0);
        String description=getIntent().getExtras().getString("description");
        String name=getIntent().getExtras().getString("name");
        String country=getIntent().getExtras().getString("country");
        String imageurl=getIntent().getExtras().getString("imageurl");
        tv_country=(TextView)findViewById(R.id.country_view);
        tv_description=(TextView)findViewById(R.id.description_view);
        tv_name=(TextView)findViewById(R.id.name_view);
        iv=(ImageView)findViewById(R.id.image_view);
        tv_runs=(TextView)findViewById(R.id.runs_view);
        tv_match=(TextView)findViewById(R.id.matches_view);
        tv_country.setText(country.toUpperCase());
        tv_match.setText("Matches: "+Integer.toString(matches));
        tv_name.setText(name.toUpperCase());
        tv_description.setText(description);
        tv_runs.setText("Runs: "+Integer.toString(runs));
        Picasso.with(this).load(imageurl).fit().placeholder(R.drawable.placeholder).into(iv);



    }
}
