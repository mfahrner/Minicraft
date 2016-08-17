package com.theironyard;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;

import static com.badlogic.gdx.math.MathUtils.random;
import static com.badlogic.gdx.math.MathUtils.randomSign;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	SpriteBatch zombie;
	SpriteBatch tree;
	TextureRegion left, right, up, down, zombieDown, zombieUp, zombieRight, zombieLeft, treeTop, treeBottom;
	float x, y, xv, yv, a, b, r, xt, yt;
	static final int WIDTH = 16;
	static final int HEIGHT = 16;

	static final int DRAW_WIDTH = WIDTH*3;
	static final int DRAW_HEIGHT = HEIGHT*3;

	static final float MAX_VELOCITY = 250;

	boolean faceUp;
	boolean faceDown;
	boolean faceLeft;
	boolean faceRight;


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
		zombie = new SpriteBatch();
		zombieDown = grid[6][4];
		tree = new SpriteBatch();
		treeTop = grid[0][0];
		treeBottom = grid [1][0];

	}

	@Override
	public void render () {

		move();

		setBoundry();

		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();

		batch.draw(zombieDown, (a + 250), (b + 250), DRAW_WIDTH, DRAW_HEIGHT);

		// I couldn't isolate the tree, so you got some bonus scenery
		// my guess is I would have had to change the split numbers
		// but I spent to much time on the zombie and don't have enough time to fiddle with it
		batch.draw(treeTop, (xt + 400), (yt + 348), DRAW_WIDTH, DRAW_HEIGHT);
		batch.draw(treeBottom, (xt + 400), (yt + 300), DRAW_WIDTH, DRAW_HEIGHT);

		batch.draw(right, x, y, DRAW_WIDTH, DRAW_HEIGHT);

		setFace();

		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	float decelerate(float velocity) {
		float deceleration = 0.95f;
		velocity *= deceleration;
		if (Math.abs(velocity) < 1) {
			velocity = 0;
		}
		return velocity;
	}

	void move() {
		// zombie movement I gave it a go I dont know if that's what you wanted
		// I originally linked it to the hero movement
		// also I couldn't get him to slow down I suspect it is because move method is in render code which refreshes 60 times a sec
		r = (randomSign() * random(-100,100));
		a = randomSign() * r;
		b = randomSign() * r;

		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			faceUp = true;
			faceDown = false;
			faceRight = false;
			faceLeft = false;
			y++;

			if (Gdx.input.isKeyPressed(Input.Keys.UP) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
				yv = MAX_VELOCITY;
			}
		}

		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			faceUp = false;
			faceDown = true;
			faceRight = false;
			faceLeft = false;
			y--;

			if (Gdx.input.isKeyPressed(Input.Keys.DOWN) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
				yv = MAX_VELOCITY * -1;
			}
		}

		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			faceUp = false;
			faceDown = false;
			faceRight = true;
			faceLeft = false;
			x++;

			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) && (Gdx.input.isKeyPressed(Input.Keys.SPACE))) {
				xv = MAX_VELOCITY;
			}
		}

		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			faceUp = false;
			faceDown = false;
			faceRight = false;
			faceLeft = true;
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

	void setFace() {
		if (faceUp) {
			batch.draw(up, x, y, DRAW_WIDTH, DRAW_HEIGHT);

		}else if (faceDown) {
			batch.draw(down, x, y, DRAW_WIDTH, DRAW_HEIGHT);

		}else if (faceRight) {
			batch.draw(right, x, y, DRAW_WIDTH, DRAW_HEIGHT);

		}else if (faceLeft) {
			batch.draw(left, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
	}

	void setBoundry() {
		if (y > Gdx.graphics.getHeight()) {
			y = 0;
		}

		if (y < -DRAW_HEIGHT) {
			y = Gdx.graphics.getHeight();
		}

		if (x > Gdx.graphics.getWidth()) {
			x = 0;
		}

		if (x < -DRAW_WIDTH) {
			x = Gdx.graphics.getWidth();
		}
	}
}
