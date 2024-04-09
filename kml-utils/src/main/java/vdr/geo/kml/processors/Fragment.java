package vdr.geo.kml.processors;

import static vdr.geo.xml.XmlUtils.toStaticList;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import vdr.geo.kml.KmlTemplate;
import vdr.geo.kml.Schema;
import vdr.geo.xml.XmlLoader;

public class Fragment implements KmlTemplate.Processor {
    @Override
    public void process(XmlLoader kmlLoader, Document kml) {
        var fragments = toStaticList(kml.getElementsByTagNameNS(Schema.NS, Schema.ELM_FRAGMENT));
        for (Node fragment : fragments) {
            var children = toStaticList(fragment.getChildNodes());
            children.forEach(child -> fragment.getParentNode().insertBefore(child, fragment));
            fragment.getParentNode().removeChild(fragment);
        }
    }
}
