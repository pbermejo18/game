package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class Nave {
    Animacion animacion = new Animacion(16,
            new Texture("nave.png"),
            new Texture("nave2.png"),
            new Texture("nave3.png"),
            new Texture("nave4.png")
    );

    float x, y, w, h, v;
    List<Disparo> disparos = new ArrayList<>();
    int vidas = 3;
    int puntos = 0;
    boolean muerto = false;
    Temporizador temporizadorFireRate = new Temporizador(20);
    Temporizador temporizadorRespawn = new Temporizador(120, false);

    Nave() {
        x = 100;
        y = 100;
        w = 150;
        h = 130;
        v = 5;
    }

    void update() {
        for (Disparo disparo : disparos) disparo.update();

        if (Gdx.input.isKeyPressed(Input.Keys.D)) x += v;
        if (Gdx.input.isKeyPressed(Input.Keys.A)) x -= v;
        if (Gdx.input.isKeyPressed(Input.Keys.W)) y += v;
        if (Gdx.input.isKeyPressed(Input.Keys.S)) y -= v;

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && temporizadorFireRate.suena() && !muerto) {
            disparos.add(new Disparo(x + w / 2, y + h));
        }

        if (temporizadorRespawn.suena()) {
            muerto = false;
        }

        if(x< 5 - w / 2) x = 5 - w / 2;
        if(x > 630 - w / 2) x = 630 - w / 2;
        if(y > 480 - h) y = 480 - h;
    }

    void render(SpriteBatch batch) {
        batch.draw(animacion.obtenerFrame(), x, y, w, h);
        for (Disparo disparo : disparos) disparo.render(batch);
    }

    public void morir() {
        vidas--;
        muerto = true;
        temporizadorRespawn.activar();
    }
}