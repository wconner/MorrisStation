package com.mygdx.game.screens.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;

/**
 * Created by Ian on 1/21/2015.
 */
public class DialogWindow {

    /**
     * For building a window and assigning it a skin
     */
    private Window window;
    private Skin skin;
    private TextureAtlas atlas;

    /**
     * Text labels
     */
    private Label label, contentLabel;

    private boolean hidden;

    private Table root;

    /**
     * Default constructor
     */
    public DialogWindow() {
        atlas = new TextureAtlas("ui_skin/uiskin.atlas");
        skin = new Skin(Gdx.files.local("ui_skin/uiskin.json"), atlas);
        skin.addRegions(atlas);
        hidden = true;
    }

    /**
     * Build a basic window
     */
    public Window makeWindow() {

        window = new Window("Player", skin);
        window.row().fill().expandX();
        window.setMovable(false);
        window.setHeight(125);
        window.setWidth(1200);

        label = new Label("", skin);
        label.setWrap(true);

        window.row().prefWidth(Gdx.graphics.getWidth() * 0.98f);
        contentLabel = new Label("", skin);
        contentLabel.setWrap(true);
        contentLabel.setFontScale(1.5f);

        window.add(contentLabel).padLeft(-50).padRight(50).expandX().padTop(-50).padBottom(-50);

        return window;
    }

    /**
     * Sets window to hidden
     */
    public void hide() {
        window.setVisible(false);
    }

    /**
     * Sets window to visible
     */
    public void show() {
        window.setVisible(true);
    }

    /**
     * Getter for window
     */
    public Window getWindow() {
        return window;
    }

    /**
     * Set the text of the only label on the page
     * TODO introduce multiple labels with variable input streams
     */
    public void setText(String text) {
        contentLabel.setText(text);
    }

    /**
     * stage.act() calls the act method on all widgets of the stage
     */
    public void update(Stage stage) {
        stage.act();
        stage.draw();
    }

    private Label label(String text) {
        final Label label = new Label("", skin);
        label.setText(text);

        Table table = new Table();
        table.add(label).width(35).space(12);

        root.add(table).fill().row();
        return label;
    }
}