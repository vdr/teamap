package vdr.geo.xml;

public class XmlException extends RuntimeException {
    public XmlException(Throwable cause) {
        super(cause);
    }

    public XmlException(String message, Throwable cause) {
        super(message, cause);
    }
}
