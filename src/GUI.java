import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

public class GUI{

    private static JFrame frame;
    WestminsterShoppingManager WestminsterShoppingManager = new WestminsterShoppingManager();
    private ShoppingCartFrame cartWindow;
    ShoppingCart Cart = new ShoppingCart();
    static User customer;
    Map<String, Product> productMap = WestminsterShoppingManager.getProductMap();
    int selectedno;

    public GUI() {

        // Create a JFrame and set its title, size and default close operation
        frame = new JFrame("Westminster Shopping Centre");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        //On exit run the saveToFile method
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                WestminsterShoppingManager.saveToFile();
                System.exit(0);
            }
        });

        //Method to check if the product has available stock

        //Get Hashmap and convert it to 2D array
        Object[][] data = convertMapToArray(productMap);

        // Create a JPanel and set its layout to GridBagLayout
        JPanel panel = new JPanel(new GridBagLayout());
        frame.setContentPane(panel);

        // Create a GridBagConstraints object to specify the constraints for each component
        GridBagConstraints c = new GridBagConstraints();

        // Create a JButton for the shopping cart
        JButton shoppingCart = new JButton("Shopping Cart");
        shoppingCart.setHorizontalAlignment(SwingConstants.CENTER);

        // Set the grid position, size and weight for the shopping cart button
        c.gridx = 3;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 0.2;

        // Set the fill and anchor for the shopping cart button
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.EAST;

        panel.add(shoppingCart, c);

        //Action listener to the shopping cart button
        shoppingCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // If the cart window is null, create a new instance of it
                if (cartWindow != null) {
                    cartWindow.dispose();
                }
                cartWindow = new ShoppingCartFrame(customer,Cart);

                // Make the cart window visible
                cartWindow.setVisible(true);
            }
        });

        // Create a JLabel for the header and set its font and alignment
        JLabel headerLabel = new JLabel("Select a product category");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 12));
        headerLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Set the grid position, size and weight for the header label
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weightx = 0.5;
        c.weighty = 0.5;

        // Set the fill and anchor for the header label
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;

        // Add the header label to the panel with the constraints
        panel.add(headerLabel, c);

        // Create a JComboBox for the product category selection and add two items
        JComboBox<String> categoryComboBox = new JComboBox<>();
        categoryComboBox.addItem("All");
        categoryComboBox.addItem("Electronics");
        categoryComboBox.addItem("Clothing");

        // Set the grid position, size and weight for the category combo box
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 0.5;

        // Set the fill and anchor for the category combo box
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;

        // Add the category combo box to the panel with the constraints
        panel.add(categoryComboBox, c);

        String[] columnNames = {"Product ID", "Name", "Category", "Price(£)", "Info"};
        JTable productTable = new JTable(data, columnNames);

        //Set Column Width for the table columns
        productTable.getColumnModel().getColumn(0).setPreferredWidth(75);
        productTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        productTable.getColumnModel().getColumn(3).setPreferredWidth(50);
        productTable.getColumnModel().getColumn(4).setPreferredWidth(175);

        //Make Table content not editable
        productTable.setDefaultEditor(Object.class, null);

        // Create a JScrollPane for the product table and set its preferred size
        JScrollPane productScrollPane = new JScrollPane(productTable);
        productScrollPane.setPreferredSize(new Dimension(550, 100));

        // Set the grid position, size and weight for the product scroll pane
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 0.4;
        c.insets = new Insets(0, 0, 10, 0);

        // Set the fill and anchor for the product scroll pane
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;

        // Add the product scroll pane to the panel with the constraints
        panel.add(productScrollPane, c);

        // Create a JTextArea for the selected product information and set its text and editable property
        JTextArea infoTextArea = new JTextArea();
        infoTextArea.setText("""
                Selected Product - Details

                Product ID:\s
                Product Name:\s
                Product Quantity:\s
                Product Price:\s
                Product Size:\s
                Product Colour:\s
                """);
        infoTextArea.setEditable(false);

        // Set the grid position, size and weight for the info scroll pane
        c.gridx = 0;
        c.gridy = 4;
        c.gridwidth = 4;
        c.gridheight = 1;
        c.weightx = 1.0;
        c.weighty = 0.4;

        // Set the fill and anchor for the info scroll pane
        c.fill = GridBagConstraints.BOTH;
        c.anchor = GridBagConstraints.CENTER;

        // Add the info scroll pane to the panel with the constraints
        panel.add(infoTextArea, c);



        // Add an action listener to the category combo box
        categoryComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected category
                selectedno = categoryComboBox.getSelectedIndex();
                String selectedCategory = (String) categoryComboBox.getSelectedItem();

                // Update the table based on the selected category
                DefaultTableModel tableModel;
                assert selectedCategory != null;
                if (selectedCategory.equals("All")) {
                    tableModel = new DefaultTableModel(data, columnNames);
                } else {
                    // Create a new 2D array for the filtered products
                    Object[][] filteredData = new Object[productMap.size()][5];

                    // Loop through the products and add the products that match the selected category to the filtered array
                    int i = 0;
                    for (Product product : productMap.values()) {
                        if (product.getProductQuantity() < 1){
                            continue;
                        }
                        if (product.getProductType().equals(selectedCategory)) {
                            filteredData[i][0] = product.getProductId();
                            filteredData[i][1] = product.getProductName();
                            filteredData[i][2] = product.getProductType();
                            filteredData[i][3] = product.getProductPrice();
                            filteredData[i][4] = product.getProductInfo();
                            i++;
                        }
                    }

                    // Create a new table model with the filtered data and column names
                    tableModel = new DefaultTableModel(filteredData, columnNames);
                }

                // Set the new model to the productTable
                productTable.setModel(tableModel);
                productTable.getColumnModel().getColumn(0).setPreferredWidth(75);
                productTable.getColumnModel().getColumn(1).setPreferredWidth(100);
                productTable.getColumnModel().getColumn(2).setPreferredWidth(100);
                productTable.getColumnModel().getColumn(3).setPreferredWidth(50);
                productTable.getColumnModel().getColumn(4).setPreferredWidth(175);

                //Call the red cell render
                productTable.setDefaultRenderer(Object.class, new RedCellRenderer());
            }
        });

        //Add a list selection listener to the product table
        productTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // Get the selected row index
                int rowIndex = productTable.getSelectedRow();

                // If the row index is valid, get the product id from the first column
                if (rowIndex >= 0) {
                    String selectedProductId = (String) productTable.getValueAt(rowIndex, 0);

                    // Retrieve the product from the HashMap using the selected ID
                    Product selectedProduct = productMap.get(selectedProductId);

                    // Display the product information in the JTextArea
                    infoTextArea.setText("Selected Product - Details\n\n" + selectedProduct.toString());
                } else if (rowIndex == -1) {
                    infoTextArea.setText("""
                            Selected Product - Details

                            Product ID:\s
                            Product Name:\s
                            Product Quantity:\s
                            Product Price:\s
                            Product Size:\s
                            Product Colour:\s
                            """);
                }
            }
        });

        // Create a JButton for the add to shopping cart action and set its text and alignment
        JButton addToCartButton = new JButton("Add to Shopping Cart");
        addToCartButton.setHorizontalAlignment(SwingConstants.CENTER);

        // Set the grid position, size and weight for the add to cart button
        c.gridx = 1;
        c.gridy = 5;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.insets = new Insets(0, 0, 0, 30);

        // Set the fill and anchor for the add to cart button
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.CENTER;

        // Add the add to cart button to the panel with the constraints
        panel.add(addToCartButton, c);

        // Add an action listener to the add to cart button
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Get the selected row index
                int rowIndex = productTable.getSelectedRow();

                // If the row index is valid, get the product id from the first column
                if (rowIndex >= 0) {
                    String selectedProductId = (String) productTable.getValueAt(rowIndex, 0);

                    // Retrieve the product from the HashMap using the selected ID
                    Product selectedProduct = productMap.get(selectedProductId);


                    //Check if the product quantity is greater 0
                    if (selectedProduct.getProductQuantity() > 0){
                        //Update the stock quantity when a product is added to the cart
                        selectedProduct.setProductQuantity(selectedProduct.getProductQuantity()-1);
                        //Make the table cell red if the quantity is less than 3
                        productTable.setDefaultRenderer(Object.class, new RedCellRenderer());
                        //Trigger the table selection listener
                        infoTextArea.setText("Selected Product - Details\n\n" + selectedProduct.toString());
                        //Add the product to the shopping cart
                        Cart.addProduct(selectedProduct);
                        //Dialog box to show the product is added to the cart
                        JOptionPane.showMessageDialog(null, "Product added to the cart");
                    } else {
                        //If the product quantity is 0, remove the product from the table
                        DefaultTableModel tableModel = (DefaultTableModel) productTable.getModel();
                        tableModel.removeRow(rowIndex);
                        JOptionPane.showMessageDialog(null, "Not enough stock");
                    }
                }
                //Trigger the action listener of combo box to update the table
                categoryComboBox.setSelectedIndex(selectedno);
                productTable.setDefaultRenderer(Object.class, new RedCellRenderer());
            }
        });

        //Call the red cell render
        productTable.setDefaultRenderer(Object.class, new RedCellRenderer());

        //Set border for the panel
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

    }

    //Class for making the cells red when quantity is less than 3
    public class RedCellRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component rendererComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            //Get the product ID from 1st column for every row and check its quantity. If Quantity less than 6 mark the whole row in red
            String productId = (String) table.getValueAt(row, 0);
            if (productId != null){
                Product product = productMap.get(productId);
                if (product.getProductQuantity() <= 3) {
                    rendererComponent.setBackground(Color.RED);
                } else {
                    rendererComponent.setBackground(Color.WHITE);
                }
            } else rendererComponent.setBackground(Color.WHITE);

            return rendererComponent;
        }
    }

    public Object[][] convertMapToArray(Map<String, Product> productMap) {
        Object[][] productArray = new Object[productMap.size()][5]; // Assuming 5 attributes in Product class

        int i = 0;
        for (Map.Entry<String, Product> entry : productMap.entrySet()) {
            Product product = entry.getValue();
            productArray[i][0] = product.getProductId();
            productArray[i][1] = product.getProductName();
            productArray[i][2] = product.getProductType();
            productArray[i][3] = product.getProductPrice();
            productArray[i][4] = product.getProductInfo();
            i++;
        }

        return productArray;
    }
}


