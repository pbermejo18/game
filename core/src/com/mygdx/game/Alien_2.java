package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Alien_2 {
    Texture texture = new Texture("alien2.png");
    float x, y, w, h, vx, vy;
    int vidas = 2;
    boolean muerto = false;
    Temporizador cambioVelocidad = new Temporizador(60);

    Alien_2() {
        x = Utils.random.nextInt(640);
        y = 480;
        w = 80;
        h = 100;
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
        if (vidas == 0) {
            muerto = true;
        } else {
        batch.draw(texture, x, y, w, h);
        }
    }

    public void morir() {
        vidas--;
        muerto = true;
    }
}
