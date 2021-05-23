package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.Files.FileType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	BitmapFont font;
	Fondo fondo;
	Emoticonos emoticonos;
	Nave jugador;
	List<Turbo> turbos;
	List<Alien> enemigos;
	List<Disparo> disparosAEliminar;
	List<Alien> enemigosAEliminar;
	List<Turbo> turbosaeliminar;
	Temporizador temporizadorNuevoEnemigo;
	Temporizador temporizadorTurbo;
	ScoreBoard scoreboard;
	boolean gameover;
	private Music music;
	private float volume = 0.5f;
	private float volume2 = 70;

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
		turbos = new ArrayList<>();
		enemigos = new ArrayList<>();
		temporizadorNuevoEnemigo = new Temporizador(120);
		temporizadorTurbo = new Temporizador(120);
		disparosAEliminar = new ArrayList<>();
		enemigosAEliminar = new ArrayList<>();
		turbosaeliminar = new ArrayList<>();
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

					Music music = Gdx.audio.newMusic(Gdx.files.getFileHandle("impacto_disparo.mp3", Files.FileType.Internal));
					music.setVolume(volume);
					music.play();
					break;
				}
			}

			if (!gameover && !jugador.muerto && Utils.solapan(enemigo.x, enemigo.y, enemigo.w, enemigo.h, jugador.x, jugador.y, jugador.w, jugador.h)) {
				jugador.morir();
				Music music = Gdx.audio.newMusic(Gdx.files.getFileHandle("impacto_nave.mp3", Files.FileType.Internal));
				music.setVolume(volume);
				music.play();
				if (jugador.vidas == 0){
					gameover = true;
				}
			}

			if (jugador.puntos >= 10){
				temporizadorNuevoEnemigo.frecuencia = 57;
			}

			if (enemigo.y < -enemigo.w) enemigosAEliminar.add(enemigo);
		}

		if (jugador.puntos == 2){
			for (Turbo turbo : turbos) turbo.update();
			if (temporizadorNuevoEnemigo.suena()) enemigos.add(new Alien());

			for (Turbo turbo : turbos) {
				for (Disparo disparo : jugador.disparos) {

					if (Utils.solapan(disparo.x, disparo.y, disparo.w, disparo.h, turbo.x, turbo.y, turbo.w, turbo.h)) {
						disparosAEliminar.add(disparo);
						turbosaeliminar.add(turbo);
						jugador.puntos++;

						Music music = Gdx.audio.newMusic(Gdx.files.getFileHandle("impacto_disparo.mp3", Files.FileType.Internal));
						music.setVolume(volume);
						music.play();
						break;
					}
				}

				if (!gameover && !jugador.muerto && Utils.solapan(turbo.x, turbo.y, turbo.w, turbo.h, jugador.x, jugador.y, jugador.w, jugador.h)) {
					jugador.morir();
					Music music = Gdx.audio.newMusic(Gdx.files.getFileHandle("impacto_nave.mp3", Files.FileType.Internal));
					music.setVolume(volume);
					music.play();
					if (jugador.vidas == 0){
						gameover = true;
					}
				}

				if (jugador.puntos >= 10){
					temporizadorNuevoEnemigo.frecuencia = 57;
				}

				if (turbo.y < -turbo.w) turbosaeliminar.add(turbo);
			}

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
		for (Turbo turbo : turbos) turbo.render(batch);
		for (Alien enemigo : enemigos) enemigo.render(batch);  //
		font.draw(batch, "" + jugador.vidas, 590, 440);
		font.draw(batch, "" + jugador.puntos, 90, 440);

		if (gameover){
			scoreboard.render(batch, font);
		}
		batch.end();
	}
}