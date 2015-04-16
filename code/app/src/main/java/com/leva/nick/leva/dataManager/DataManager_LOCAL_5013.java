package com.leva.nick.leva.dataManager;


import android.content.Context;
import android.util.Log;

import com.leva.nick.leva.openingHours.ItemOpeningHours;
import com.leva.nick.leva.promotions.ItemPromotion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by davstpierre on 15-04-06.
 */
public class DataManager {

    public static void writeOpeningHours(Context context, ArrayList<ItemOpeningHours> listToWrite) {
        String filename = context.getFilesDir().getPath().toString() + "/OpeningHours.txt";

        try {
            FileWriter fw = new FileWriter(filename);
            Writer output = new BufferedWriter(fw);

            for (int i = 0 ; i < listToWrite.size() ; i++) {
                output.write(listToWrite.get(i).getDay().toString() + "\n");
                output.write(listToWrite.get(i).getTimeOpen().toString() + "\n");
                output.write(listToWrite.get(i).getTimeClose().toString() + "\n");
            }

            output.close();
            Log.d("IMA", " Fichier écrit");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ItemOpeningHours> readOpeningHours(Context context) {

        ArrayList<ItemOpeningHours> readList = new ArrayList<ItemOpeningHours>();
        String filename = context.getFilesDir().getPath().toString() + "/OpeningHours.txt";

        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            if (!input.ready()) {
                throw new IOException();
            }

            for (int i = 0 ; i < 7 ; i++) {
                readList.add(new ItemOpeningHours(input.readLine(), input.readLine(), input.readLine()));
            }

            Log.d("IMA", " Fichier lu ");
            return readList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writePromotions(Context context, ArrayList<ItemPromotion> listToWrite) {
        ArrayList<ItemOpeningHours> temp = new ArrayList<ItemOpeningHours>();
        String filename = context.getFilesDir().getPath().toString() + "/Promotions.txt";

        try {
            FileWriter fw = new FileWriter(filename);
            Writer output = new BufferedWriter(fw);

            for (int i = 0 ; i < listToWrite.size() ; i++) {
                output.write(listToWrite.get(i).getDay().toString() + "\n");
                output.write(listToWrite.get(i).getPromotionName().toString() + "\n");
                output.write(listToWrite.get(i).getPromotionDescription().toString() + "\n");
                output.write(listToWrite.get(i).getTimeStart().toString() + "\n");
                output.write(listToWrite.get(i).getTimeEnd().toString() + "\n");
                output.write(listToWrite.get(i).getImage().toString() + "\n");
            }

            output.close();
            Log.d("IMA", " Fichier écrit");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<ItemPromotion> readPromotions(Context context) {

        ArrayList<ItemPromotion> readList = new ArrayList<ItemPromotion>();
        String filename = context.getFilesDir().getPath().toString() + "/Promotions.txt";

        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            if (!input.ready()) {
                throw new IOException();
            }

            for (int i = 0 ; i < 7 ; i++) {
                readList.add(new ItemPromotion(input.readLine(), input.readLine(), input.readLine(),
                        input.readLine(), input.readLine(), input.readLine()));
            }

            Log.d("IMA", " Fichier lu ");
            return readList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void writeEntrepriseAccountDetails(Context context, ArrayList<String> accountDetails,
                                                     String username) {
        String filename = context.getFilesDir().getPath().toString() + "/AccountDetails" + username + ".txt";

        try {
            FileWriter fw = new FileWriter(filename);
            Writer output = new BufferedWriter(fw);

            for (int i = 0 ; i < accountDetails.size() ; i++) {
                output.write(accountDetails.get(i).toString() + "\n");
            }

            output.close();
            Log.d("IMA", " Fichier écrit");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readEntrepriseAccountDetails(Context context, String username) {

        ArrayList<String> readList = new ArrayList<String>();
        String filename = context.getFilesDir().getPath().toString() + "/AccountDetails" + username + ".txt";

        try {
            BufferedReader input = new BufferedReader(new FileReader(filename));
            if (!input.ready()) {
                throw new IOException();
            }

            for (int i = 0 ; i < 8; i++) {
                readList.add(input.readLine());
            }

            Log.d("IMA", " Fichier lu ");
            return readList;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}