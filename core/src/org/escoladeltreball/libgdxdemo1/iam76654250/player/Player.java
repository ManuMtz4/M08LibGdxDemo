package org.escoladeltreball.libgdxdemo1.iam76654250.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.PPM;

/**
 * Created by Manuel Martinez.
 */

public class Player extends Sprite {

    private World world;
    private Body body;

    public Player(World world, float x, float y) {
        super(new Texture("Player1.png"));
        this.world = world;
        this.setPosition(x, y);
        createBody();
    }

    void createBody() {
        BodyDef bodyDef = new BodyDef();
        // A static body isnt affected by gravity or other forces
        // A kinematic body is not affected by gravity but it is affected by other forces
        // A dynamic body is affected by gravity and other forces

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / PPM, getY() / PPM);

        body = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();

        // Player bien centrado y body recortado -10 para pegarlo más a la nube
        shape.setAsBox((getWidth() / 2f / PPM) - 0.15f, (getHeight() / 2f / PPM) - 0.08f);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 6.3f; // mass of the body
        fixtureDef.friction = 0.2f;
        //fixtureDef.restitution = 0.05f;
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Player");

        body.setFixedRotation(true);
        shape.dispose();
    }

    public void drawPlayer(SpriteBatch batch) {
        batch.draw(this, getX() - getWidth() / 2f, getY() - getHeight() / 2f);
    }

    public void updatePlayer() {
        this.setPosition(body.getPosition().x * PPM, body.getPosition().y * PPM);
    }

    public Body getBody() {
        return body;
    }

}
