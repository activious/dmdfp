package dk.au.cs.dwebtek;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Validator {
    /**
     * Reads and validates XML input according to a specified XMLSchema.
     *
     * @param xmlToReadAndValidate as the XML to read and validate
     * @param schemaToValidateWith as the XMLSchema to validate with
     * @return the validated document
     * @throws JDOMException       if the XML is invalid XML or if the XML does not conform to
     *                             the schema
     * @throws java.io.IOException
     */
    @SuppressWarnings("deprecation")
    public static Document readAndValidateXML(InputStream xmlToReadAndValidate,
                                              Path schemaToValidateWith) throws JDOMException, IOException {
        SAXBuilder builder = new SAXBuilder();
        builder.setValidation(true);
        builder.setProperty(
                "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                "http://www.w3.org/2001/XMLSchema");
        builder.setProperty(
                "http://java.sun.com/xml/jaxp/properties/schemaSource",
                schemaToValidateWith.toFile());
        return builder.build(xmlToReadAndValidate);
    }

    /**
     * @see #readAndValidateXML(java.io.InputStream, java.nio.file.Path)
     */
    public static void validateXML(Document document, Path schemaToValidateWith) throws IOException, JDOMException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        new XMLOutputter().output(document, out);
        readAndValidateXML(new ByteArrayInputStream(out.toByteArray()), schemaToValidateWith);
    }

    /**
     * Command line interface for validating an XML file against an XML schema.
     *
     * @param args [XML-file (.xml), XML-schema-file (.xsd)]
     * @throws JDOMException       if the XML is invalid XML or if the XML does not conform to
     *                             the schema
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException, JDOMException {
        String schema = args[0];
        String file = args[1];
        try {
            readAndValidateXML(new FileInputStream(Paths.get(file).toFile()), Paths.get(schema));
            System.out.printf("%s is valid according to %s.%n", file, schema);
        } catch (JDOMException e) {
            System.out.printf("%s is NOT valid according to %s.%n", file, schema);
            System.out.printf("Cause: %s%n", e.getMessage());
        } catch (IOException e) {
            System.err.printf("ERROR: %s%n", e.getMessage());
        }
    }
}
