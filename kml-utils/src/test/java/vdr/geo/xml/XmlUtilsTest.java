package vdr.geo.xml;

import static org.assertj.core.api.Assertions.assertThat;

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
        assertThat(output).isEqualTo(SAMPLE);
    }

    @Test
    void shouldLoadFile() {
        Path xml = TestUtils.getFile("sample.xml");
        String output = XmlUtils.xmlToString(XmlUtils.fileToXml(xml));
        assertThat(output).isEqualTo(SAMPLE);
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
        assertThat(toString(elements)).isEqualTo("a, x:b, c");
        elements = XmlUtils.toStaticList(document.getElementsByTagNameNS("urn:x", "b"));
        assertThat(toString(elements)).isEqualTo("x:b");
        elements = XmlUtils.toStaticList(document.getElementsByTagName("x:b"));
        assertThat(toString(elements)).isEqualTo("x:b");
        elements = XmlUtils.toStaticList(document.getChildNodes());
        assertThat(toString(elements)).isEqualTo("a");
    }

    @Test
    void shouldFindAttribute() {
        String xml = """
            <a xmlns:x="urn:x" x:attr="x value" attr="default value"/>
            """;
        var a = XmlUtils.stringToXml(xml).getDocumentElement();
        assertThat(XmlUtils.attr(a, "attr")).isEqualTo("default value");
        assertThat(XmlUtils.attr(a, "x:attr")).isEqualTo("x value");
        assertThat(XmlUtils.attr(a, "urn:x", "attr")).isEqualTo("x value");
    }

    private String toString(List<? extends Node> list) {
        return list.stream().map(Node::getNodeName).collect(Collectors.joining(", "));
    }
}