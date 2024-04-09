package vdr.geo.kml.processors;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xmlunit.assertj3.XmlAssert;
import vdr.geo.test.TestXmlLoader;
import vdr.geo.xml.XmlLoader;

class IncludeTest {
    private static final String INC = """
        <vdr:Fragment xmlns="http://www.opengis.net/kml/2.2" xmlns:vdr="urn:vdr:geo:kml">
            <Style id="regionstyle">
                <IconStyle>
                    <href>https://raw.githubusercontent.com/vdr/teamap/main/icons/double_circle.png</href>
                </IconStyle>
            </Style>
        </vdr:Fragment>
        """;

    private static final String RECURSEINC = """
        <Recurse xmlns="http://www.opengis.net/kml/2.2" xmlns:vdr="urn:vdr:geo:kml">
            <vdr:Include>inc#regionstyle</vdr:Include>
        </Recurse>
        """;

    @Test
    void shouldIncludeDocument() {
        var loader = new TestXmlLoader("""
            <kml xmlns="http://www.opengis.net/kml/2.2" xmlns:vdr="urn:vdr:geo:kml">
                <Document>
                    <vdr:Include>inc</vdr:Include>
                    <name>China</name>
                </Document>
            </kml>
            """, Map.of("inc", INC));

        testIncluder(loader, """
            <kml xmlns="http://www.opengis.net/kml/2.2" xmlns:vdr="urn:vdr:geo:kml">
                <Document>
                    <vdr:Fragment xmlns="http://www.opengis.net/kml/2.2" xmlns:vdr="urn:vdr:geo:kml">
                        <Style id="regionstyle">
                            <IconStyle>
                                <href>https://raw.githubusercontent.com/vdr/teamap/main/icons/double_circle.png</href>
                            </IconStyle>
                        </Style>
                    </vdr:Fragment>
                    <name>China</name>
                </Document>
            </kml>
            """);
    }

    @Test
    void shouldIncludeIdElement() {
        var loader = new TestXmlLoader("""
            <kml xmlns="http://www.opengis.net/kml/2.2" xmlns:vdr="urn:vdr:geo:kml">
                <Document>
                    <vdr:Include>inc#regionstyle</vdr:Include>
                    <name>China</name>
                </Document>
            </kml>
            """, Map.of("inc", INC));

        testIncluder(loader,"""
            <kml xmlns="http://www.opengis.net/kml/2.2" xmlns:vdr="urn:vdr:geo:kml">
                <Document>
                    <Style id="regionstyle">
                        <IconStyle>
                            <href>https://raw.githubusercontent.com/vdr/teamap/main/icons/double_circle.png</href>
                        </IconStyle>
                    </Style>
                    <name>China</name>
                </Document>
            </kml>
            """);
    }

    @Test
    void shouldIncludeRecursively() {
        var loader = new TestXmlLoader("""
            <kml xmlns="http://www.opengis.net/kml/2.2" xmlns:vdr="urn:vdr:geo:kml">
                <Document>
                    <vdr:Include>recurseinc</vdr:Include>
                    <name>China</name>
                </Document>
            </kml>
            """, Map.of("inc", INC, "recurseinc", RECURSEINC));

        testIncluder(loader,"""
            <kml xmlns="http://www.opengis.net/kml/2.2" xmlns:vdr="urn:vdr:geo:kml">
                <Document>
                    <Recurse>
                        <Style id="regionstyle">
                            <IconStyle>
                                <href>https://raw.githubusercontent.com/vdr/teamap/main/icons/double_circle.png</href>
                            </IconStyle>
                        </Style>
                    </Recurse>
                    <name>China</name>
                </Document>
            </kml>
            """);
    }

    private void testIncluder(XmlLoader loader, String expected) {
        var include = new Include();

        Document kml = loader.load();
        include.process(loader, kml);

        XmlAssert.assertThat(kml).and(expected).ignoreWhitespace().areIdentical();
    }
}