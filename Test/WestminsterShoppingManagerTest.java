import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WestminsterShoppingManagerTest {

    private WestminsterShoppingManager shoppingManager;
    private ByteArrayOutputStream outputStream;

    @Before
    public void setUp() {
        shoppingManager = new WestminsterShoppingManager();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @Test
    public void testAddProduct() {
        // Create a sample product for testing

        testCreateProduct();

        Map<String, Product> productMap = WestminsterShoppingManager.getProductMap();
        assertTrue(productMap.containsKey("TestID"));
        assertEquals(1, productMap.size());

        // Reset System.in to standard input stream
        System.setIn(System.in);
    }

    @Test
    public void testPrintProducts() {
        // Add a product for testing
        WestminsterShoppingManager.getProductMap().put("TestID", new Electronics("TestID", "TestProduct", "Electronics", 10, 25.0, "TestBrand", 2));

        shoppingManager.printProducts();

        String expectedOutput = "TestID: TestProduct (Electronics) - Quantity: 10, Price: $25.0, Brand: TestBrand, Warranty: 2";
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
        public void testCreateProduct() {
        // Simulate user input for creating an Electronics product
        String input = "TestID\nTestProduct\n10\n25.0\n1\nTestBrand\n2\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));

        Product createdProduct = shoppingManager.createProduct();

        assertTrue(createdProduct instanceof Electronics);
        assertEquals("TestID", createdProduct.getProductId());
        assertEquals("TestProduct", createdProduct.getProductName());
        assertEquals(10, createdProduct.getProductQuantity());
        assertEquals(25.0, createdProduct.getProductPrice());
        assertEquals("TestBrand", ((Electronics) createdProduct).getBrand());
        assertEquals(2, ((Electronics) createdProduct).getWarranty());

        // Reset System.in to standard input stream
        System.setIn(System.in);
    }
}
