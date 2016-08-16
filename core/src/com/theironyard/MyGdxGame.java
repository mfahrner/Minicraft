package com.theironyard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	TextureRegion left, right, up, down;
	float x, y, xv, yv;
	static final int WIDTH = 16;
	static final int HEIGHT = 16;

	static final int DRAW_WIDTH = WIDTH*3;
	static final int DRAW_HEIGHT = HEIGHT*3;

	static final float MAX_VELOCITY = 250;


	@Override
	public void create () {
		batch = new SpriteBatch();
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
		down = grid[6][0];
		up = grid[6][1];
		right = grid[6][3];
		left = new TextureRegion(right);
		left.flip(true, false);


	}

	@Override
	public void render () {

		move();
		if (y > 475) {
			y = 0;
		}
		if (y < -45) {
			y = 430;
		}
		if (x > 630) {
			x = 0;
		}
		if (x < -40) {
			x = 600;
		}

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(right, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			batch.draw(up, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			batch.draw(down, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			batch.draw(right, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			batch.draw(left, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
			batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	float decelerate(float velocity) {
		float deceleration = 0.95f; // the closer to 1, the slower the deceleration
		velocity *= deceleration;
		if (Math.abs(velocity) < 1) {
			velocity = 0;
		}
		return velocity;
	}

	void move() {
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			y++;
			if (Gdx.input.isKeyPressed(Input.Keys.UP) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
				yv = MAX_VELOCITY;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			y--;
			if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
				yv = MAX_VELOCITY * -1;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			x++;
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
				xv = MAX_VELOCITY;
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			x--;
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
				xv = MAX_VELOCITY * -1;
			}
		}

		y += yv * Gdx.graphics.getDeltaTime();
		x += xv * Gdx.graphics.getDeltaTime();

		yv = decelerate(yv);
		xv = decelerate(xv);
	}
}
