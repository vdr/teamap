package vdr.geo;

import java.nio.file.Path;
import org.w3c.dom.Document;
import vdr.geo.kml.KmlTemplate;
import vdr.geo.xml.XmlLoader;
import vdr.geo.xml.XmlUtils;

/**
 * Some quick processing for kml to make it work across Google Map, Google Earth, Mapbox
 */
public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: KmlUtils <input file>");
            System.exit(1);
        }
        XmlLoader kmlLoader = new XmlLoader(Path.of(args[0]));
        KmlTemplate template = new KmlTemplate();

        Document kml = template.process(kmlLoader);
        String xml = XmlUtils.xmlToString(kml);

        System.out.println(xml);
    }
}