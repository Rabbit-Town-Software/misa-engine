package misa.data.tiled2misa;

import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TiledParser handles reading and parsing TMX map files exported from the Tiled map editor.
 *
 * <p>
 * It supports parsing maps from both filesystem files and classpath InputStreams,
 * and can handle multiple data encodings (CSV, Base64).
 * </p>
 */
public class TiledParser
{
    private static final Logger LOGGER = Logger.getLogger(TiledParser.class.getName());

    private String resourceBasePath = "";
    private final List<TiledTileset> tilesets;

    /**
     * Creates a new TiledParser.
     *
     * @param tilesets An optional list of manually preloaded tilesets. Can be empty.
     */
    public TiledParser(List<TiledTileset> tilesets)
    {
        this.tilesets = tilesets;
    }

    /**
     * Sets a base path used to resolve relative tileset image or TSX paths.
     *
     * @param basePath Base resource path (should end with "/").
     */
    public void setResourceBasePath(String basePath)
    {
        if (!basePath.endsWith("/"))
        {
            basePath += "/";
        }
        this.resourceBasePath = basePath;
        LOGGER.info("Resource base path set to: " + this.resourceBasePath);
    }

    /**
     * Loads and parses a TMX map file from the filesystem.
     *
     * @param filePath Path to the TMX file.
     * @return A constructed TiledMap, or null if loading failed.
     */
    @SuppressWarnings("unused")
    public TiledMap loadFromTMX(String filePath)
    {
        LOGGER.info("Attempting to load TMX file: " + filePath);

        try
        {
            File xmlFile = new File(filePath);

            // Check if file exists before parsing
            if (!xmlFile.exists())
            {
                LOGGER.severe("TMX file not found: " + filePath);
                return null;
            }

            Document document = parseXMLFile(xmlFile);
            LOGGER.info("TMX file parsed successfully.");
            return createTiledMapFromDocument(document);
        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Exception while loading TMX file: " + filePath, e);
            return null;
        }
    }

    /**
     * Loads and parses a TMX map from an InputStream (e.g., from inside a JAR).
     *
     * @param inputStream Stream containing TMX data.
     * @return A constructed TiledMap, or null if loading failed.
     */
    public TiledMap loadFromInputStream(InputStream inputStream)
    {
        try
        {
            Document document = parseXMLStream(inputStream);
            LOGGER.info("Successfully parsed TMX data from InputStream.");
            return createTiledMapFromDocument(document);
        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Failed to load map from InputStream.", e);
            return null;
        }
    }

    // --- Low-Level XML Parsing Methods ---

    private Document parseXMLFile(File file) throws Exception
    {
        // Parse XML from a file on disk
        LOGGER.fine("Parsing XML from file: " + file.getAbsolutePath());
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(file);
        document.getDocumentElement().normalize();
        return document;
    }

    private Document parseXMLStream(InputStream inputStream) throws Exception
    {
        // Parse XML from an InputStream (e.g., JAR resource)
        LOGGER.fine("Parsing XML from InputStream...");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);
        document.getDocumentElement().normalize();
        LOGGER.info("XML parsing completed.");
        return document;
    }

    // --- Map Creation Methods ---

    private TiledMap createTiledMapFromDocument(Document document)
    {
        // Root <map> element
        Element mapElement = document.getDocumentElement();
        LOGGER.info("Root element: " + mapElement.getTagName());

        // Read basic map properties
        int mapWidth = getIntAttribute(mapElement, "width");
        int mapHeight = getIntAttribute(mapElement, "height");
        int tileWidth = getIntAttribute(mapElement, "tilewidth");
        int tileHeight = getIntAttribute(mapElement, "tileheight");

        LOGGER.info(String.format(
                "Map attributes — Size: %dx%d | Tile: %dx%d",
                mapWidth,
                mapHeight,
                tileWidth,
                tileHeight
        ));

        // Parse layers, tilesets, and object layers
        List<TiledTileset> parsedTilesets = parseTilesets(document);
        List<TiledLayer> parsedLayers = parseLayers(document);
        List<TiledObject> parsedObjects = parseObjectLayers(document);

        // Build final TiledMap object
        return new TiledMap.Builder()
                .setWidth(mapWidth)
                .setHeight(mapHeight)
                .setTileWidth(tileWidth)
                .setTileHeight(tileHeight)
                .setLayers(parsedLayers)
                .setTilesets(parsedTilesets)
                .setObjects(parsedObjects)
                .build();
    }

    private int getIntAttribute(Element element, String attribute)
    {
        try
        {
            String val = element.getAttribute(attribute);
            return Integer.parseInt(val);
        }
        catch (NumberFormatException e)
        {
            LOGGER.warning("Invalid or missing integer attribute: " + attribute);
            return 0;
        }
    }

    // --- Tileset Parsing ---

    private List<TiledTileset> parseTilesets(Document document)
    {
        List<TiledTileset> tilesets = new ArrayList<>();
        NodeList tilesetNodes = document.getElementsByTagName("tileset");

        LOGGER.info("Parsing tilesets... Found: " + tilesetNodes.getLength());

        for (int i = 0; i < tilesetNodes.getLength(); i++)
        {
            Element tilesetElement = (Element) tilesetNodes.item(i);
            String source = tilesetElement.getAttribute("source");
            int firstGID = getIntAttribute(tilesetElement, "firstgid");

            String tsxPath = resourceBasePath + source;
            LOGGER.info("Reading TSX: " + tsxPath + " (firstgid=" + firstGID + ")");

            String imagePath = extractImagePathFromTSX(tsxPath);

            if (imagePath != null)
            {
                tilesets.add(new TiledTileset(imagePath, firstGID));
                LOGGER.info("Loaded tileset image: " + imagePath);
            }
            else
            {
                LOGGER.warning("Could not resolve image path from TSX: " + tsxPath);
            }
        }

        return tilesets;
    }

    private String extractImagePathFromTSX(String tsxPath)
    {
        // Read the <image> tag inside the external .tsx file
        LOGGER.fine("Attempting to load TSX resource: " + tsxPath);

        try (InputStream tsxStream = getClass().getClassLoader().getResourceAsStream(tsxPath))
        {
            if (tsxStream == null)
            {
                LOGGER.severe("TSX file not found via classloader: " + tsxPath);
                return null;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(tsxStream);
            document.getDocumentElement().normalize();

            Element imageElement = (Element) document.getElementsByTagName("image").item(0);
            String imageSource = imageElement.getAttribute("source");

            LOGGER.fine("Extracted tileset image: " + imageSource);

            return resourceBasePath + imageSource;
        }
        catch (Exception e)
        {
            LOGGER.log(Level.SEVERE, "Failed to parse TSX file: " + tsxPath, e);
            return null;
        }
    }

    // --- Tile Layer Parsing ---

    private List<TiledLayer> parseLayers(Document document)
    {
        List<TiledLayer> layers = new ArrayList<>();
        NodeList layerNodes = document.getElementsByTagName("layer");

        LOGGER.info("Parsing tile layers... Found: " + layerNodes.getLength());

        for (int i = 0; i < layerNodes.getLength(); i++)
        {
            Element layerElement = (Element) layerNodes.item(i);
            String layerName = layerElement.getAttribute("name");
            int layerWidth = getIntAttribute(layerElement, "width");
            int layerHeight = getIntAttribute(layerElement, "height");

            LOGGER.info("Layer: " + layerName + " (" + layerWidth + "x" + layerHeight + ")");

            Element dataElement = (Element) layerElement.getElementsByTagName("data").item(0);
            String encoding = dataElement.getAttribute("encoding");

            try
            {
                long[][] tileData;

                // Support CSV or Base64 tile encoding
                if ("csv".equalsIgnoreCase(encoding))
                {
                    tileData = decodeCSVTileData(dataElement.getTextContent().trim(), layerWidth, layerHeight);
                }
                else if ("base64".equalsIgnoreCase(encoding))
                {
                    tileData = decodeBase64TileData(dataElement.getTextContent().trim(), layerWidth, layerHeight);
                }
                else
                {
                    LOGGER.warning("Unsupported encoding type: " + encoding + " in layer: " + layerName);
                    continue;
                }

                layers.add(new TiledLayer(layerName, layerWidth, layerHeight, tileData));
            }
            catch (Exception e)
            {
                LOGGER.log(Level.SEVERE, "Failed to parse layer: " + layerName, e);
            }
        }

        return layers;
    }

    private long[][] decodeCSVTileData(String csvData, int width, int height)
    {
        // Decode comma-separated tile IDs
        long[][] tileData = new long[height][width];
        String[] tokens = csvData.split(",");

        if (tokens.length != width * height)
        {
            LOGGER.warning("CSV tile count mismatch: expected " + (width * height) + ", got " + tokens.length);
        }

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                int index = row * width + col;
                tileData[row][col] = Long.parseLong(tokens[index].trim());
            }
        }

        return tileData;
    }

    private long[][] decodeBase64TileData(String encodedData, int width, int height)
    {
        // Decode Base64-encoded tile IDs
        byte[] decodedData = Base64.getDecoder().decode(encodedData);
        ByteBuffer buffer = ByteBuffer.wrap(decodedData);
        long[][] tileData = new long[height][width];

        for (int row = 0; row < height; row++)
        {
            for (int col = 0; col < width; col++)
            {
                tileData[row][col] = buffer.getInt() & 0xFFFFFFFFL;
            }
        }

        return tileData;
    }

    // --- Object Layer Parsing ---

    private List<TiledObject> parseObjectLayers(Document document)
    {
        List<TiledObject> objects = new ArrayList<>();
        NodeList objectGroupNodes = document.getElementsByTagName("objectgroup");

        LOGGER.info("Parsing object groups... Found: " + objectGroupNodes.getLength());

        for (int i = 0; i < objectGroupNodes.getLength(); i++)
        {
            Element objectGroupElement = (Element) objectGroupNodes.item(i);
            NodeList objectNodes = objectGroupElement.getElementsByTagName("object");

            for (int j = 0; j < objectNodes.getLength(); j++)
            {
                Element objectElement = (Element) objectNodes.item(j);
                objects.add(parseObject(objectElement));
            }
        }

        return objects;
    }

    private TiledObject parseObject(Element objectElement)
    {
        // Parse a single <object> from a layer
        int id = Integer.parseInt(objectElement.getAttribute("id"));
        String name = objectElement.hasAttribute("name") ? objectElement.getAttribute("name") : "Unnamed";
        String type = objectElement.hasAttribute("type") ? objectElement.getAttribute("type") : "Undefined";
        double x = Double.parseDouble(objectElement.getAttribute("x"));
        double y = Double.parseDouble(objectElement.getAttribute("y"));
        double width = objectElement.hasAttribute("width") ? Double.parseDouble(objectElement.getAttribute("width")) : 0;
        double height = objectElement.hasAttribute("height") ? Double.parseDouble(objectElement.getAttribute("height")) : 0;

        Map<String, String> properties = new HashMap<>();
        NodeList propertyNodes = objectElement.getElementsByTagName("property");
        for (int p = 0; p < propertyNodes.getLength(); p++)
        {
            Element propertyElement = (Element) propertyNodes.item(p);
            properties.put(propertyElement.getAttribute("name"), propertyElement.getAttribute("value"));
        }

        LOGGER.fine("Parsed object: " + name + " (type=" + type + ", id=" + id + ")");
        return new TiledObject(id, name, type, x, y, width, height, properties);
    }

    /**
     * Finds and returns the tileset associated with a specific tile ID.
     *
     * @param tileID The global tile ID.
     * @return The matching TiledTileset, or null if none matches.
     */
    @SuppressWarnings("unused")
    public TiledTileset getTilesetForTile(int tileID)
    {
        for (TiledTileset tileset : tilesets)
        {
            if (tileset.containsTile(tileID)) return tileset;
        }
        return null;
    }
}
