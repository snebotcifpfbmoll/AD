package com.snebot.ad.workspace.file;

import com.snebot.ad.workspace.data.Catalog;
import com.snebot.ad.workspace.data.Product;
import com.snebot.ad.workspace.helper.DummyUtils;
import com.snebot.ad.workspace.helper.FileDataSingleton;
import com.snebot.ad.workspace.helper.FileUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import org.w3c.dom.Document;

import java.io.File;
import java.util.List;

@SpringBootTest
public class FileApplicationTest {
    @Test
    void storeProductsInXML() {
        DummyUtils dummyUtils = new DummyUtils();
        FileDataSingleton fileDataSingleton = FileDataSingleton.getInstance();

        List<Product> products = dummyUtils.generateObjects(Product.class, 50);
        Document document = fileDataSingleton.convertProductsToXML(products);
        Assert.notNull(document, "failed to convert products to XML");

        String path = FileUtils.getUserPath("products.xml");
        File file = fileDataSingleton.saveDocument(document, path);
        Assert.notNull(file, "failed to store XML file");

        List<Product> storedProducts = fileDataSingleton.getProductsFromXML(file);
        Assert.notEmpty(storedProducts, "failed to get products from XML file");
    }

    @Test
    void trySerializer() {
        DummyUtils dummyUtils = new DummyUtils();
        FileDataSingleton fileDataSingleton = FileDataSingleton.getInstance();

        List<Product> products = dummyUtils.generateObjects(Product.class, 50);
        String path = FileUtils.getUserPath("products.data");
        fileDataSingleton.serializeContent(products, path);

        List<Product> savedProducts = fileDataSingleton.deserializeContent(path);
        Assert.notNull(savedProducts, "failed to deserialize content");
    }

    @Test
    void tryMarshalling() {
        DummyUtils dummyUtils = new DummyUtils();
        FileDataSingleton fileDataSingleton = FileDataSingleton.getInstance();

        String path = FileUtils.getUserPath("products.json");
        Catalog catalog = new Catalog();
        catalog.setName("Test catalog");
        catalog.setProducts(dummyUtils.generateObjects(Product.class, 50));

        File file = fileDataSingleton.marshallContent(catalog, path);
        Assert.notNull(file, "failed to save catalog");

        Catalog loadedCatalog = fileDataSingleton.unmarshallContent(path, Catalog.class);
        Assert.notNull(loadedCatalog, "failed to unmarshall content");
        Assert.isTrue(catalog.equals(loadedCatalog), "catalog and loadedCatalog are not equal");
    }

    @Test
    void tryMashrallingJSON() {
        DummyUtils dummyUtils = new DummyUtils();
        FileDataSingleton fileDataSingleton = FileDataSingleton.getInstance();

        String path = FileUtils.getUserPath("products.json");
        Catalog catalog = new Catalog();
        catalog.setName("Test catalog");
        catalog.setProducts(dummyUtils.generateObjects(Product.class, 50));

        String content = fileDataSingleton.marshallJSON(catalog);
        System.out.println(content);
        FileUtils.createFile(path, content);

        List<String> loaded = FileUtils.readFileLines(path);
        loaded.forEach(System.out::println);
        String loadedContent = FileUtils.readFileLines(path).get(0);
        Catalog loadedCatalog = fileDataSingleton.unmarshallJSON(loadedContent, Catalog.class);
        Assert.isTrue(catalog.equals(loadedCatalog), "failed unmarshalling of catalog");
    }
}