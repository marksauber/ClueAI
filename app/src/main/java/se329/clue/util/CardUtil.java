package se329.clue.util;

import android.util.JsonReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by davisbatten on 4/14/16.
 */
public class CardUtil {

    public CardUtil(){

        try {
            File f = new File("Cards.json");
            InputStream in = new FileInputStream(f);
            JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private void readCategory(JsonReader r) throws IOException{
        r.beginArray();
        while (r.hasNext()){
            String n = r.nextName();
            if(n.equals("id")){

            }
        }

    }


}
