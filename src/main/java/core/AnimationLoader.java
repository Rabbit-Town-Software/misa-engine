package core;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class AnimationLoader
{

    // Returns an array of images which can then be used in the animator
    public static BufferedImage[] loadAnimations(String[] paths)
    {
        List<BufferedImage> images = new ArrayList<>();

        for (String path : paths)
        {
            try (InputStream is = AnimationLoader.class.getClassLoader().getResourceAsStream(path))
            {
                if (is == null)
                {
                    System.err.println("Image not found: " + path);
                    continue;
                }
                BufferedImage image = ImageIO.read(is);
                images.add(image);
                System.out.println("Image loaded: " + path);
            }
            catch (IOException e)
            {
                System.out.println("Something went wrong while loading image: " + path);
            }
        }
        return images.toArray(new BufferedImage[0]);
    }
}
