package com.leva.nick.leva.promotions;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

import com.leva.nick.leva.R;

/**
 * Created by davstpierre on 15-03-14.
 */
public class MyAdapterPromotions extends ArrayAdapter<ItemPromotion> {

    private final Context context;
    private final ArrayList<ItemPromotion> itemsArrayList;
    private ImageButton imageButton;

    public MyAdapterPromotions(Context context, ArrayList<ItemPromotion> itemsArrayList) {
        super(context, R.layout.day_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        // Création de l'inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Obtenir la vue d'une rangée provenant de l'inflater.
        View rowView = inflater.inflate(R.layout.day_row, parent, false);

        // Obtenir les éléments graphiques du modèle d'une rangée.
        final TextView textViewDay = (TextView) rowView.findViewById(R.id.textViewDay);
        final EditText editTextPromotion = (EditText) rowView.findViewById(R.id.editTextPromotion);
        final EditText editTextPromotionDescription = (EditText) rowView.findViewById(R.id.editTextPromotionDescription);
        final EditText editTextStart = (EditText) rowView.findViewById(R.id.editTextOpen);
        final EditText editTextEnd = (EditText) rowView.findViewById(R.id.editTextEnd);
        imageButton = (ImageButton) rowView.findViewById(R.id.imageButton);

        // Placer le texte pour la vue du modèle d'une rangée;
        textViewDay.setText(itemsArrayList.get(position).getDay());
        editTextPromotion.setText(itemsArrayList.get(position).getPromotionName());
        editTextPromotionDescription.setText(itemsArrayList.get(position).getPromotionDescription());
        editTextStart.setText(itemsArrayList.get(position).getTimeStart());
        editTextEnd.setText(itemsArrayList.get(position).getTimeEnd());
        imageButton.setImageURI(Uri.parse(itemsArrayList.get(position).getImage()));

        // Place l'étiquette de l'objet
        editTextPromotion.setTag(position);
        editTextPromotionDescription.setTag(position);
        editTextStart.setTag(position);
        editTextEnd.setTag(position);
        imageButton.setTag(position);

        // Ajout du Listener
        editTextPromotion.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final int position = (int) v.getTag();
                    itemsArrayList.get(position).setPromotionName(editTextPromotion.getText().toString());
                }
            }
        });

        editTextPromotionDescription.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final int position = (int) v.getTag();
                    itemsArrayList.get(position).setPromotionDescription(editTextPromotionDescription.getText().toString());
                }
            }
        });

        editTextStart.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final int position = (int) v.getTag();
                    itemsArrayList.get(position).setTimeStart(editTextStart.getText().toString());
                }
            }
        });

        editTextEnd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final int position = (int) v.getTag();
                    itemsArrayList.get(position).setTimeEnd(editTextEnd.getText().toString());
                }
            }
        });

        return rowView;
    }
}
