package vdr.geo.kml;

/**
 * Expand custom templating annotation in KML
 */

public class Schema {
    public static final String NS = "urn:vdr:geo:kml";
    /**
     * Include the specified element. The value is the relative path to the included XML.
     * Optionally followed by "#" and the id of a specific element inside the document
     * to include that child element instead of the document root element.
     * eg: <ul>
     *     <li><tt>&lt;vdr:Include>path/to/file.xml&lt;/vdr:Include></tt></li>
     *     <li><tt>&lt;vdr:Include>path/to/file.xml#childElementId&lt;/vdr:Include></tt></li>
     * </ul>
     * Include are processed recursively.
     */
    public static final String ELM_INCLUDE = "Include";
    /**
     * Custom container, replaced in post-processing by its children element.
     * Should be included inside another container, or if root of a document, but included in another kml.
     */
    public static final String ELM_FRAGMENT = "Fragment";
    public static final String ATTR_STYLEURL = "styleUrl";
    public static final String ATTR_NORMAL = "normal";
    public static final String ATTR_HIGHLIGHT = "highlight";
}
