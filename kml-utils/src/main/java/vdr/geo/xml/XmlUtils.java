package vdr.geo.xml;

import static lombok.AccessLevel.PRIVATE;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import lombok.NoArgsConstructor;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@NoArgsConstructor(access = PRIVATE)
public final class XmlUtils {
    public static Document fileToXml(Path file) {
        try {
            return stringToXml(new String(Files.readAllBytes(file), StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new XmlException("Failed to load file:" + file, e);
        }
    }

    public static Document stringToXml(String xmlString) {
        var factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);

        try (var reader = new StringReader(xmlString)) {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(reader));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new XmlException(e);
        }
    }

    public static String xmlToString(Document xml) {
        final String lame = """
            <xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
                <xsl:strip-space elements="*"/>
                <xsl:output method="xml" encoding="UTF-8"/>
                        
                <xsl:template match="@*|node()">
                    <xsl:copy>
                        <xsl:apply-templates select="@*|node()"/>
                    </xsl:copy>
                </xsl:template>
                        
            </xsl:stylesheet>
            """;
        try (var reader = new StringReader(lame)) {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            transformerFactory.setAttribute("indent-number", 2);
            Transformer transformer = transformerFactory.newTransformer(new StreamSource(reader));
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");

            Writer out = new StringWriter();
            transformer.transform(new DOMSource(xml), new StreamResult(out));

            return out.toString();
        } catch (Exception e) {
            throw new XmlException(e);
        }
    }

    public static List<Node> toStaticList(NodeList nodeList) {
        List<Node> nodes = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            nodes.add(nodeList.item(i));
        }

        return nodes;
    }

    public static String attr(Node node, String name) {
        var attributes = node.getAttributes();
        if (attributes != null && attributes.getNamedItem(name) != null) {
            return attributes.getNamedItem(name).getNodeValue();
        }

        return null;
    }

    public static String attr(Node node, String ns , String name) {
        var attributes = node.getAttributes();
        if (attributes != null && attributes.getNamedItemNS(ns, name) != null) {
            return attributes.getNamedItemNS(ns, name).getNodeValue();
        }

        return null;
    }
}
