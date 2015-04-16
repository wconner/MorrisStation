package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.mygdx.game.entities.AbstractDynamicObject;
import com.mygdx.game.util.Constants;

/**
 * Created by Ian on 2/5/2015.
 */
public class Background extends AbstractDynamicObject {


    private TextureRegion background;

    private int length;

    public Background(int id, int length) {
        super(id);
        this.length = length;
        init();
    }

    private void init() {
        dimension.set(2, 2);
        background = Assets.instance.decoration.background;

        // shift mountain left
        origin.x = -dimension.x * 2;

        //extend length
        //length += dimension.x * 2;
    }

    private void drawBackgroundTwo(SpriteBatch batch, float offsetX,
                                   float offsetY, float tintColor){

        Texture reg = Assets.instance.back.back;

        batch.setColor(tintColor, tintColor, tintColor, 1);

        reg.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        //sourceX += 10;

        //Simply draw it using that variable in the srcX.

        batch.draw(reg, 0, 0, 0, 0, (int) Constants.VIEWPORT_WIDTH, (int) Constants.VIEWPORT_HEIGHT);

        //TiledDrawable tile = new TiledDrawable(reg);
        //tile.draw(batch, 0, 0, 10, 10);

    }


    private void drawBackground(SpriteBatch batch, float offsetX,
                                float offsetY, float tintColor) {
        TextureRegion reg = null;
        batch.setColor(tintColor, tintColor, tintColor, 1);
        float xRel = dimension.x * offsetX;
        float yRel = dimension.y * offsetY;

        //background spans the whole level
        int mountainLength = 0;
        mountainLength += MathUtils.ceil(length / (2 * dimension.x));
        mountainLength += MathUtils.ceil(0.5f + offsetX);
        for (int i = 0; i < mountainLength; i++) {

            // mountain left
            reg = background;

            batch.draw(reg.getTexture(),
                    origin.x + xRel, position.y + origin.y + yRel,
                    origin.x, origin.y,
                    dimension.x, dimension.y,
                    scale.x, scale.y,
                    rotation,
                    reg.getRegionX(), reg.getRegionY(),
                    reg.getRegionWidth(), reg.getRegionHeight(),
                    false, false);
            xRel += dimension.x;
        }

        // reset color to white
        batch.setColor(0, 0, 0, 0);
    }


    @Override
    public void render (SpriteBatch batch){
        // distant background (dark gray)
        drawBackgroundTwo(batch, 1f, 1f, 1f);
    }
}
