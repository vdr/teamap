package vdr.geo.kml.processors;

import java.util.Map;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.xmlunit.assertj3.XmlAssert;
import vdr.geo.test.TestXmlLoader;
import vdr.geo.xml.XmlLoader;

class FragmentTest {
    @Test
    void shouldRemoveFragment() {
        testIncluder("""
            <kml xmlns="http://www.opengis.net/kml/2.2" xmlns:vdr="urn:vdr:geo:kml">
                <Document>
                    <vdr:Fragment xmlns="http://www.opengis.net/kml/2.2" xmlns:vdr="urn:vdr:geo:kml">
                        <Style id="regionstyle">
                            <IconStyle>
                                <href>https://raw.githubusercontent.com/vdr/teamap/main/icons/double_circle.png</href>
                            </IconStyle>
                        </Style>
                        <Style id="regionstyle2">
                            <IconStyle>
                                <href>https://raw.githubusercontent.com/vdr/teamap/main/icons/double_circle.png</href>
                            </IconStyle>
                        </Style>
                    </vdr:Fragment>
                    <name>China</name>
                </Document>
            </kml>
            """, """
            <kml xmlns="http://www.opengis.net/kml/2.2" xmlns:vdr="urn:vdr:geo:kml">
                <Document>
                    <Style id="regionstyle">
                        <IconStyle>
                            <href>https://raw.githubusercontent.com/vdr/teamap/main/icons/double_circle.png</href>
                        </IconStyle>
                    </Style>
                    <Style id="regionstyle2">
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
    void shouldSupportFragmentInFragment() {
        testIncluder("""
            <kml xmlns="http://www.opengis.net/kml/2.2" xmlns:vdr="urn:vdr:geo:kml">
                <Document>
                    <vdr:Fragment>
                        <vdr:Fragment>
                            <Style id="regionstyle">
                                <vdr:Fragment>
                                    <IconStyle>
                                        <href>https://raw.githubusercontent.com/vdr/teamap/main/icons/double_circle.png</href>
                                    </IconStyle>
                                </vdr:Fragment>
                            </Style>
                        </vdr:Fragment>
                        <Style id="regionstyle2">
                            <IconStyle>
                                <href>https://raw.githubusercontent.com/vdr/teamap/main/icons/double_circle.png</href>
                            </IconStyle>
                        </Style>
                    </vdr:Fragment>
                    <name>China</name>
                </Document>
            </kml>
            """, """
            <kml xmlns="http://www.opengis.net/kml/2.2" xmlns:vdr="urn:vdr:geo:kml">
                <Document>
                    <Style id="regionstyle">
                        <IconStyle>
                            <href>https://raw.githubusercontent.com/vdr/teamap/main/icons/double_circle.png</href>
                        </IconStyle>
                    </Style>
                    <Style id="regionstyle2">
                        <IconStyle>
                            <href>https://raw.githubusercontent.com/vdr/teamap/main/icons/double_circle.png</href>
                        </IconStyle>
                    </Style>
                    <name>China</name>
                </Document>
            </kml>
            """);
    }

    private void testIncluder(String baseXml, String expected) {
        var fragment = new Fragment();

        XmlLoader loader = new TestXmlLoader(baseXml, Map.of());
        Document kml = loader.load();
        fragment.process(loader, kml);

        XmlAssert.assertThat(kml).and(expected).ignoreWhitespace().areIdentical();
    }
}