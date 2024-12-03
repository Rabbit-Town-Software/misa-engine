package misa.tiled2misa;

import java.util.Map;

/**
 * Represents an object in a Tiled map's object layer.
 * Stores essential information about the object, including:
 * - `id`: Unique identifier for the object.
 * - `name`: The name of the object (default is "Unnamed").
 * - `type`: The type/category of the object (default is "Undefined").
 * - `x`, `y`: Position of the object on the map in pixels.
 * - `width`, `height`: Dimensions of the object in pixels.
 * - `properties`: A map of custom properties (key-value pairs) associated with the object.
 */
public record TiledObject(int id, String name, String type,
                          double x, double y,
                          double width, double height,
                          Map<String, String> properties) {}
