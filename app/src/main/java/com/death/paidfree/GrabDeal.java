package com.death.paidfree;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

public class GrabDeal extends AppCompatActivity {

    Toolbar toolbar;
    ImageView photo;
    TextView dealTitle;
    TextView deal_desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grab_deal);
        photo = (ImageView) findViewById(R.id.photo);
        dealTitle = (TextView) findViewById(R.id.dealTitle);
        deal_desc = (TextView) findViewById(R.id.deal_desc);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dealTitle.setText(getIntent().getStringExtra("TITLE"));
        deal_desc.setText(getIntent().getStringExtra("DESC"));

        Picasso.with(this)
                .load(getIntent().getData())
                .into(photo);
        Log.e("DATA", getIntent().getStringExtra("TITLE"));

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAfterTransition();
            }
        });

    }
}
