package vdr.geo.kml.processors;

import static vdr.geo.xml.XmlUtils.toStaticList;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import vdr.geo.kml.KmlTemplate;
import vdr.geo.kml.Schema;
import vdr.geo.xml.XmlLoader;

public class Include implements KmlTemplate.Processor {
    @Override
    public void process(XmlLoader kmlLoader, Document kml) {
        var includes = toStaticList(kml.getElementsByTagNameNS(Schema.NS, Schema.ELM_INCLUDE));
        for (Node include : includes) {
            String path = StringUtils.trimToEmpty(include.getTextContent());

            Document toInclude = kmlLoader.loadInclude(path);

        }
    }
}
