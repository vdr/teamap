package vdr.geo.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import vdr.geo.test.TestUtils;

class XmlUtilsTest {
    public static final String SAMPLE = """
            <?xml version="1.0" encoding="UTF-8"?><ns:parent xmlns:ns="urn:something">
              <child ns:id="101">
                <name>Bob Martin</name>
              </child>
              <ns:child id="102">
                <name>Bob Marley</name>
              </ns:child>
            </ns:parent>
            """;

    @Test
    void shouldPrettyPrint() {
        String output = XmlUtils.xmlToString(XmlUtils.stringToXml(SAMPLE));
        assertEquals(SAMPLE, output);
    }

    @Test
    void shouldLoadFile() {
        Path xml = TestUtils.getFile("sample.xml");
        String output = XmlUtils.xmlToString(XmlUtils.fileToXml(xml));
        assertEquals(SAMPLE, output);
    }

    @Test
    void shouldCreateStaticList() {
        String xml = """
            <a xmlns:x="urn:x">
                <x:b>
                    <c d="value"/>
                </x:b>
            </a>
            """;
        Document document = XmlUtils.stringToXml(xml);

        var elements = XmlUtils.toStaticList(document.getElementsByTagName("*"));
        assertEquals("a, x:b, c", toString(elements));
        elements = XmlUtils.toStaticList(document.getElementsByTagNameNS("urn:x", "b"));
        assertEquals("x:b", toString(elements));
        elements = XmlUtils.toStaticList(document.getElementsByTagName("x:b"));
        assertEquals("x:b", toString(elements));
        elements = XmlUtils.toStaticList(document.getChildNodes());
        assertEquals("a", toString(elements));
    }

    private String toString(List<? extends Node> list) {
        return list.stream().map(Node::getNodeName).collect(Collectors.joining(", "));
    }
}