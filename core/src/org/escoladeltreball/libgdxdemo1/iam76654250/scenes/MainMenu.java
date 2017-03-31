package org.escoladeltreball.libgdxdemo1.iam76654250.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;

import org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo;
import org.escoladeltreball.libgdxdemo1.iam76654250.GameMain;
import org.escoladeltreball.libgdxdemo1.iam76654250.cloud.Cloud;
import org.escoladeltreball.libgdxdemo1.iam76654250.player.Player;

import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.H_HEIGHT;
import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.H_WIDTH;
import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.PPM;


/**
 * Created by iam76654250 on 3/13/17.
 */

public class MainMenu implements Screen, ContactListener {

    private GameMain game;

    private Texture bg;
    private Player player;
    private SpriteBatch batch;
    private World world;

    private OrthographicCamera box2DCamera;
    private Box2DDebugRenderer debugRender;

    private Cloud cloud;

    public MainMenu(GameMain game) {
        this.game = game;

        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false, GameInfo.WIDTH / PPM, GameInfo.HEIGHT / PPM);

        box2DCamera.position.set(GameInfo.H_WIDTH, GameInfo.H_HEIGHT, 0);

        debugRender = new Box2DDebugRenderer();

        this.world = new World(new Vector2(0, -9.8f), true);

        this.world.setContactListener(this);

        this.batch = game.getBatch();
        this.bg = new Texture("GameBG.png");
        this.player = new Player(
                this.world,
                H_WIDTH,
                H_HEIGHT + 250
        );

        cloud = new Cloud(this.world,
                "Cloud1.png", "cloud");

        cloud.setSpritePosition(H_WIDTH, H_HEIGHT);
    }

    @Override
    public void show() {

    }

    void update(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {

            // IMPULSO
            player.getBody().applyLinearImpulse(new Vector2(-0.1f, 0),
                    player.getBody().getWorldCenter(), true);

            // FUERZA
            /*player.getBody().applyForce(new Vector2(-5f, 0),
                    player.getBody().getWorldCenter(), true);*/

        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.getBody().applyLinearImpulse(new Vector2(0.1f, 0),
                    player.getBody().getWorldCenter(), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            player.getBody().applyLinearImpulse(new Vector2(0, 0.6f),
                    player.getBody().getWorldCenter(), true);
        }
    }

    @Override
    public void render(float delta) {

        update(delta);

        player.updatePlayer();

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(bg, 0, 0);
        batch.draw(player, player.getX(), player.getY() - player.getHeight() / 2);

        batch.draw(cloud, cloud.getX(), cloud.getY() - cloud.getHeight() / 2);

        batch.end();

        debugRender.render(world, box2DCamera.combined);

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }


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
        bg.dispose();
        player.getTexture().dispose();
    }

    /**
     * Called when two fixtures begin to touch.
     *
     * @param contact
     */
    @Override
    public void beginContact(Contact contact) {

        Fixture firstBody, secondBody;

        if (contact.getFixtureA().getUserData().equals("Player")) {
            // Setting the fixture A the player is the first body
            firstBody = contact.getFixtureA();
            secondBody = contact.getFixtureB();
        } else {
            firstBody = contact.getFixtureB();
            secondBody = contact.getFixtureA();
        }

        System.out.println("el nombre del primer body es: " + firstBody.getUserData());

    }

    /**
     * Called when two fixtures cease to touch.
     *
     * @param contact
     */
    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
