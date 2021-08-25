import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        Product product1 = new Product("banana", 1.1);
        Product product2 = new Product("kokos", 2.5);

        ProductCatalog productCatalog = new ProductCatalog();

        productCatalog.addProduct(product1);
        productCatalog.addProduct(product2);
        productCatalog.save(productCatalog, "productcatalog");

        ProductCatalog.load("productcatalog");

    }
}
