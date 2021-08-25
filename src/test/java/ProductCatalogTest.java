import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ProductCatalogTest {

    Product product1 = new Product("banana", 1.1);
    Product product2 = new Product("kokos", 2.5);

    @BeforeAll
    static void saveFile() {
        String fileContent = "{\n" +
                "  \"listProducts\": [\n" +
                "    {\n" +
                "      \"id\": 1,\n" +
                "      \"designation\": \"banana\",\n" +
                "      \"price\": 1.1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 2,\n" +
                "      \"designation\": \"kokos\",\n" +
                "      \"price\": 2.5\n" +
                "    }\n" +
                "  ]\n" +
                "} ";

        try {
            FileWriter writer = new FileWriter("testFile");
            writer.write(fileContent);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void deleteTestFile() {
        File file = new File("testFile");
        file.delete();
    }

    @Test
    void testAddProductShouldReturnTrueIfProductAdd() {
        ProductCatalog productCatalog = new ProductCatalog();
        assertTrue(productCatalog.addProduct(product1));
    }

    @Test
    void testAddProductShouldReturnFalseIfProductNull() {
        ProductCatalog productCatalog = new ProductCatalog();
        assertFalse(productCatalog.addProduct(null));
    }

    @Test
    void testSaveShouldReturnEqualFiles() {
        String path = "thisTestFile";
        ProductCatalog testProductCatalog = new ProductCatalog();
        testProductCatalog.addProduct(product1);
        testProductCatalog.addProduct(product2);
        try {
            testProductCatalog.save(testProductCatalog, path);

            String file1ToString = new String(Files.readAllBytes(Paths.get(path))).trim();
            String file2ToString = new String(Files.readAllBytes(Paths.get("testFile"))).trim();
            assertEquals(file1ToString, file2ToString);
        } catch (IOException e) {
            e.printStackTrace();
        }

        File thisTestFile = new File(path);
        thisTestFile.delete();
    }

    @Test
    void testSaveShouldThrowIOExceptionIfEmptyPath() {
        ProductCatalog testProductCatalog = new ProductCatalog();

        assertThrows(IOException.class, () -> testProductCatalog.save(testProductCatalog, ""));
    }

    @Test
    void testLoadShouldReturnProductCatalogueFromTestFile() throws FileNotFoundException {
        Product product3 = new Product("banana", 1.1);
        Product product4 = new Product("kokos", 2.5);
        ProductCatalog productCatalog = new ProductCatalog();
        productCatalog.addProduct(product1);
        productCatalog.addProduct(product2);

        assertEquals(ProductCatalog.load("testFile"), productCatalog);
    }

    @Test
    void testLoadShouldReturnNullIfWrongFilePath() {
        assertNull(ProductCatalog.load("wrongPath"));
    }

    @Test
    void testLoadShouldReturnNPExceptionIfPathNull() {
        assertThrows(NullPointerException.class, () -> ProductCatalog.load(null));
    }
}