package misa.test;

import misa.entities.GameObject;
import misa.core.Startable;
import misa.systems.animation.AnimationController;
import misa.systems.animation.AnimationLoader;

import java.util.Map;

public class TestPlayer extends GameObject implements Startable
{
    public float speed = 3f;
    public AnimationController playerAnimator;

    public TestPlayer(double coordinateX, double coordinateY)
    {
        super(coordinateX, coordinateY, true, true, null);
    }

    @Override
    public void start()
    {
        playerAnimator = new AnimationController(this, AnimationLoader.loadNamedAnimations(Map.of(
                "IdleNorth", new String[] {
                        "girl/Idle/North/girlIdle012.png",
                        "girl/Idle/North/girlIdle013.png",
                        "girl/Idle/North/girlIdle014.png",
                        "girl/Idle/North/girlIdle015.png"
                },

                "IdleEast", new String[] {
                        "girl/Idle/East/girlIdle008.png",
                        "girl/Idle/East/girlIdle009.png",
                        "girl/Idle/East/girlIdle010.png",
                        "girl/Idle/East/girlIdle011.png"
                },

                "IdleSouth", new String[] {
                        "girl/Idle/South/girlIdle000.png",
                        "girl/Idle/South/girlIdle001.png",
                        "girl/Idle/South/girlIdle002.png",
                        "girl/Idle/South/girlIdle003.png"
                },

                "IdleWest", new String[] {
                        "girl/Idle/West/girlIdle004.png",
                        "girl/Idle/West/girlIdle005.png",
                        "girl/Idle/West/girlIdle006.png",
                        "girl/Idle/West/girlIdle007.png"
                },

                "WalkNorth", new String[] {
                        "girl/Walk/North/girlWalk012.png",
                        "girl/Walk/North/girlWalk013.png",
                        "girl/Walk/North/girlWalk014.png",
                        "girl/Walk/North/girlWalk015.png"
                },

                "WalkEast", new String[] {
                        "girl/Walk/East/girlWalk008.png",
                        "girl/Walk/East/girlWalk009.png",
                        "girl/Walk/East/girlWalk010.png",
                        "girl/Walk/East/girlWalk011.png"
                },

                "WalkSouth", new String[] {
                        "girl/Walk/South/girlWalk000.png",
                        "girl/Walk/South/girlWalk001.png",
                        "girl/Walk/South/girlWalk002.png",
                        "girl/Walk/South/girlWalk003.png"
                },

                "WalkWest", new String[] {
                        "girl/Walk/West/girlWalk004.png",
                        "girl/Walk/West/girlWalk005.png",
                        "girl/Walk/West/girlWalk006.png",
                        "girl/Walk/West/girlWalk007.png"
                }
        )));

        playerAnimator.play("IdleSouth");
    }
}
