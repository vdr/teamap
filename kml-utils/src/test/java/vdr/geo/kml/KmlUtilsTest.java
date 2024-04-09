package vdr.geo.kml;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import vdr.geo.xml.XmlUtils;

class KmlUtilsTest {
    private static String KML = """
        <kml xmlns="http://www.opengis.net/kml/2.2">
            <Document>
                <Style id="regionstyle">
                    <IconStyle>
                        <href>https://maps.google.com/mapfiles/kml/shapes/placemark_circle.png</href>
                    </IconStyle>
                </Style>
                <Placemark id="dupe">
                    <styleUrl id="dupe">#regionstyle</styleUrl>
                    <name>Shanghai</name>
                    <Polygon>
                        <outerBoundaryIs>
                            <LinearRing>
                                <coordinates>121.2649,30.689583 120.895119,31.011978 121.207253,31.47485 121.325974,31.505417 121.666809,31.328194 121.862358,31.11347 121.890419,30.847918 121.526802,30.821529 121.2649,30.689583</coordinates>
                            </LinearRing>
                        </outerBoundaryIs>
                    </Polygon>
                </Placemark>
            </Document>
        </kml>
        """;

    @ParameterizedTest
    @CsvSource({
        "regionstyle, true",
        "Shanghai, false",
        "dupe, true"
    })
    void shouldFindById(String id, boolean exist) {
        var kml = XmlUtils.stringToXml(KML);
        assertThat(KmlUtils.findById(kml, id).isPresent()).isEqualTo(exist);
    }
}