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
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import org.escoladeltreball.libgdxdemo1.iam76654250.GameMain;
import org.escoladeltreball.libgdxdemo1.iam76654250.cloud.CloudsController;
import org.escoladeltreball.libgdxdemo1.iam76654250.player.Player;

import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.GRAVITY;
import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.HEIGHT;
import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.H_HEIGHT;
import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.H_WIDTH;
import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.PPM;
import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.WIDTH;

/**
 * Created by Manuel Martinez.
 */

public class Gameplay implements Screen, ContactListener {

    private GameMain game;

    private Sprite[] bgs;

    private float lastYPosition;

    private SpriteBatch batch;

    private OrthographicCamera mainCamera;

    private Viewport gameViewPort;

    private OrthographicCamera box2DCamera;
    private Box2DDebugRenderer debugRenderer;

    private World world;

    private CloudsController cloudController;

    private Player player;

    private long puntos;

    public Gameplay(GameMain game) {
        this.game = game;
        this.batch = game.getBatch();

        mainCamera = new OrthographicCamera(WIDTH, HEIGHT);
        mainCamera.position.set(H_WIDTH, H_HEIGHT, 0);

        gameViewPort = new StretchViewport(WIDTH, HEIGHT, mainCamera);

        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false, WIDTH / PPM, HEIGHT / PPM);
        box2DCamera.position.set(WIDTH / 2f, HEIGHT / 2f, 0);

        debugRenderer = new Box2DDebugRenderer();

        world = new World(new Vector2(0, GRAVITY), true);
        this.world.setContactListener(this);

        cloudController = new CloudsController(world);

        player = cloudController.positionPlayer(player);

        createBackgrounds();

        this.puntos = 0;
    }

    void createBackgrounds() {
        bgs = new Sprite[3];

        for (int i = 0; i < bgs.length; i++) {
            bgs[i] = new Sprite(new Texture("GameBG.png"));
            bgs[i].setPosition(0, -(i * bgs[i].getHeight()));
            lastYPosition = Math.abs(bgs[i].getY());
        }
    }

    void drawBackgrounds() {

        for (int i = 0; i < bgs.length; i++) {
            batch.draw(bgs[i], bgs[i].getX(), bgs[i].getY());
        }

    }

    void checkBackgroundsOutOfBound() {
        for (int i = 0; i < bgs.length; i++) {
            if ((bgs[i].getY() - bgs[i].getHeight() / 2f - 15/* elimina glich rojo*/) > mainCamera.position.y) {
                float newPosition = bgs[i].getHeight() + lastYPosition;
                bgs[i].setPosition(0, -newPosition);
                lastYPosition = Math.abs(newPosition);
            }
        }
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

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        drawBackgrounds();

        cloudController.drawClouds(batch);

        player.drawPlayer(batch);

        if (player.getBody().getPosition().x * PPM > (WIDTH + (player.getWidth() / 2)) || player.getBody().getPosition().x * PPM < (0 - (player.getWidth()
                / 2)) ||
                H_HEIGHT < player.getY() - (player.getHeight() / 2) - mainCamera.position.y) {
            muere();
        }

        batch.end();

        //debugRenderer.render(world, box2DCamera.combined);

        batch.setProjectionMatrix(mainCamera.combined);

        mainCamera.update();

        player.updatePlayer();

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);


    }

    private boolean flipPlayer = false;

    private void playerControlls() {

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            if (!flipPlayer) {
                player.setFlip(true, false);
                flipPlayer = true;
            }
            player.getBody().applyLinearImpulse(new Vector2(-0.3f, 0),
                    player.getBody().getWorldCenter(), true);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            if (flipPlayer) {
                player.setFlip(false, false);
                flipPlayer = false;
            }
            player.getBody().applyLinearImpulse(new Vector2(0.3f, 0),
                    player.getBody().getWorldCenter(), true);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            // Solo hará un salto por click
            player.getBody().applyLinearImpulse(new Vector2(0, 12f),
                    player.getBody().getWorldCenter(), true);
        }
    }

    private void update(float delta) {
        moveCamera();
        checkBackgroundsOutOfBound();
        cloudController.setCameraY(mainCamera.position.y);
        cloudController.createAndArrangeNewClouds();
        playerControlls();
    }

    private void moveCamera() {
        mainCamera.position.y -= 2;
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

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
    }


    private Fixture lastBody;
    private boolean firstTime = true;

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

        if (secondBody.getUserData().equals("Dark cloud")) {
            //System.out.println("ESTAS MUERTO");
            muere();
        } else if (secondBody.getUserData().equals("Cloud") && !secondBody.equals(lastBody)) {
            lastBody = secondBody;
            if (!firstTime) {
                puntos += 1;
            } else {
                firstTime = false;
            }
            //System.out.println(puntos);
        }

        //System.out.println("el nombre del primer body es: " + firstBody.getUserData());
    }

    public void muere() {
        // Reajusto la camara
        mainCamera.position.set(H_WIDTH, H_HEIGHT, 0);
        mainCamera.update();
        batch.setProjectionMatrix(mainCamera.combined);

        game.setScreen(new Muerte(game, puntos));
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
