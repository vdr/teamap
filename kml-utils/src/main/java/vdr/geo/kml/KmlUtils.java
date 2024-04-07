package vdr.geo.kml;

import static lombok.AccessLevel.PRIVATE;
import static vdr.geo.xml.XmlUtils.toStaticList;

import java.util.Objects;
import java.util.Optional;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

@NoArgsConstructor(access = PRIVATE)
public final class KmlUtils {
    public static Optional<Node> findById(Document kml, String id) {
        return toStaticList(kml.getElementsByTagName("*")).stream()
            .filter(node -> Objects.equals(id, node.getAttributes().getNamedItem("id").getNodeValue()))
            .findAny();
    }
}
