package org.escoladeltreball.libgdxdemo1.iam76654250.cloud;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import org.escoladeltreball.libgdxdemo1.iam76654250.player.Player;

import java.util.Random;

import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.H_HEIGHT;
import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.H_WIDTH;

/**
 * Created by iam76654250 on 3/27/17.
 */

public class CloudsController {

    private World world;

    private Array<Cloud> clouds = new Array<Cloud>();

    private final float DISTANCE_BETWEEN_CLOUDS = 250f;
    private float minX, maxX;

    private float lastCloudPositionY;

    private float cameraY;
    private Random random = new Random();

    public CloudsController(World world) {
        this.world = world;
        minX = H_WIDTH - 150;
        maxX = H_WIDTH + 100;
        createCloud();
        positionClouds(true);
    }

    private void createCloud() {

        // 2 Dark Clouds
        for (int i = 0; i < 2; i++) {
            clouds.add(new Cloud(world, "DarkCloud.png", "Dark cloud"));
        }

        int index = 1;

        // 6 Clouds
        for (int i = 0; i < 6; i++) {
            clouds.add(new Cloud(world, "Cloud" + index + ".png", "Cloud"));
            index++;

            if (index == 4) {
                index = 1;
            }

        }

        clouds.shuffle();

    }

    public void positionClouds(boolean firstTimeArranging) {

        while (clouds.get(0).getCloudName().equals("Dark cloud")) {
            clouds.shuffle();
        }

        float positionY = 0;

        if (firstTimeArranging) {
            positionY = H_HEIGHT;
        } else {
            positionY = lastCloudPositionY;
        }

        int controlX = 0;

        for (Cloud cloud : clouds) {

            if (cloud.getX() == 0 && cloud.getY() == 0) {

                float tempX = 0;

                if (controlX == 0) {
                    tempX = randomBetwennNumbers(maxX - 50, maxX);
                    controlX = 1;
                } else if (controlX == 1) {
                    tempX = randomBetwennNumbers(minX + 50, minX);
                    controlX = 0;
                }


                cloud.setSpritePosition(tempX, positionY);
                positionY -= DISTANCE_BETWEEN_CLOUDS;
                lastCloudPositionY = positionY;
            }

        }
    }

    public void drawClouds(SpriteBatch batch) {
        for (Cloud cloud : clouds) {
            batch.draw(cloud, cloud.getX() - cloud.getWidth() / 2f , cloud.getY() - cloud.getHeight() / 2f);
        }
    }

    public void setCameraY(float cameraY) {
        this.cameraY = cameraY;
    }

    public void createAndArrangeNewClouds() {
        for (int i = 0; i < clouds.size; i++) {
            if ((clouds.get(i).getY() - H_HEIGHT - 15) > cameraY) {
                clouds.get(i).getTexture().dispose();
                clouds.removeIndex(i);
            }
        }

        if (clouds.size == 4) {
            createCloud();

            positionClouds(false);
        }

    }

    public Player positionPlayer(Player player) {
        player = new Player(world, clouds.get(0).getX(), clouds.get(0).getY() + 100);
        return player;
    }

    private float randomBetwennNumbers(float max, float min) {
        return random.nextFloat() * (max - min) + min;

    }

}
