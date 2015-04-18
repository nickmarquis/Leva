package com.leva.nick.leva.openingHours;

import android.app.Activity;
import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.leva.nick.leva.openingHours.ItemOpeningHours;

import java.util.ArrayList;

import com.leva.nick.leva.R;

import static com.leva.nick.leva.dataManager.DataManager.readOpeningHours;
import static com.leva.nick.leva.dataManager.DataManager.writeOpeningHours;


public class OpeningHours extends Activity {

    ArrayList<ItemOpeningHours> items = new ArrayList<>();

    private String[] dayOfWeek = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};

    Button button;

    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opening_hours);

        // On passe le contexte et les données à l'adapteur personnalisé.
        MyAdapterOpeningHours adapter = new MyAdapterOpeningHours(this, generateData());

        // Liste principale.
        ListView listView = (ListView) findViewById(R.id.listView);

        // Ajout de l'adapter à la liste principale.
        listView.setAdapter(adapter);

        // Ajout d'un OnClickListener sur la liste principale.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                View parentRow = (View) view.getParent();
                ListView listView = (ListView) parentRow.getParent();
            }
        });

        button = (Button) findViewById(R.id.buttonAdd);



        mUsername = getIntent().getExtras().getString("username");
    }

    private ArrayList<ItemOpeningHours> generateData() {

        ArrayList<ItemOpeningHours> readInFile = readOpeningHours(this);
        if (readInFile != null) {
            items = readInFile;
        } else {

            for (int i = 0; i < dayOfWeek.length; i++) {
                items.add(new ItemOpeningHours(dayOfWeek[i], "", ""));
            }
        }
        return items;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_opening_hours, menu);
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

    public void checkFields(View view) {

        for (int i = 0; i < dayOfWeek.length - 1; i++) {
            if (items.get(i).getTimeOpen().equals("") || items.get(i).getTimeClose().equals("")) {
                button.setTextColor(new Color().RED);
            } else {
                button.setTextColor(new Color().GREEN);
                writeOpeningHours(this, items);
            }
        }
    }
}
