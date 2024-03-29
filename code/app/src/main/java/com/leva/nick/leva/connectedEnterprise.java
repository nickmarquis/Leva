package com.leva.nick.leva;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.leva.nick.leva.openingHours.OpeningHours;
import com.leva.nick.leva.promotions.PromotionEditList;


public class connectedEnterprise extends Activity {

    String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connected_enterprise);

        mUsername = getIntent().getExtras().getString("username");

        TextView textViewUsername = (TextView)findViewById(R.id.textViewUsername);
        textViewUsername.setText("Bonjour " + mUsername + " !");
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_connected_enterprise, menu);
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


    public void listActivity(View view) {
        Intent intent = new Intent(connectedEnterprise.this, PromotionEditList.class);
        intent.putExtra("username", mUsername);
        startActivity(intent);
    }

    public void listOpeningHoursActivity(View view) {
        Intent intent = new Intent(connectedEnterprise.this, OpeningHours.class);
        intent.putExtra("username", mUsername);
        startActivity(intent);
    }

    public void goBack(View view) {
        Intent intent = new Intent(connectedEnterprise.this, TabLayoutA.class);
        intent.putExtra("username", mUsername);
        startActivity(intent);
    }
}
