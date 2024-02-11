import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;

class UserLogin extends JFrame {
    Map<String, User> userMap = WestminsterShoppingManager.getUserMap();

    public UserLogin() {
        setTitle("User Login");
        setLayout(new GridBagLayout()); // GridBagLayout
        setLocationRelativeTo(null);

        //Layout for the username label and text box for it
        JPanel usernamePanel = new JPanel(new GridBagLayout());
        GridBagConstraints usernameConstraints = new GridBagConstraints();
        usernameConstraints.gridx = 0;
        usernameConstraints.gridy = 0;
        usernameConstraints.gridwidth = 1;
        usernameConstraints.gridheight = 1;
        usernameConstraints.weightx = 1.0;
        usernameConstraints.weighty = 1.0;
        usernameConstraints.fill = GridBagConstraints.BOTH;
        JLabel usernameLabel = new JLabel("Username: ");
        usernamePanel.add(usernameLabel, usernameConstraints);

        usernameConstraints.gridx = 1;
        usernameConstraints.gridy = 0;
        usernameConstraints.gridwidth = 1;
        usernameConstraints.gridheight = 1;
        usernameConstraints.weightx = 1.0;
        usernameConstraints.weighty = 1.0;
        usernameConstraints.fill = GridBagConstraints.BOTH;
        JTextField usernameText = new JTextField();
        usernamePanel.add(usernameText, usernameConstraints);

        //Layout for the password label and text box for it

        JPanel passwordPanel = new JPanel(new GridBagLayout());
        GridBagConstraints passwordConstraints = new GridBagConstraints();
        passwordConstraints.gridx = 0;
        passwordConstraints.gridy = 0;
        passwordConstraints.gridwidth = 1;
        passwordConstraints.gridheight = 1;
        passwordConstraints.weightx = 1.0;
        passwordConstraints.weighty = 1.0;
        passwordConstraints.fill = GridBagConstraints.BOTH;
        JLabel passwordLabel = new JLabel("Password: ");
        passwordPanel.add(passwordLabel, passwordConstraints);

        passwordConstraints.gridx = 1;
        passwordConstraints.gridy = 0;
        passwordConstraints.gridwidth = 1;
        passwordConstraints.gridheight = 0;
        passwordConstraints.weightx = 1.0;
        passwordConstraints.weighty = 1.0;
        passwordConstraints.fill = GridBagConstraints.BOTH;
        JPasswordField passwordText = new JPasswordField();
        passwordPanel.add(passwordText, passwordConstraints);

        //Layout for the login button
        JPanel loginPanel = new JPanel(new GridBagLayout());
        GridBagConstraints loginConstraints = new GridBagConstraints();
        loginConstraints.gridx = 0;
        loginConstraints.gridy = 0;
        loginConstraints.gridwidth = 1;
        loginConstraints.gridheight = 1;
        loginConstraints.weightx = 1.0;
        loginConstraints.weighty = 0.0;
        loginConstraints.fill = GridBagConstraints.BOTH;
        JButton loginButton = new JButton("Login");
        loginPanel.add(loginButton, loginConstraints);

        //Layout for the register button
        JPanel registerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints registerConstraints = new GridBagConstraints();
        registerConstraints.gridx = 0;
        registerConstraints.gridy = 0;
        registerConstraints.gridwidth = 1;
        registerConstraints.gridheight = 1;
        registerConstraints.weightx = 1.0;
        registerConstraints.weighty = 0.0;
        registerConstraints.fill = GridBagConstraints.BOTH;
        JButton registerButton = new JButton("Register");
        registerPanel.add(registerButton, registerConstraints);

        //Layout for the login and register buttons in a panel
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints buttonConstraints = new GridBagConstraints();
        buttonConstraints.gridx = 0;
        buttonConstraints.gridy = 0;
        buttonConstraints.gridwidth = 1;
        buttonConstraints.gridheight = 1;
        buttonConstraints.weightx = 1.0;
        buttonConstraints.weighty = 0.0;
        buttonConstraints.fill = GridBagConstraints.BOTH;
        buttonPanel.add(loginPanel, buttonConstraints);

        buttonConstraints.gridx = 1;
        buttonConstraints.gridy = 0;
        buttonConstraints.gridwidth = 1;
        buttonConstraints.gridheight = 1;
        buttonConstraints.weightx = 1.0;
        buttonConstraints.weighty = 0.0;
        buttonConstraints.fill = GridBagConstraints.BOTH;
        buttonPanel.add(registerPanel, buttonConstraints);

        //Layout for the username, password and button panels in a panel
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints bottomConstraints = new GridBagConstraints();
        bottomConstraints.gridx = 0;
        bottomConstraints.gridy = 0;
        bottomConstraints.gridwidth = 1;
        bottomConstraints.gridheight = 1;
        bottomConstraints.weightx = 1.0;
        bottomConstraints.weighty = 1.0;
        bottomConstraints.fill = GridBagConstraints.BOTH;
        mainPanel.add(usernamePanel, bottomConstraints);

        bottomConstraints.gridx = 0;
        bottomConstraints.gridy = 1;
        bottomConstraints.gridwidth = 1;
        bottomConstraints.gridheight = 1;
        bottomConstraints.weightx = 1.0;
        bottomConstraints.weighty = 1.0;
        bottomConstraints.fill = GridBagConstraints.BOTH;
        mainPanel.add(passwordPanel, bottomConstraints);

        bottomConstraints.gridx = 0;
        bottomConstraints.gridy = 2;
        bottomConstraints.gridwidth = 1;
        bottomConstraints.gridheight = 1;
        bottomConstraints.weightx = 1.0;
        bottomConstraints.weighty = 1.0;
        bottomConstraints.fill = GridBagConstraints.BOTH;
        mainPanel.add(buttonPanel, bottomConstraints);

        GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.gridx = 0;
        panelConstraints.gridy = 1;
        panelConstraints.gridwidth = 1;
        panelConstraints.gridheight = 1;
        panelConstraints.weightx = 1.0;
        panelConstraints.weighty = 1.0;
        panelConstraints.fill = GridBagConstraints.BOTH;
        add(mainPanel, panelConstraints);

        //Add a Label to show the login status with its constraints and add it to the bottom panel
        JLabel loginLabel = new JLabel("Login Status");
        loginLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bottomConstraints.gridx = 0;
        bottomConstraints.gridy = 3;
        bottomConstraints.gridwidth = 1;
        bottomConstraints.gridheight = 1;
        bottomConstraints.weightx = 1.0;
        bottomConstraints.weighty = 1.0;
        bottomConstraints.fill = GridBagConstraints.BOTH;
        mainPanel.add(loginLabel, bottomConstraints);


        //ActionListener for Login Button and the logins should be verified from the userMap
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (userMap.isEmpty()) {
                    loginLabel.setText("No users registered. Register!");
                }
                //Loop through the userMap to find the user
                for (Map.Entry<String, User> entry : userMap.entrySet()) {
                    String username = entry.getValue().getUsername();
                    User user = entry.getValue();
                    //Check if the username and password are correct
                    if (username.equals(usernameText.getText())) {
                        //Check if the password is correct
                        if (user.getPassword().equals(passwordText.getText())) {
                            System.out.println("\nLogin Successful");
                            GUI.customer = user;
                            loginLabel.setText("Login Successful");
                            //After UserLogin only main window should be visible
                            new GUI();
                            //Close the login window
                            dispose();
                        } else {
                            loginLabel.setText("Incorrect Password");
                        }
                        break;
                    } else loginLabel.setText("User not found. Register!");
                }
            }
        });

        //ActionListener for Register Button and the logins should be added to the UserMap
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameText.getText();
                String password = passwordText.getText();
                User user = new User(username, password);
                userMap.put(username, user);
                loginLabel.setText("User Registered. Login Now!");
                System.out.println("User Registered");
            }
        });

        //Border for the main panel
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pack();
        setLocationRelativeTo(null);
        setSize(400, 300);
        setVisible(true);
    }
}