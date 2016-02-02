package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by bill on 1/27/16.
 */
public class JsonTest {
    private JsonValue jsonValue;
    private JsonReader jsonReader;

    public JsonTest() {
        jsonReader = new JsonReader();
        jsonValue = jsonReader.parse(Gdx.files.internal("android/assets/data/Dialogue.json"));
    }

    public void generateDialogue(String actor, int level, int dialogID){
        jsonValue.get(actor).get(level).get(dialogID).get(0).getString("phrase");
        /** This is where we would pass above to UI */
    }
}


