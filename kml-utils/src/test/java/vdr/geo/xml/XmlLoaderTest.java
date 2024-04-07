package vdr.geo.xml;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import vdr.geo.test.TestUtils;

class XmlLoaderTest {
    @Test
    void shouldLoadFile() {
        Path xml = TestUtils.getFile("sample.xml");
        String expected = XmlUtils.xmlToString(XmlUtils.fileToXml(xml));
        String actual = XmlUtils.xmlToString(new XmlLoader(xml).load());

        assertEquals(expected, actual);
    }

    @Test
    void shouldLoadInclude() {
        Path xml = TestUtils.getFile("sample.xml");
        XmlLoader loader = new XmlLoader(xml);

        String sibling = XmlUtils.xmlToString(loader.loadInclude("sibling.xml"));
        assertTrue(sibling.contains("sibling"));
        String child = XmlUtils.xmlToString(loader.loadInclude("includes/child.xml"));
        assertTrue(child.contains("child"));
    }
}