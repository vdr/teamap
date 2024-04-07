package vdr.geo.xml;

import java.nio.file.Path;
import lombok.RequiredArgsConstructor;
import org.w3c.dom.Document;

@RequiredArgsConstructor
public class XmlLoader {
    private final Path main;

    public Document load() throws XmlException {
        return XmlUtils.fileToXml(main);
    }

    /**
     * @param relativePath the path relative to main file.
     * @return the document specified in relativePath.
     */
    public Document loadInclude(String relativePath) throws XmlException {
        return XmlUtils.fileToXml(main.getParent().resolve(relativePath));
    }
}
