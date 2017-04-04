package org.escoladeltreball.libgdxdemo1.iam76654250.cloud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
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

public class Cloud extends Sprite {

    private World world;
    private Body body;
    private String cloudName;

    public Cloud(World world, String name, String cloudName) {
        super(new Texture(name));
        this.world = world;
        this.cloudName = cloudName;
    }

    public String getCloudName() {
        return cloudName;
    }

    public void setSpritePosition(float x, float y) {
        setPosition(x, y);
        createBody();
    }

    void createBody() {
        BodyDef bodyDef = new BodyDef();
        // A static body isnt affected by gravity or other forces
        // A kinematic body is not affected by gravity but it is affected by other forces
        // A dynamic body is affected by gravity and other forces
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(getX() / PPM, getY() / PPM);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();

        // Ajusto el body de las imagenes
        shape.setAsBox((getWidth() / 2f / PPM) - 0.2f, (getHeight() / 2f / PPM) -0.1f);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(cloudName);
        fixture.setSensor(false); // para atravesar

        shape.dispose();
    }

}
