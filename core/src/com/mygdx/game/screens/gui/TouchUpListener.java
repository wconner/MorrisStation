/**
 * Copyright (c) 2013 matheusdev
 * <p>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or
 * sell copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p>
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * <p>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.mygdx.game.screens.gui;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;

/**
 * Created with IntelliJ IDEA.
 * Author: matheusdev
 * Date: 4/29/13
 * Time: 7:58 PM
 * <p>
 * I've finally figured out what this class does.  The way Touchup works you need to have TouchDown set to true in order
 * for it to activate.  This will return true allowing the button to give the 'depressed' look, and allows TouchUp to active
 * Note: Even though this class is called TouchUpListener, it overrides touchDOWN  -Bill
 */

public abstract class TouchUpListener extends InputListener {
    @Override
    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
        return true;
    }
}
