package se329.clue.util;

import android.content.Context;
import android.util.JsonReader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by davisbatten on 4/14/16.
 */
public class CardUtil {
    private ArrayList<String> suspects;
    private ArrayList<String> weapons;
    private ArrayList<String> rooms;
    private HashMap<String,Integer> cardToId;
    public CardUtil(Context context){

        try {
            JsonReader reader = new JsonReader(new InputStreamReader(context.getAssets().open("Cards.json"), "UTF-8"));
            cardToId = new HashMap<String,Integer>();
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
            String id = "" ;
            while (reader.hasNext()) {
                String prop = reader.nextName();
                String name;
                if (prop.equals("id")) {
                    id = reader.nextString();
                } else if (prop.equals("name")) {
                    name = reader.nextString();
                    cards.add(name);
                    cardToId.put(name,Integer.parseInt(id));
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
    public HashMap<String,Integer> getCardToId(){return cardToId;}

}
