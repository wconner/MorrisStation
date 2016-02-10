package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

/**
 * Created by bill on 1/27/16.
 */
public class JsonTest {
    private JsonValue jsonValue;
    private JsonReader jsonReader;

    ArrayList<String> dialogOptions;
    String actor;
    int level, dialogID;

    public JsonTest() {
        jsonReader = new JsonReader();
        jsonValue = jsonReader.parse(Gdx.files.internal("android/assets/data/Dialogue.json"));
        dialogOptions = new ArrayList<>();
    }

    public void setDialog(String actor, int level, int dialogID){
        this.actor = actor; this.level = level; this.dialogID = dialogID;
    }

    public String getDialog(){ return jsonValue.get(actor).get(level).get(dialogID).getString("phrase");}

    public ArrayList getDialogOptions(){
        dialogOptions.clear();
        for (int i = 0; i < 3; i++)
            dialogOptions.add(jsonValue.get(actor).get(level).get(dialogID).get("options").get(i).getString("phrase"));

        return dialogOptions;
    }

    public int getUpdatedDialogID(int optionSelected){ return jsonValue.get(actor).get(level).get(dialogID).get("options").get(optionSelected - 1).getInt("nextDialogue");}
}


