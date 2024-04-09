package vdr.geo.kml;

import static lombok.AccessLevel.PRIVATE;
import static vdr.geo.xml.XmlUtils.toStaticList;

import java.util.Objects;
import java.util.Optional;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import vdr.geo.xml.XmlUtils;

@NoArgsConstructor(access = PRIVATE)
public final class KmlUtils {
    /**
     * Find the first node with attribute id equals to the provided id.
     * @return the first node found or empty() if none found, no kml or no id.
     */
    public static Optional<Node> findById(Document kml, String id) {
        if (Objects.isNull(kml) || Objects.isNull(id) || id.isEmpty()) {
            return Optional.empty();
        }

        return toStaticList(kml.getElementsByTagName("*")).stream()
            .filter(node -> Objects.equals(id, XmlUtils.attr(node, "id")))
            .findAny();
    }
}
