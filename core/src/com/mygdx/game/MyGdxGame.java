package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont font;
	Fondo fondo;
	Emoticonos emoticonos;
	Nave jugador;
	List<Alien> enemigos;
	List<Disparo> disparosAEliminar;
	List<Alien> enemigosAEliminar;
	Temporizador temporizadorNuevoEnemigo;
	ScoreBoard scoreboard;
	boolean gameover;


	@Override
	public void create() {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		font.getData().setScale(2f);

		inicializarJuego();
	}

	void inicializarJuego(){
		fondo = new Fondo();
		jugador = new Nave();
		emoticonos = new Emoticonos();
		enemigos = new ArrayList<>();
		temporizadorNuevoEnemigo = new Temporizador(120);
		disparosAEliminar = new ArrayList<>();
		enemigosAEliminar = new ArrayList<>();
		scoreboard = new ScoreBoard();

		gameover = false;
	}

	void update() {
		Temporizador.framesJuego += 1;

		if (temporizadorNuevoEnemigo.suena()) enemigos.add(new Alien());

		if(!gameover) jugador.update();

		for (Alien enemigo : enemigos) enemigo.update();

		for (Alien enemigo : enemigos) {
			for (Disparo disparo : jugador.disparos) {
				if (Utils.solapan(disparo.x, disparo.y, disparo.w, disparo.h, enemigo.x, enemigo.y, enemigo.w, enemigo.h)) {
					disparosAEliminar.add(disparo);
					enemigosAEliminar.add(enemigo);
					jugador.puntos++;
					break;
				}
			}

			if (!gameover && !jugador.muerto && Utils.solapan(enemigo.x, enemigo.y, enemigo.w, enemigo.h, jugador.x, jugador.y, jugador.w, jugador.h)) {
				jugador.morir();
				if (jugador.vidas == 0){
					gameover = true;
				}
			}

			if (jugador.puntos >= 10){
				temporizadorNuevoEnemigo.frecuencia = 57;
			}

			if (enemigo.y < -enemigo.w) enemigosAEliminar.add(enemigo);
		}

		for (Disparo disparo : jugador.disparos)
			if (disparo.x > 640)
				disparosAEliminar.add(disparo);

		for (Disparo disparo : disparosAEliminar) jugador.disparos.remove(disparo);
		for (Alien enemigo : enemigosAEliminar) enemigos.remove(enemigo);
		disparosAEliminar.clear();
		enemigosAEliminar.clear();

		if(gameover) {
			int result = scoreboard.update(jugador.puntos);
			if(result == 1) {
				inicializarJuego();
			} else if (result == 2) {
				Gdx.app.exit();
			}
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		update();

		batch.begin();
		fondo.render(batch);
		jugador.render(batch);
		emoticonos.render(batch);
		for (Alien enemigo : enemigos) enemigo.render(batch);  //
		font.draw(batch, "" + jugador.vidas, 590, 440);
		font.draw(batch, "" + jugador.puntos, 90, 440);

		if (gameover){
			scoreboard.render(batch, font);
		}
		batch.end();
	}
}