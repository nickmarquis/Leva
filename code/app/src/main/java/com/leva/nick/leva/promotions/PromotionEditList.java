package ca.ulaval.ima.miniproject.promotions;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

import ca.ulaval.ima.miniproject.R;

import static ca.ulaval.ima.miniproject.dataManager.DataManager.readPromotions;
import static ca.ulaval.ima.miniproject.dataManager.DataManager.writePromotions;


public class PromotionEditList extends ActionBarActivity {

    ListView listView;
    ArrayList<ItemPromotion> items = new ArrayList<ItemPromotion>();

    private String[] dayOfWeek = {"Lundi", "Mardi", "Mercredi", "Jeudi", "Vendredi", "Samedi", "Dimanche"};

    private int SELECT_PICTURE = 1;
    private String selectedImagePath;
    ImageButton selectedPicture;
    Uri imageUri;

    private int mPosition;

    private int mImageToAdd = 0;
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_edit_list);

        // On passe le contexte et les données à l'adapteur personnalisé.
        MyAdapterPromotions adapter = new MyAdapterPromotions(this, generateData());

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
                mPosition = listView.getPositionForView(parentRow);
            }
        });

        button = (Button) findViewById(R.id.buttonAdd);
    }

    private ArrayList<ItemPromotion> generateData(){

        ArrayList<ItemPromotion> readInFile = readPromotions(this);

        if (readInFile != null) {
            items = readInFile;
        } else {
            for (int i = 0; i < dayOfWeek.length; i++) {
                items.add(new ItemPromotion(dayOfWeek[i], "", "", "", "", ""));
            }
        }
        return items;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_promotion_edit_list, menu);
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


    public void choosePictureFromLibrary(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Choisir une photo"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);

                selectedPicture = (ImageButton) findViewById(R.id.imageButton);
                selectedPicture.setImageURI(selectedImageUri);
                items.get(mPosition).setImage(selectedImageUri.toString());

                if (mImageToAdd < dayOfWeek.length-1) {
                    mImageToAdd += 1;
                } else if (mImageToAdd == dayOfWeek.length) {
                    mImageToAdd = 0;
                }
            }
        }
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

    public void checkPromotionFields(View view) {

        for (int i = 0 ; i < dayOfWeek.length-1 ; i++) {
            if (items.get(i).getPromotionName().equals("") ||
                items.get(i).getPromotionDescription().equals("") ||
                items.get(i).getTimeStart().equals("") ||
                items.get(i).getTimeEnd().equals("")) {
                button.setTextColor(new Color().RED);
            } else {
                button.setTextColor(new Color().GREEN);
                writePromotions(this, items);
            }
        }
    }
}
