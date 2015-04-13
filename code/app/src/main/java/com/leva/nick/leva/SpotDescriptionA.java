package com.leva.nick.leva;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class SpotDescriptionA extends Activity {

    SpotsMarker mSpot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spot_description);

        Intent intent = getIntent();
        mSpot = intent.getParcelableExtra("myMarker");

        ImageView image = (ImageView)findViewById(R.id.imgD);
        TextView tilte = (TextView)findViewById(R.id.tilteD);
        ExpandableTextView desc = (ExpandableTextView)findViewById(R.id.descD);

        int drawableResourceId = this.getResources().getIdentifier(mSpot.getmPicture(), "drawable", this.getPackageName());
        image.setImageResource(drawableResourceId);
        tilte.setText(mSpot.getmLabel());
        desc.setText(mSpot.getmDescription());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_spot_description, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
