import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Objects;

public class ProductCatalog {
    //    private static final int MAXPROD = 100;
    private static final String PRODUCTCATALOG = "productcatalog";
    ArrayList<Product> listProducts = new ArrayList<>();
    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductCatalog that = (ProductCatalog) o;
        return Objects.equals(listProducts, that.listProducts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listProducts);
    }

    public boolean addProduct(Product product) {
        if (product != null) {
            listProducts.add(product);
            return true;
        }
        System.out.println("Product must be not null.");
        return false;
    }

    public void save(ProductCatalog productCatalog, String path) throws IOException {
        FileWriter writer = new FileWriter(path);
        gson.toJson(productCatalog, writer);
        writer.flush();
    }

    public static ProductCatalog load(String path) {
        ProductCatalog loadedFile = new ProductCatalog();
        try {
            FileReader reader = new FileReader(path);
            Type productCatalogType = new TypeToken<ProductCatalog>() {
            }.getType();
            loadedFile = gson.fromJson(reader, productCatalogType);
            System.out.println(loadedFile.listProducts);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
            return null;
        }
        return loadedFile;
    }
}
