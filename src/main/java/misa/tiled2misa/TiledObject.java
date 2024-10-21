package misa.tiled2misa;

import java.util.Map;

/**
 * represents an object in a Tiled map's object layer
 * <p>
 * tiled maps can include object layers that contain various game objects like:
 * spawn points, interactive areas, collision boundaries, and more
 * each `TiledObject` stores information about an individual object, including:
 * its ID, name, type, position, dimensions, and any custom properties possibly assigned to it
 * <p>
 * - `id`: the unique identifier for the object within the map
 * - `name`: the name of the object, which can be used to reference it
 *      (e.g., "PlayerSpawn", "TreasureChest")
 * - `type`: the type of the object, often used to categorize or identify different kinds of objects
 *      (e.g., "Enemy", "Trigger")
 * - `x`: the x coordinate of the object’s position on the map (in pixels)
 * - `y`: the y coordinate of the object’s position on the map (in pixels)
 * - `width`: the width of the object (in pixels)
 *      this can be used to define the size of areas like collision boxes or triggers
 * - `height`: the height of the object (in pixels)
 * - `properties`: a map of custom properties that can store additional information about the object
 *      (e.g., {"health": "100"}, {"interaction": "open"})
 * <p>
 * the `TiledObject` is implemented as a Java record which means:
 * - it is immutable and providing a compact way to carry object data
 * - the fields are automatically accessible as methods
 * - the record provides default implementations for `equals()`, `hashCode()`, and `toString()`
 */
public record TiledObject(int id, String name, String type,
                          double x, double y,
                          double width, double height,
                          Map<String, String> properties) {}
