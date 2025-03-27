package misa.test;

import misa.core.GameLoop;
import misa.core.GameWindow;
import misa.entities.Player;

@SuppressWarnings("unused")
public class TestGame
{
    public static void main(String[] args)
    {
        GameLoop gameLoop = GameLoop.createDefault();

        Player player = gameLoop.createDefaultPlayer();

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
