package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import java.util.ArrayList;

/**
 * Created by bill on 1/27/16.
 */
public class JsonParser {
    private JsonValue jsonValue;

    ArrayList<String> dialogOptions, otherOptions;
    String actor, level;
    int dialogID;

    public JsonParser() {
        JsonReader jsonReader = new JsonReader();
        jsonValue = jsonReader.parse(Gdx.files.internal("data/Dialogue.json"));
        dialogOptions = new ArrayList<>();
    }

    public JsonParser(String path) {
        JsonReader jsonReader = new JsonReader();
        jsonValue = jsonReader.parse(Gdx.files.internal(path));
        otherOptions = new ArrayList<>();
    }

    //@TODO just pass in the NPC being talked to and then get the name/level/dialogID from that NPC.
    /**
     * This is called before entering dialog to tell JsonParser where it should be looking for the dialog.
     *
     * @param actor    The name of the NPC being talked to.
     * @param level    The level (scene) where the NPC is being talked to.
     * @param dialogID The dialogID (where in the conversation) of the NPC.
     */
    public void setDialog(String actor, String level, int dialogID) {
        this.actor = actor;
        this.level = level;
        this.dialogID = dialogID;
    }

    /**
     * Gets the Dialog for dialogWindow.
     *
     * @return The dialog
     */
    public String getDialog() {
        return jsonValue.get(actor).get(level).get(dialogID).getString("phrase");
    }

    /**
     * Gets the dialog options for dialogButtons.
     *
     * @return the dialog options.
     */
    public ArrayList getDialogOptions() {
        dialogOptions.clear();
        for (int i = 0; i < jsonValue.get(actor).get(level).get(dialogID).get("options").size; i++)
            dialogOptions.add(jsonValue.get(actor).get(level).get(dialogID).get("options").get(i).getString("phrase"));
        return dialogOptions;
    }

    /**
     * Gets the next dialogID based on which option was chosen by the player, this value is then updated in the NPC.
     *
     * @param optionSelected which option was selected.
     * @return the updated dialogID for the NPC.
     */
    public int getUpdatedDialogID(int optionSelected) {
        return jsonValue.get(actor).get(level).get(dialogID).get("options").get(optionSelected - 1).getInt("nextDialogue");
    }

    public String getItemField(int itemID, String field) {
        return jsonValue.get(itemID).getString(field);
    }

    public String getItemField(String itemID, String field) {
        return jsonValue.get(itemID).getString(field);
    }
}
