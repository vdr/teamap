package vdr.geo.kml.processors;

import static vdr.geo.xml.XmlUtils.toStaticList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import vdr.geo.kml.KmlTemplate;
import vdr.geo.xml.XmlLoader;

public class Variant implements KmlTemplate.Processor {
    @Override
    public void process(XmlLoader kmlLoader, Document kml) {
        var elements = toStaticList(kml.getElementsByTagName("*"));
        for (Node element : elements) {

//            String reference = trimToEmpty(include.getTextContent());
//
//            String path = trimToEmpty(substringBefore(reference, "#"));
//            String elementId = trimToEmpty(substringAfter(reference, "#"));
//
//            var includedXml = kmlLoader.loadInclude(path);
//            process(kmlLoader, includedXml);
//
//            Node includeNode = includedXml.getDocumentElement();
//            if (!elementId.isEmpty()) {
//                includeNode = KmlUtils
//                    .findById(includedXml, elementId)
//                    .orElseThrow(() -> new XmlException("No element id '" + elementId + "' in xml " + path));
//            }

//            includeNode = kml.importNode(includeNode, true);
//            include.getParentNode().insertBefore(includeNode, include);
//            include.getParentNode().removeChild(include);
        }
    }
}
