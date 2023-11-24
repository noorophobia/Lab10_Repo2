 
package lab8_;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/lab8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "pass";

     public static boolean insertNewUser(String username, String password) throws ClassNotFoundException {
        String sql = "INSERT INTO Users (username, password) VALUES (?, ?)";
         try
        {  
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con=DriverManager.getConnection(   DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            int affectedRows = preparedStatement.executeUpdate();
    System.out.println(affectedRows);
            return affectedRows > 0;  
        } catch (SQLException e) {
            e.printStackTrace();
            return false; }
    }
  public static List<String> getUserList() throws ClassNotFoundException {
        List<String> userList = new ArrayList<>();
  try
        {  
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con=DriverManager.getConnection(   DB_URL, DB_USER, DB_PASSWORD);            if (con != null) {
                System.out.println("Connected to the database!");

                String sql = "SELECT * FROM Users";

                try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                    try (ResultSet resultSet = preparedStatement.executeQuery()) {
                        while (resultSet.next()) {
                             userList.add(resultSet.getString("username"));
 
                         }
                    }
                }
            } else {
                System.out.println("Failed to connect to the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userList;
    }

  


     public static List<String> getSentMessages(String currentUser) throws ClassNotFoundException {
                 
        List<String> sentMessages = new ArrayList<>();
        String sql = "SELECT recipient, message_content FROM Messages WHERE sender = ?";

  try
        {  
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con=DriverManager.getConnection(   DB_URL, DB_USER, DB_PASSWORD);
PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, currentUser);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String recipient = resultSet.getString("recipient");
                    String messageContent = resultSet.getString("message_content");
                    sentMessages.add("To: " + recipient + "\n" + messageContent);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return sentMessages;
    }

   

     public static boolean storeChatMessage(String sender, String recipient, String messageContent) throws ClassNotFoundException {
 
         String sql = "INSERT INTO Messages (sender, recipient, message_content) VALUES (?, ?, ?)";
  try
        {  
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con=DriverManager.getConnection(   DB_URL, DB_USER, DB_PASSWORD);
PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, sender);
            preparedStatement.setString(2, recipient);
            preparedStatement.setString(3, messageContent);

            int affectedRows = preparedStatement.executeUpdate();
        System.out.print("message store d? "+affectedRows);
            return affectedRows > 0;  
        } catch (SQLException e) {
            e.printStackTrace();
            return false; }
    }
 
 public static List<String> getReceivedMessages(String currentUser) throws ClassNotFoundException {         
        List<String> receivedMessages = new ArrayList<>();
        String sql = "SELECT sender, message_content FROM Messages WHERE recipient = ?";

  try
        {  
Class.forName("com.mysql.cj.jdbc.Driver");
Connection con=DriverManager.getConnection(   DB_URL, DB_USER, DB_PASSWORD);
PreparedStatement preparedStatement = con.prepareStatement(sql);

            preparedStatement.setString(1, currentUser);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    String sender = resultSet.getString("sender");
                    String messageContent = resultSet.getString("message_content");
                    receivedMessages.add("From: " + sender + "\n" + messageContent);
                }
            }
            for(int i=0 ;i <receivedMessages.size();i++){
                System.out.println(receivedMessages.get(i));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return receivedMessages;
    }

 } 