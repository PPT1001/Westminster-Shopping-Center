public class Clothing extends Product{
    private String size;
    private String colour;

    public Clothing(String productId, String productName,String productType, int productQuantity, double productPrice, String size, String colour) {
        super(productId, productName,productType, productQuantity, productPrice);
        this.size = size;
        this.colour = colour;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    @Override
    public String getProductInfo() {
        return size + ", " + colour;
    }

    @Override
    public String toString() {
        return super.toString() +
                "Product Size: " + size + "\n" +
                "Product Colour: " + colour + "\n";
    }
}