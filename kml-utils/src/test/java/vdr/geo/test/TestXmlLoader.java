package vdr.geo.test;

import java.util.HashMap;
import java.util.Map;
import org.w3c.dom.Document;
import vdr.geo.xml.XmlException;
import vdr.geo.xml.XmlLoader;
import vdr.geo.xml.XmlUtils;

public class TestXmlLoader extends XmlLoader {
    private final Document main;
    private final Map<String, Document> includes;

    public TestXmlLoader(String main, Map<String, String> includes) {
        super(null);

        // Preload - good to fail first for the tests,
        // but unfortunately prevent reuse of this class unlike the normal XmlLoader
        this.main = XmlUtils.stringToXml(main);
        this.includes = new HashMap<>();
        includes.forEach((path, xml) -> {
            this.includes.put(path, XmlUtils.stringToXml(xml));
        });
    }

    @Override
    public Document load() throws XmlException {
        return main;
    }

    @Override
    public Document loadInclude(String relativePath) throws XmlException {
        return includes.get(relativePath);
    }
}
