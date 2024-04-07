package vdr.geo.kml;

/**
 * Expand custom templating annotation in KML
 * <p>
 *     Supports with <tt>xmlns:vdr={@value #NS}</tt>
 *     <ul>
 *         <li><tt>{@value #ATTR_STYLEURL}</tt> attribute on Style:
 *              Merge from parent style. Reproduce "inline style" behaviour</li>
 *         <li><tt>{@value #ATTR_STYLEURL}</tt> attribute on Container:
 *              Use the specified style url as default on Features</li>
 *         <li> <tt>{@value #ATTR_NORMAL}</tt>,
 *              <tt>vdr:{@value #ATTR_HIGHLIGHT}</tt> attributes on StyleMap.
 *              Quick create pair for normal, highlight</li>
 *     </ul>
 * </p>
 */

public class Schema {
    public static final String NS = "urn:vdr:geo:kml";
    public static final String ELM_INCLUDE = "include";
    public static final String ELM_PART = "part";
    public static final String ATTR_STYLEURL = "styleUrl";
    public static final String ATTR_NORMAL = "normal";
    public static final String ATTR_HIGHLIGHT = "highlight";
}
