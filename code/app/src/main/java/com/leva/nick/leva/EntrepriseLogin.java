package com.leva.nick.leva;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

import static com.leva.nick.leva.dataManager.DataManager.readEntrepriseAccountDetails;
import static com.leva.nick.leva.dataManager.DataManager.writeEntrepriseAccountDetails;


public class EntrepriseLogin extends Activity {

    private EditText editTextUserName;
    private EditText editTextPassword;
    private Button buttonConnect;
    private Button buttonNoAccount;
    private EditText editTextUserNameNew;
    private EditText editTextPasswordNew;
    private EditText editTextPasswordNewVerification;
    private EditText editTextAddress;
    private EditText editTextPhoneNumber;
    private EditText editTextDescription;
    private ImageButton imageButtonLogo;
    private Button buttonCreateAccount;

    private int SELECT_PICTURE = 1;
    private String selectedImagePath;
    ImageButton selectedPicture;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entreprise_login);

        editTextUserName = (EditText) findViewById(R.id.editTextUserName);
        editTextPassword = (EditText) findViewById(R.id.editTextPassword);

        buttonConnect = (Button) findViewById(R.id.buttonConnect);
        buttonNoAccount = (Button) findViewById(R.id.buttonNoAccount);

        editTextUserNameNew = (EditText) findViewById(R.id.editTextUserNameNew);
        editTextPasswordNew = (EditText) findViewById(R.id.editTextPasswordNew);
        editTextPasswordNewVerification = (EditText) findViewById(R.id.editTextPasswordNewVerification);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);
        editTextPhoneNumber = (EditText) findViewById(R.id.editTextPhoneNumber);
        editTextDescription = (EditText) findViewById(R.id.editTextDescription);
        imageButtonLogo = (ImageButton) findViewById(R.id.imageButtonLogo);

        buttonCreateAccount = (Button) findViewById(R.id.buttonCreateAccount);

        editTextUserNameNew.setVisibility(View.INVISIBLE);
        editTextPasswordNew.setVisibility(View.INVISIBLE);
        editTextPasswordNewVerification.setVisibility(View.INVISIBLE);
        editTextAddress.setVisibility(View.INVISIBLE);
        editTextPhoneNumber.setVisibility(View.INVISIBLE);
        editTextDescription.setVisibility(View.INVISIBLE);
        imageButtonLogo.setVisibility(View.INVISIBLE);
        buttonCreateAccount.setVisibility(View.INVISIBLE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_entreprise_login, menu);
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


    public void showAddingFields(View view) {
        editTextUserNameNew.setVisibility(View.VISIBLE);
        editTextPasswordNew.setVisibility(View.VISIBLE);
        editTextPasswordNewVerification.setVisibility(View.VISIBLE);
        editTextAddress.setVisibility(View.VISIBLE);
        editTextPhoneNumber.setVisibility(View.VISIBLE);
        editTextDescription.setVisibility(View.VISIBLE);
        imageButtonLogo.setVisibility(View.VISIBLE);
        buttonCreateAccount.setVisibility(View.VISIBLE);
    }

    public boolean checkFieldsNewAccount() {

        if (editTextUserNameNew.getText().toString().equals("") ||
                editTextPasswordNew.getText().toString().equals("") ||
                editTextPasswordNewVerification.getText().toString().equals("") ||
                editTextAddress.getText().toString().equals("") ||
                editTextDescription.getText().toString().equals("")) {
            buttonCreateAccount.setTextColor(new Color().RED);
            return false;
        } else {
            buttonCreateAccount.setTextColor(new Color().GREEN);
            return true;
        }
    }

    public void createAccount(View view) {

        if (checkFieldsNewAccount()) {

            Log.d("IMA", "------ Validation done.");

            ArrayList<String> accountDetailsList = new ArrayList<String>();

            accountDetailsList.add(editTextUserNameNew.getText().toString());
            accountDetailsList.add(editTextPasswordNew.getText().toString());
            accountDetailsList.add(editTextPasswordNewVerification.getText().toString());
            accountDetailsList.add(editTextAddress.getText().toString());
            accountDetailsList.add(editTextPhoneNumber.getText().toString());
            accountDetailsList.add(editTextDescription.getText().toString());
            // TODO add image path.
            accountDetailsList.add("IMAGE PATH");

            Log.d("IMA", "------ Validation OK, start writing the information");

            writeEntrepriseAccountDetails(this, accountDetailsList, accountDetailsList.get(0));

            Log.d("IMA", "------ Wrote the information");

            Toast.makeText(getApplicationContext(), "Connectez-vous avec votre nouveau profil !", Toast.LENGTH_LONG).show();

            editTextUserNameNew.setVisibility(View.INVISIBLE);
            editTextPasswordNew.setVisibility(View.INVISIBLE);
            editTextPasswordNewVerification.setVisibility(View.INVISIBLE);
            editTextAddress.setVisibility(View.INVISIBLE);
            editTextPhoneNumber.setVisibility(View.INVISIBLE);
            editTextDescription.setVisibility(View.INVISIBLE);
            imageButtonLogo.setVisibility(View.INVISIBLE);
            buttonCreateAccount.setVisibility(View.INVISIBLE);
        }
    }

    public void chooseLogoFromLibrary(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Choisir une photo"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                selectedImagePath = getPath(selectedImageUri);

                imageButtonLogo.setImageURI(selectedImageUri);
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

    public boolean checkFieldsExistingAccount() {

        if (editTextUserName.getText().toString().equals("") ||
                editTextPassword.getText().toString().equals("")) {
            buttonCreateAccount.setTextColor(new Color().RED);
            return false;
        } else {
            buttonCreateAccount.setTextColor(new Color().GREEN);
            return true;
        }
    }

    public void connectionAction(View view) {

        if (checkFieldsExistingAccount()) {
            // readEntrepriseAccountDetails(this, editTextUserName.getText().toString()).get(0).toString().equalsIgnoreCase(editTextUserName.getText().toString());


            ArrayList<String> user = new ArrayList<String>();

            Log.d("IMA", "---- User access in progress.");

            user = readEntrepriseAccountDetails(this, editTextUserName.getText().toString());

            if (user.get(0).equalsIgnoreCase(editTextUserName.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Connecté en tant que " + editTextUserName.getText().toString(), Toast.LENGTH_LONG).show();
                Log.d("IMA", "---- User access granted.");
                Intent intent = new Intent(getApplicationContext(), connectedEnterprise.class);
                intent.putExtra("username", editTextUserName.getText().toString());
                startActivity(intent);
            }
        } else {
            Log.d("IMA", "---- User unknown.");
        }
    }
}
