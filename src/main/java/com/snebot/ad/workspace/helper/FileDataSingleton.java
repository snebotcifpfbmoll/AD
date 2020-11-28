package com.snebot.ad.workspace.helper;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;
import com.snebot.ad.workspace.data.Catalog;
import com.snebot.ad.workspace.data.Product;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileDataSingleton {
    private static Logger log = LoggerFactory.getLogger(FileDataSingleton.class);
    private static FileDataSingleton instance = null;

    private FileDataSingleton() {
    }

    public static FileDataSingleton getInstance() {
        if (instance == null) instance = new FileDataSingleton();
        return instance;
    }

    public File saveDocument(Document document, String path) {
        File file = null;
        try {
            file = new File(path);
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(document);
            StreamResult streamResult = new StreamResult(file);
            transformer.transform(domSource, streamResult);
        } catch (Exception e) {
            log.error("failed to save document to a file ", e);
        }

        return file;
    }

    public Document convertProductsToXML(List<Product> products) {
        File file = null;
        Document document = null;
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            document = documentBuilder.newDocument();

            Element rootElement = document.createElement("productos");
            document.appendChild(rootElement);
            for (Product product : products) {
                Element productElement = document.createElement("producto");
                rootElement.appendChild(productElement);

                Attr valueAttr = document.createAttribute("value");
                String value = String.format("%d", product.getValue());
                valueAttr.setValue(value);
                productElement.setAttributeNode(valueAttr);

                productElement.appendChild(document.createTextNode(product.getName()));
            }
        } catch (Exception e) {
            log.error("failed to convert products to XML ", e);
        }

        return document;
    }

    public List<Product> getProductsFromXML(File file) {
        ArrayList<Product> products = new ArrayList<>();
        try {
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(file);

            NodeList productList = document.getElementsByTagName("producto");
            for (int i = 0; i < productList.getLength(); i++) {
                Node productNode = productList.item(i);
                if (productNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element productElement = (Element) productNode;
                    String name = productElement.getTextContent();
                    int value = Integer.parseInt(productElement.getAttribute("value"));
                    products.add(new Product(name, value));
                }
            }
        } catch (Exception e) {
            log.error("failed to generate xml file ", e);
        }

        return products;
    }

    public void serializeContent(Object obj, String path) {
        ObjectOutputStream serializer = null;
        try {
            if (obj != null) {
                serializer = new ObjectOutputStream(new FileOutputStream(path));
                serializer.writeObject(obj);
            }
        } catch (Exception e) {
            log.error("failed to serializer object ", e);
        } finally {
            if (serializer != null) {
                try {
                    serializer.close();
                } catch (Exception e) {
                    log.error("failed to close serializer ", e);
                }
            }
        }
    }

    public <T extends Serializable> T deserializeContent(String path) {
        T obj = null;
        ObjectInputStream deserializer = null;
        try {
            deserializer = new ObjectInputStream(new FileInputStream(path));
            obj = (T) deserializer.readObject();
        } catch (Exception e) {
            log.error("failed to serializer object ", e);
        } finally {
            if (deserializer != null) {
                try {
                    deserializer.close();
                } catch (Exception e) {
                    log.error("failed to close serializer ", e);
                }
            }
        }
        return obj;
    }

    public <T extends Serializable> File marshallContent(Catalog content, String path) {
        File file = null;
        try {
            file = new File(path);
            JAXBContext context = JAXBContext.newInstance(content.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(content, file);
        } catch (Exception e) {
            log.error("failed to sava marshall content ", e);
        }

        return file;
    }

    public <T extends Serializable> T unmarshallContent(String path, Class<T> classType) {
        T content = null;
        try {
            File file = new File(path);
            content = classType.getDeclaredConstructor().newInstance();
            JAXBContext context = JAXBContext.newInstance(content.getClass());
            Unmarshaller um = context.createUnmarshaller();
            if (file.exists() && !file.isDirectory()) content = (T) um.unmarshal(file);
        } catch (Exception e) {
            log.error("failed to unmarshall file ", e);
        }

        return content;
    }

    private ObjectMapper getMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(MapperFeature.USE_WRAPPER_NAME_AS_PROPERTY_NAME);
        AnnotationIntrospector introspector = new JaxbAnnotationIntrospector(objectMapper.getTypeFactory());
        objectMapper.setAnnotationIntrospector(introspector);
        objectMapper.configure(JsonGenerator.Feature.AUTO_CLOSE_TARGET, false);
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return objectMapper;
    }

    public <T extends Serializable> String marshallJSON(T content) {
        try {
            return getMapper().writeValueAsString(content);
        } catch (Exception e) {
            log.error("failed to marshal item as json ", e);
            return StringUtils.EMPTY;
        }
    }

    public <T extends Serializable> T unmarshallJSON(String content, Class<T> classType) {
        try {
            return getMapper().readValue(content, classType);
        } catch (Exception e) {
            log.error("failed to unmarshal json ", e);
            return null;
        }
    }
}