package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Alien {
    Texture texture = new Texture("alien.png");
    float x, y, w, h, vx, vy;
    Temporizador cambioVelocidad = new Temporizador(60);

    Alien() {
        x = Utils.random.nextInt(640);
        y = 480;
        w = 64;
        h = 48;
        vx = -2;
        vy = 0;
    }

    public void update() {
        y += vy;
        x += vx;

        if (cambioVelocidad.suena()) {
            vx = Utils.random.nextInt(6) - 3;
            vy = -(Utils.random.nextInt(3)+1);
        }
        if(x<0) x = 0;
        if(x > 540 + w) x = 540 + w;
        if(y > 428 + h) y = 428 + h;

    }

    void render(SpriteBatch batch) {
        batch.draw(texture, x, y, w, h);
    }
}