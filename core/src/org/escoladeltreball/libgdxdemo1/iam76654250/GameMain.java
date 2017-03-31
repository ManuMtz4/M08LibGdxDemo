package org.escoladeltreball.libgdxdemo1.iam76654250;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import org.escoladeltreball.libgdxdemo1.iam76654250.scenes.Gameplay;
import org.escoladeltreball.libgdxdemo1.iam76654250.scenes.MainMenu;


public class GameMain extends Game {

    public static String bestPuntos;

    /*NADA MAS UN SPRITEBATCH POR JUEGO*/
    private SpriteBatch batch;

    public SpriteBatch getBatch() {
        return batch;
    }

    @Override
    public void create() {
        bestPuntos = "0";
        batch = new SpriteBatch();
        //setScreen(new MainMenu(this));
        setScreen(new Gameplay(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() { // Finaliza el juego para nop consumir recursos
        batch.dispose();
    }
}
