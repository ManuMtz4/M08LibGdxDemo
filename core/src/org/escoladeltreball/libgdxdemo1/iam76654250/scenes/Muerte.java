package org.escoladeltreball.libgdxdemo1.iam76654250.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.escoladeltreball.libgdxdemo1.iam76654250.GameMain;

import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.HEIGHT;
import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.H_HEIGHT;
import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.H_WIDTH;
import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.WIDTH;
import static org.escoladeltreball.libgdxdemo1.iam76654250.GameMain.bestPuntos;

/**
 * Created by Manuel Martinez.
 */

public class Muerte implements Screen {

    private GameMain game;
    private SpriteBatch batch;
    private String puntos;
    private Sprite bg;

    private Stage stage;

    private Viewport gameViewPort;

    private OrthographicCamera mainCamera;

    private Sprite restart;
    private ImageButton backBtn;

    private static BitmapFont font;

    private static float textWidthBest;
    private static float textWidth;


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

        GlyphLayout layoutBest = new GlyphLayout(font, bestPuntos);
        textWidthBest = layoutBest.width;

        GlyphLayout layout = new GlyphLayout(font, String.valueOf(puntos));
        textWidth = layout.width;

        mainCamera = new OrthographicCamera(WIDTH, HEIGHT);
        mainCamera.position.set(H_WIDTH, H_HEIGHT, 0);

        gameViewPort = new StretchViewport(WIDTH, HEIGHT, mainCamera);

        stage = new Stage(gameViewPort, batch);

        backBtn = new ImageButton(new SpriteDrawable(new Sprite(new Texture("back.png"))));
        stage.addActor(backBtn);
        backBtn.setPosition(H_WIDTH - backBtn.getWidth() / 2, H_HEIGHT - 330);

        // Damos funcionalidad al boton
        Gdx.input.setInputProcessor(backBtn.getStage());

        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                restartGame();
            }
        });

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

        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        font.draw(batch, bestPuntos, H_WIDTH - textWidthBest / 2, H_HEIGHT - 100);

        font.draw(batch, puntos, H_WIDTH - textWidth / 2, H_HEIGHT - 195);

        batch.draw(restart, H_WIDTH - restart.getWidth() / 2, H_HEIGHT - 15);

        batch.end();

        stage.act(Gdx.graphics.getDeltaTime());

        stage.draw();

    }

    private void restartGame() {
        game.setScreen(new Gameplay(game));
    }

    private void enter() {
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            restartGame();
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
