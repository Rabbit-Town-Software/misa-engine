package misa.data.tiled2misa;

import java.util.Map;

/**
 * Represents an object in a Tiled map's object layer.
 * <p>
 * - `id`: Unique identifier for the object.
 * - `name`: Human-readable name of the object (default is "Unnamed").
 * - `type`: Category or type of the object (default is "Undefined").
 * - `x`, `y`: Position of the object in pixels.
 * - `width`, `height`: Dimensions of the object in pixels.
 * - `properties`: Custom properties associated with the object as key-value pairs.
 */
public record TiledObject(
        int id,                  // Unique object identifier
        String name,             // Name of the object
        String type,             // Type/category of the object
        double x,                // X-coordinate in pixels
        double y,                // Y-coordinate in pixels
        double width,            // Width in pixels
        double height,           // Height in pixels
        Map<String, String> properties // Custom properties as key-value pairs
)
{
    /*
      1. `id` ensures each object is uniquely identifiable.
      2. `name` and `type` provide metadata for understanding the object's role.
      3. `x`, `y`, `width`, `height` define the object's spatial attributes.
      4. `properties` stores additional metadata or configuration values for the object.
     */
}
