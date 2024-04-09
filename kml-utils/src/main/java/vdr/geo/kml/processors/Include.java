package vdr.geo.kml.processors;

import static org.apache.commons.lang3.StringUtils.substringAfter;
import static org.apache.commons.lang3.StringUtils.substringBefore;
import static org.apache.commons.lang3.StringUtils.trimToEmpty;
import static vdr.geo.xml.XmlUtils.toStaticList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import vdr.geo.kml.KmlTemplate;
import vdr.geo.kml.KmlUtils;
import vdr.geo.kml.Schema;
import vdr.geo.xml.XmlException;
import vdr.geo.xml.XmlLoader;

public class Include implements KmlTemplate.Processor {
    @Override
    public void process(XmlLoader kmlLoader, Document kml) {
        var includes = toStaticList(kml.getElementsByTagNameNS(Schema.NS, Schema.ELM_INCLUDE));
        for (Node include : includes) {
            String reference = trimToEmpty(include.getTextContent());

            String path = trimToEmpty(substringBefore(reference, "#"));
            String elementId = trimToEmpty(substringAfter(reference, "#"));

            var includedXml = kmlLoader.loadInclude(path);
            process(kmlLoader, includedXml);

            Node includeNode = includedXml.getDocumentElement();
            if (!elementId.isEmpty()) {
                includeNode = KmlUtils
                    .findById(includedXml, elementId)
                    .orElseThrow(() -> new XmlException("No element id '" + elementId + "' in xml " + path));
            }

            includeNode = kml.importNode(includeNode, true);
            include.getParentNode().insertBefore(includeNode, include);
            include.getParentNode().removeChild(include);
        }
    }
}
