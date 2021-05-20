package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Fondo {

    Animacion animacion = new Animacion(3,
            new Texture("frame_01_delay-0.04s.gif"),
            new Texture("frame_02_delay-0.04s.gif"),
            new Texture("frame_03_delay-0.04s.gif"),
            new Texture("frame_04_delay-0.04s.gif"),
            new Texture("frame_05_delay-0.04s.gif"),
            new Texture("frame_06_delay-0.04s.gif"),
            new Texture("frame_07_delay-0.04s.gif"),
            new Texture("frame_08_delay-0.04s.gif"),
            new Texture("frame_09_delay-0.04s.gif"),
            new Texture("frame_10_delay-0.04s.gif"),
            new Texture("frame_11_delay-0.04s.gif"),
            new Texture("frame_12_delay-0.04s.gif"),
            new Texture("frame_13_delay-0.04s.gif"),
            new Texture("frame_14_delay-0.04s.gif"),
            new Texture("frame_15_delay-0.04s.gif"),
            new Texture("frame_16_delay-0.04s.gif"),
            new Texture("frame_17_delay-0.04s.gif"),
            new Texture("frame_18_delay-0.04s.gif"),
            new Texture("frame_19_delay-0.04s.gif"),
            new Texture("frame_20_delay-0.04s.gif"),
            new Texture("frame_21_delay-0.04s.gif"),
            new Texture("frame_22_delay-0.04s.gif"),
            new Texture("frame_23_delay-0.04s.gif")
            );


    void render(SpriteBatch batch){
        batch.draw(animacion.obtenerFrame(),0,0,640,480);
    }
}
