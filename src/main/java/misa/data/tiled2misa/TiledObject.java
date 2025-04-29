package misa.data.tiled2misa;

import java.util.Map;

/**
 * Represents an individual object defined in a Tiled map's object layer.
 *
 * <p>
 * Each TiledObject contains:
 * <ul>
 *     <li><b>id</b> - Unique identifier for the object.</li>
 *     <li><b>name</b> - Human-readable name of the object (default "Unnamed").</li>
 *     <li><b>type</b> - Category or classification of the object (default "Undefined").</li>
 *     <li><b>x</b>, <b>y</b> - Position of the object in pixels.</li>
 *     <li><b>width</b>, <b>height</b> - Size of the object in pixels.</li>
 *     <li><b>properties</b> - Custom key-value pairs attached to the object.</li>
 * </ul>
 * </p>
 */
public record TiledObject(
        int id,                  // Unique object identifier
        String name,              // Name of the object
        String type,              // Type/category of the object
        double x,                 // X-coordinate in pixels
        double y,                 // Y-coordinate in pixels
        double width,             // Width in pixels
        double height,            // Height in pixels
        Map<String, String> properties // Custom properties as key-value pairs
)
{
    /*
     * 1. `id` ensures that every object is uniquely identifiable inside the map.
     * 2. `name` and `type` allow you to label and categorize objects for gameplay logic.
     * 3. `x`, `y`, `width`, and `height` define the object's spatial position and size.
     * 4. `properties` provides customizable metadata (such as tags, behaviors, or settings).
     */
}
