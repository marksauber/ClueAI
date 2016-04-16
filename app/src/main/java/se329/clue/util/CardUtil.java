package se329.clue.util;

import android.content.Context;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;

import java.io.Console;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by davisbatten on 4/14/16.
 */
public class CardUtil {
    private ArrayList<String> suspects;
    private ArrayList<String> weapons;
    private  ArrayList<String> rooms;

    public CardUtil(Context context){

        try {
            JsonReader reader = new JsonReader(new InputStreamReader(context.getAssets().open("Cards.json"), "UTF-8"));

            reader.beginObject();
            int id;
            //For some reason hasNext() always returns true after going through rooms array
            boolean done = false;
            while (reader.hasNext() && !done) {
                String name = reader.nextName();
                if (name.equals("id")) {
                    id = reader.nextInt();
                } else if (name.equals("suspects")) {
                    suspects = new ArrayList<String>(readCategory(reader));
                }else if (name.equals("weapons")) {
                    weapons = new ArrayList<String>(readCategory(reader));
                }else if (name.equals("rooms")) {
                    rooms = new ArrayList<String>(readCategory(reader));
                    done = true;
                }
            }

            reader.endObject();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<String> readCategory(JsonReader reader) throws IOException {
        ArrayList<String> cards = new ArrayList<String>();
        reader.beginArray();
        while (reader.hasNext()) {
            reader.beginObject();
            while (reader.hasNext()) {
                String suspectProp = reader.nextName();
                String suspectId;
                String suspectName;
                if (suspectProp.equals("id")) {
                    suspectId = reader.nextString();
                } else if (suspectProp.equals("name")) {
                    suspectName = reader.nextString();
                    cards.add(suspectName);
                }
            }
            reader.endObject();
        }
        reader.endArray();
        return cards;
    }

    public ArrayList<String> getSuspects() {
        return suspects;
    }

    public ArrayList<String> getWeapons() {
        return weapons;
    }

    public ArrayList<String> getRooms() {
        return rooms;
    }
}
