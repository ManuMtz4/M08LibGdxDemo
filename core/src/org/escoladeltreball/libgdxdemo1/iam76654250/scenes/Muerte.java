package org.escoladeltreball.libgdxdemo1.iam76654250.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import org.escoladeltreball.libgdxdemo1.iam76654250.GameMain;

import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.H_HEIGHT;
import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.H_WIDTH;
import static org.escoladeltreball.libgdxdemo1.iam76654250.GameMain.bestPuntos;

/**
 * Created by Manuel Martinez.
 */

public class Muerte implements Screen {

    private GameMain game;
    private SpriteBatch batch;
    private String puntos;
    private Sprite bg;

    private Sprite restart;

    BitmapFont font;


    public Muerte(GameMain game, long puntos) {
        this.puntos = String.valueOf(puntos);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font/blow.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        font = generator.generateFont(parameter);

        long bPuntos = Long.parseLong(bestPuntos);

        if (bPuntos < puntos) {
            bestPuntos = String.valueOf(puntos);
        }

        this.game = game;
        this.batch = game.getBatch();
        this.bg = new Sprite(new Texture("HighscoreBG.png"));

        this.restart = new Sprite(new Texture("StartGame.png"));

    }

    @Override
    public void show() {

    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

        enter();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(bg, 0, 0);

        GlyphLayout layoutBest = new GlyphLayout(font, bestPuntos);
        float textWidthBest = layoutBest.width;

        GlyphLayout layout = new GlyphLayout(font, puntos);
        float textWidth = layout.width;

        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        font.draw(batch, bestPuntos, H_WIDTH - textWidthBest / 2, H_HEIGHT-100);

        font.draw(batch, puntos, H_WIDTH - textWidth / 2, H_HEIGHT-195);

        batch.draw(restart, H_WIDTH - restart.getWidth() / 2, H_HEIGHT-330);

        batch.end();

    }

    private void enter() {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            game.setScreen(new Gameplay(game));
        }
    }

    /**
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
