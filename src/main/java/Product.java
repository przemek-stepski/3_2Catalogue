import java.util.Objects;

public class Product {

    private final int id;
    private final String designation;
    private final double price;

    private static int counter = 0;


    public Product(String designation, double price) {
        this.id = ++counter;
        this.designation = designation;
        this.price = price;
    }

    public Product(int id, String designation, double price) {
        this.id = id;
        this.designation = designation;
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (this.getClass() != o.getClass() || o == null) return false;
        Product product = (Product) o;
        return Objects.equals(this.id, product.id) && Objects.equals(this.designation, product.designation) && Objects.equals(this.price, product.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, designation, price);
    }

    @Override
    public String toString() {
        return id + "#" + designation + "#" + price;
    }

    public static Product fromString(String line) {
        String[] productArgs = line.split("#");
        int idFromLine = Integer.parseInt(productArgs[0]);
        String designationFromLine = productArgs[1];
        double priceFromLine = Double.parseDouble(productArgs[2]);

        return new Product(idFromLine, designationFromLine, priceFromLine);
    }
}