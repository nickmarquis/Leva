package ca.ulaval.ima.miniproject.openingHours;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import ca.ulaval.ima.miniproject.R;

/**
 * Created by davstpierre on 15-04-06.
 */
public class MyAdapterOpeningHours extends ArrayAdapter<ItemOpeningHours> {
    private final Context context;
    private final ArrayList<ItemOpeningHours> itemsArrayList;

    public MyAdapterOpeningHours(Context context, ArrayList<ItemOpeningHours> itemsArrayList) {
        super(context, R.layout.opening_hours_row, itemsArrayList);

        this.context = context;
        this.itemsArrayList = itemsArrayList;
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        // Création de l'inflater
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Obtenir la vue d'une rangée provenant de l'inflater.
        View rowView = inflater.inflate(R.layout.opening_hours_row, parent, false);

        // Obtenir les éléments graphiques du modèle d'une rangée.
        final TextView textViewDay = (TextView) rowView.findViewById(R.id.textViewDay);
        final EditText editTextOpen = (EditText) rowView.findViewById(R.id.editTextOpen);
        final EditText editTextClose = (EditText) rowView.findViewById(R.id.editTextClose);

        // Placer le texte pour la vue du modèle d'une rangée;
        textViewDay.setText(itemsArrayList.get(position).getDay());
        editTextOpen.setText(itemsArrayList.get(position).getTimeOpen());
        editTextClose.setText(itemsArrayList.get(position).getTimeClose());

        // Place l'étiquette de l'objet
        editTextOpen.setTag(position);
        editTextClose.setTag(position);

        // Ajout du Listener
        editTextOpen.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final int position = (int) v.getTag();
                    itemsArrayList.get(position).setTimeOpen(editTextOpen.getText().toString());
                }
            }
        });

        editTextClose.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    final int position = (int) v.getTag();
                    itemsArrayList.get(position).setTimeClose(editTextClose.getText().toString());
                }
            }
        });
        return rowView;
    }
}
