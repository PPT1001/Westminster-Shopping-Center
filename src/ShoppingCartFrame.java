import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ShoppingCartFrame extends JFrame {
    public JTable cartTable;

    public ShoppingCartFrame(User customer, ShoppingCart Cart) {
        setTitle("Shopping Cart");
        setLayout(new GridBagLayout()); // GridBagLayout

        // Top Layer: Table
        JPanel topPanel = new JPanel(new BorderLayout());
        //Make the table use HTML to format the text

        String[] columnNames = {"Product", "Quantity", "Price"};
        DefaultTableModel cartTableModel = new DefaultTableModel(null, columnNames);
        cartTable = new JTable(cartTableModel);
        cartTable.setRowHeight(80);
        JScrollPane tableScrollPane = new JScrollPane(cartTable);
        tableScrollPane.setPreferredSize(new Dimension(600, 200)); // Adjust the size as needed

        GridBagConstraints tableConstraints = new GridBagConstraints();
        tableConstraints.gridx = 0;
        tableConstraints.gridy = 0;
        tableConstraints.gridwidth = 1;
        tableConstraints.gridheight = 1;
        tableConstraints.weightx = 1.0;
        tableConstraints.weighty = 1.0;
        tableConstraints.fill = GridBagConstraints.BOTH;
        topPanel.add(tableScrollPane, BorderLayout.CENTER);
        add(topPanel, tableConstraints);

        // Bottom Layer: Details
        JPanel bottomPanel = new JPanel(new GridBagLayout());
        GridBagConstraints bottomConstraints = new GridBagConstraints();
        bottomConstraints.gridx = 0;
        bottomConstraints.gridy = 1;
        bottomConstraints.gridwidth = 1;
        bottomConstraints.gridheight = 1;
        bottomConstraints.weightx = 1.0;
        bottomConstraints.weighty = 0.2;

        JLabel totalLabel = new JLabel("Total: ");

        bottomConstraints.gridx = 1;
        bottomConstraints.gridy = 2;
        bottomConstraints.weighty = 0.1;
        bottomConstraints.gridwidth = 2;
        bottomConstraints.anchor = GridBagConstraints.EAST;
        bottomConstraints.insets = new Insets(0, 0, 0, 5);
        bottomPanel.add(totalLabel, bottomConstraints);

        JTextPane totalText = new JTextPane();
        totalText.setText(Cart.getTotal());

        bottomConstraints.gridx = 3;
        bottomConstraints.gridy = 2;
        bottomConstraints.gridwidth = 1;
        bottomConstraints.anchor = GridBagConstraints.EAST;
        bottomConstraints.insets = new Insets(0, 0, 0, 0);
        bottomPanel.add(totalText, bottomConstraints);

        JLabel firstPurchaseDiscountLabel = new JLabel("First Purchase Discount (10%):");

        bottomConstraints.gridx = 1;
        bottomConstraints.gridy = 3;
        bottomConstraints.gridwidth = 2;
        bottomConstraints.anchor = GridBagConstraints.EAST;
        bottomConstraints.insets = new Insets(0, 0, 0, 5);
        bottomPanel.add(firstPurchaseDiscountLabel, bottomConstraints);

        JTextPane firstPurchaseDiscountText = new JTextPane();
        firstPurchaseDiscountText.setText(Cart.getFirstPurchaseDiscount(customer));

        bottomConstraints.gridx = 3;
        bottomConstraints.gridy = 3;
        bottomConstraints.gridwidth = 1;
        bottomConstraints.anchor = GridBagConstraints.EAST;
        bottomConstraints.insets = new Insets(0, 0, 0, 0);
        bottomPanel.add(firstPurchaseDiscountText, bottomConstraints);

        JLabel categoryDiscountLabel = new JLabel("3 items in the same category discount (20%): ");

        bottomConstraints.gridx = 1;
        bottomConstraints.gridy = 4;
        bottomConstraints.gridwidth = 2;
        bottomConstraints.anchor = GridBagConstraints.EAST;
        bottomConstraints.insets = new Insets(0, 0, 0, 5);
        bottomPanel.add(categoryDiscountLabel, bottomConstraints);

        JTextPane categoryDiscountText = new JTextPane();
        categoryDiscountText.setText(Cart.getCategoryDiscount());

        bottomConstraints.gridx = 3;
        bottomConstraints.gridy = 4;
        bottomConstraints.gridwidth = 1;
        bottomConstraints.anchor = GridBagConstraints.EAST;
        bottomConstraints.insets = new Insets(0, 0, 0, 0);
        bottomPanel.add(categoryDiscountText, bottomConstraints);

        JLabel finalTotalLabel = new JLabel("Final Total: ");

        bottomConstraints.gridx = 1;
        bottomConstraints.gridy = 5;
        bottomConstraints.gridwidth = 2;
        bottomConstraints.anchor = GridBagConstraints.EAST;
        bottomConstraints.insets = new Insets(0, 0, 0, 5);
        bottomPanel.add(finalTotalLabel, bottomConstraints);

        JTextPane finalTotalText = new JTextPane();
        finalTotalText.setText(Cart.getFinalTotal(customer));

        bottomConstraints.gridx = 3;
        bottomConstraints.gridy = 5;
        bottomConstraints.gridwidth = 1;
        bottomConstraints.anchor = GridBagConstraints.EAST;
        bottomConstraints.insets = new Insets(0, 0, 0, 0);
        bottomPanel.add(finalTotalText, bottomConstraints);


        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.setHorizontalAlignment(SwingConstants.CENTER);

        bottomConstraints.gridx = 3;
        bottomConstraints.gridy = 6;
        bottomConstraints.gridwidth = 1;
        bottomConstraints.weighty = 0.2;
        bottomConstraints.anchor = GridBagConstraints.EAST;
        bottomPanel.add(checkoutButton, bottomConstraints);

        //ActionListener for Checkout Button and the purchased count should increase by 1
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                customer.setNumTimesPurchased(customer.getNumTimesPurchased()+1);
                System.out.println("Checkout Successful");
                //Dialog box to show the final total and successful
                JOptionPane.showMessageDialog(null, "Checkout Successful! \nFinal Total: " + Cart.getFinalTotal(customer));
                dispose();
                Cart.clearCart();
            }
        });

        GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.gridx = 0;
        panelConstraints.gridy = 1;
        panelConstraints.gridwidth = 1;
        panelConstraints.gridheight = 1;
        panelConstraints.weightx = 1.0;
        panelConstraints.weighty = 0.2;
        panelConstraints.fill = GridBagConstraints.BOTH;
        add(bottomPanel, panelConstraints);

        //Set border for the bottom panel
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pack();
        setLocationRelativeTo(null);
        setSize(600, 500);
        updateShoppingCartTable(cartTable,Cart);

    }

    public void updateShoppingCartTable(JTable cartTable, ShoppingCart Cart) {
        DefaultTableModel cartTableModel = (DefaultTableModel) cartTable.getModel();
        cartTableModel.setRowCount(0); // Clear existing rows

        // Add products from the shopping cart to the table
        for (Product product : Cart.getProducts()) {

            //If the same product is added to the cart, increase the quantity.
            int quantity = 1;
            for (int i = 0; i < cartTableModel.getRowCount(); i++) {
                if (cartTableModel.getValueAt(i, 0).equals(product.shoppingCartString(product))) {
                    quantity = (int) cartTableModel.getValueAt(i, 1) + 1;
                    cartTableModel.removeRow(i);
                    break;
                }
            }

            Object[] rowData = {product.shoppingCartString(product), quantity, product.getProductPrice()*quantity};
            cartTableModel.addRow(rowData);

        }
    }
}
