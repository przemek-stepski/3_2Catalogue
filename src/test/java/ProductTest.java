import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    Product testProduct = new Product(1, "apple", 2.0);
    String testString = "1#apple#2.0";

    @Test
    void testToStringShouldReturnEquals() {
        assertEquals(testString, testProduct.toString());
    }

    @Test
    void testFromStringShouldReturnEquals() {
        assertEquals(testProduct, Product.fromString(testString));
    }
}