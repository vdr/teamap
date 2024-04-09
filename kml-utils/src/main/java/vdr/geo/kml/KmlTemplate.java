package vdr.geo.kml;

import java.util.List;
import org.w3c.dom.Document;
import vdr.geo.kml.processors.Fragment;
import vdr.geo.kml.processors.Include;
import vdr.geo.kml.processors.Variant;
import vdr.geo.xml.XmlLoader;

/**
 * Expand custom templating annotation in KML.
 * @see Schema
 */
public class KmlTemplate {
    private List<Processor> processors = List.of(new Include(), new Variant(), new Fragment());

    public Document process(XmlLoader loader) {
        Document kml = loader.load();
        processors.forEach(p -> p.process(loader, kml));

        return kml;
    }

    public interface Processor {
        void process(XmlLoader kmlLoader, Document kml);
    }
}
