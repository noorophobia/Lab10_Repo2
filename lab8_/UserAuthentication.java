/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab8_;
 
import lab8_.DatabaseManager;
 import java.awt.GridLayout;

import javax.swing.*;
 import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class UserAuthentication extends JFrame {
      List users; 
    private JTextField usernameField;
    private JPasswordField passwordField;

    public UserAuthentication() throws ClassNotFoundException {
        users=new ArrayList<>();
       users=DatabaseManager.getUserList();
        
         JLabel usernameLabel1 = new JLabel("LOGIN:\n");
         JLabel usernameLabel2 = new JLabel("SIGN UP:\n");

         JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(8);

        JButton loginButton = new JButton("Login");

         JLabel newUsernameLabel = new JLabel("New Username:");
        JLabel newPasswordLabel = new JLabel("New Password:");

        JTextField newUsernameField = new JTextField(15);
        JPasswordField newPasswordField = new JPasswordField(8);
JPanel panel=new JPanel();
        JButton registerButton = new JButton("Register");
         setLayout(new GridLayout(3, 2));
         panel.add(usernameLabel1);
        panel.add(usernameLabel);
     panel.   add(usernameField);
        panel.add(passwordLabel);
      panel.  add(passwordField);
 panel.        add(loginButton);
 add(panel);

         loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                if (authenticateUser(username, password)) {
                    JOptionPane.showMessageDialog(UserAuthentication.this, "Login Successful!");
                    try {
                        openUserDashboard(username);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(UserAuthentication.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(UserAuthentication.this, "Invalid username or password");
                }
            }
        });
JPanel newpanel=new JPanel();
         setLayout(new GridLayout(3, 2));
         newpanel.add(usernameLabel2);
        newpanel.add(newUsernameLabel);
       newpanel. add(newUsernameField);
       newpanel. add(newPasswordLabel);
       newpanel. add(newPasswordField);
        //add(new JLabel());  
      newpanel.  add(registerButton);
      add(newpanel);

         registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = newUsernameField.getText();
                char[] newPasswordChars = newPasswordField.getPassword();
                String newPassword = new String(newPasswordChars);

                if (validateRegistration(newUsername, newPassword)) {
                     users.add(newUsername);
                    JOptionPane.showMessageDialog(UserAuthentication.this, "Registration Successful!");
                    DatabaseManager m=new DatabaseManager();
                    try {
                        m.insertNewUser(newUsername,newPassword);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(UserAuthentication.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    
                    try {
                        openUserDashboard(newUsername);
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(UserAuthentication.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {
                    JOptionPane.showMessageDialog(UserAuthentication.this, "Invalid username or password. Username must be unique.");
                }
            }
        });

         setTitle("User Authentication");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  
        setVisible(true);
    }

    private boolean authenticateUser(String username, String password) {
        for(int i=0;i<users.size();i++){
            
          if(  users.get(i).equals(username)){
              return true;
          }
            
        }
         return false;
    }

    private boolean validateRegistration(String username, String newPassword) {
 for(int i=0;i<users.size();i++){
            
          if(  users.get(i).equals(username)){
              return false;
          }
            
        }
         return true;}
       private void openUserDashboard(String u) throws ClassNotFoundException {
         UserDashboard userDashboard = new UserDashboard(u);
        userDashboard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
        dispose();  
    }
    }

