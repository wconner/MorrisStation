package com.mygdx.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.JsonValue;

/**
 * Created by bill on 1/27/16.
 */
public class JsonTest {

    com.badlogic.gdx.utils.JsonReader jsonReader = new com.badlogic.gdx.utils.JsonReader();
    JsonValue root = jsonReader.parse(Gdx.files.internal("android/assets/data/jsonTest.json"));
    int id = root.get("person").get("id").asInt();
    String EC = root.get("person").get("emergencyContacts").get(0).get("name").asString();
    int b = 3;
//    FileHandle file = Gdx.files.internal("android/assets/data/jsonTest.json");
//    String scores = file.readString();
//    Json json = new Json();
//    int id = json.fromJson()
////    PlayerScore score = json.fromJson(PlayerScore.class, scores);
}


