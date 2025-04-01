package misa.test;

import misa.core.GameLoop;
import misa.core.GameWindow;
import misa.core.Renderer;
import misa.entities.Player;

import misa.data.tiled2misa.TiledParser;
import misa.data.tiled2misa.TiledMap;

import java.io.InputStream;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class TestGame
{
    public static void main(String[] args)
    {
        GameLoop gameLoop = GameLoop.createDefault();

        Player player = gameLoop.createDefaultPlayer();

        TiledParser tiledParser = new TiledParser(new ArrayList<>());

        tiledParser.setResourceBasePath("test_assets/maps/");

        InputStream stream = TestGame.class.getClassLoader().getResourceAsStream("test_assets/maps/testmap.tmx");
        TiledMap tiledMap = tiledParser.loadFromInputStream(stream);


        gameLoop.getRenderer().setTiledMap(tiledMap);

        player.setAnimation(player.loadAnimations(new String[]
                {
                        "test_assets/girlidle/idleSouth/girlIdle000.png",
                        "test_assets/girlidle/idleSouth/girlIdle001.png",
                        "test_assets/girlidle/idleSouth/girlIdle002.png",
                        "test_assets/girlidle/idleSouth/girlIdle003.png",
                }));

        GameWindow.launchGame(gameLoop);
    }
}
