package org.escoladeltreball.libgdxdemo1.iam76654250.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import org.escoladeltreball.libgdxdemo1.iam76654250.GameMain;

import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.HEIGHT;
import static org.escoladeltreball.libgdxdemo1.iam76654250.GameInfo.WIDTH;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = (int)WIDTH;
		config.height = (int)HEIGHT;
		new LwjglApplication(new GameMain(), config);
	}
}
