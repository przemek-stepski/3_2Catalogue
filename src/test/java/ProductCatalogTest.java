import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

class ProductCatalogTest {

    static final String PATH_TO_TEST_FILE_BEFORE_ALL_BUILD_FROM_STRING = "testFileBeforeAllBuildFromString";

    // jak zmieniam na static to instancja pozostaje ta sama dla każdego testu,a jak nie static to buduje się nowa instacja
    final ProductCatalog testProductCatalog = new ProductCatalog();

    static final Product product1 = new Product("banana", 1.1);
    static final Product product2 = new Product("kokos", 2.2);


    @BeforeAll
    static void buildTestFile() {
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
                "      \"price\": 2.2\n" +
                "    }\n" +
                "  ]\n" +
                "} ";

        try {
            FileWriter writer = new FileWriter(PATH_TO_TEST_FILE_BEFORE_ALL_BUILD_FROM_STRING);
            writer.write(fileContent);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @AfterAll
    public static void deleteTestFile() {
        new File(PATH_TO_TEST_FILE_BEFORE_ALL_BUILD_FROM_STRING).delete();
    }

    @Test
    void testAddProductShouldReturnTrueIfProductAdd() {
        assertTrue(testProductCatalog.addProduct(product1));
    }

    @Test
    void testAddProductShouldReturnFalseIfProductNull() {
        assertFalse(testProductCatalog.addProduct(null));
    }

    @Test
    void testSaveShouldReturnEqualFiles() {
        String path = "thisTestFileSaveMethodTest";
        addTwoProductsToTestProductCatalog();

        try {
            testProductCatalog.save(path);

            String file1ToString = new String(Files.readAllBytes(Paths.get(PATH_TO_TEST_FILE_BEFORE_ALL_BUILD_FROM_STRING))).trim();
            String file2ToString = new String(Files.readAllBytes(Paths.get(path))).trim();

            assertEquals(file1ToString, file2ToString);

        } catch (IOException e) {
            e.printStackTrace();
        }

        new File(path).delete();
    }

    @Test
    void testSaveShouldThrowIOExceptionIfEmptyPath() {
        assertThrows(IOException.class, () -> testProductCatalog.save(""));
    }

    @Test
    void testSaveShouldThrowNPExceptionIfPathNull() {
        assertThrows(NullPointerException.class, () -> testProductCatalog.save(null));
    }

    @Test
    void testLoadShouldReturnProductCatalogueFromTestFile() {
        addTwoProductsToTestProductCatalog();

        assertEquals(ProductCatalog.load(PATH_TO_TEST_FILE_BEFORE_ALL_BUILD_FROM_STRING), testProductCatalog);
    }

    @Test
    void testLoadShouldReturnNullIfWrongFilePath() {
        assertNull(ProductCatalog.load("wrongPath"));
    }

    @Test
    void testLoadShouldReturnNPExceptionIfPathNull() {
        assertThrows(NullPointerException.class, () -> ProductCatalog.load(null));
    }

    private void addTwoProductsToTestProductCatalog()  {
        testProductCatalog.addProduct(product1);
        testProductCatalog.addProduct(product2);
    }
}