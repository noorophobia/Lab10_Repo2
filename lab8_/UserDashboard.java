 
package lab8_;

 
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.*;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDashboard extends JFrame  {
    private JTextArea inboxTextArea;
    private JTextArea sentBoxTextArea;
    private JTextField recipientField;
    private JTextArea messageContentArea;
    private String username;
    public UserDashboard(String u)  throws ClassNotFoundException{
         setTitle("User Dashboard");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    username=u;
    
         JTabbedPane tabbedPane = new JTabbedPane();

         JPanel inboxPanel = new JPanel();
        inboxPanel.setLayout(new BorderLayout());
        inboxTextArea = new JTextArea();
        inboxTextArea.setEditable(false);
        JScrollPane inboxScrollPane = new JScrollPane(inboxTextArea);
        inboxPanel.add(inboxScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Inbox", inboxPanel);

         JPanel sentBoxPanel = new JPanel();
        sentBoxPanel.setLayout(new BorderLayout());
        sentBoxTextArea = new JTextArea();
        sentBoxTextArea.setEditable(false);
        JScrollPane sentBoxScrollPane = new JScrollPane(sentBoxTextArea);
        sentBoxPanel.add(sentBoxScrollPane, BorderLayout.CENTER);
        tabbedPane.addTab("Sent Box", sentBoxPanel);

         JPanel sendMessagePanel = new JPanel();
        sendMessagePanel.setLayout(new GridLayout(3, 2));

        JLabel recipientLabel = new JLabel("Recipient's User ID:");
        recipientField = new JTextField();
        JLabel messageContentLabel = new JLabel("Message Content:");
        messageContentArea = new JTextArea();
initializeSentBox() ;
initializeReceivedBox();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    sendMessage();
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(UserDashboard.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        sendMessagePanel.add(recipientLabel);
        sendMessagePanel.add(recipientField);
        sendMessagePanel.add(messageContentLabel);
        sendMessagePanel.add(messageContentArea);
        sendMessagePanel.add(new JLabel()); 
        sendMessagePanel.add(sendButton);

        tabbedPane.addTab("Send Message", sendMessagePanel);

         add(tabbedPane);

         setVisible(true);
    }

    private void sendMessage() throws ClassNotFoundException {
         String recipient = recipientField.getText();
        String messageContent = messageContentArea.getText();

          
        String sender = username;  
         if (recipient.isEmpty()) {
                JOptionPane.showMessageDialog(UserDashboard.this, "Receipient Name can not be empty!");
         }
         else if  (messageContent.isEmpty()) {
                JOptionPane.showMessageDialog(UserDashboard.this, "Message box can not be empty!");
         }
         else{
                    DatabaseManager m=new DatabaseManager();
                    
        m.storeChatMessage(sender, recipient, messageContent);

         sentBoxTextArea.append("To: " + recipient + "\n" + messageContent + "\n\n");

         }
         recipientField.setText("");
        messageContentArea.setText("");
         
    }
 private void initializeSentBox() throws ClassNotFoundException {
         List<String> sentMessages = DatabaseManager.getSentMessages(username);
          if(sentMessages.isEmpty()){
              System.out.print("nothigni in db");
          }
         for (String message : sentMessages) {
            sentBoxTextArea.append(message + "\n\n");
        }
    }private void initializeReceivedBox() throws ClassNotFoundException{
         List<String> sentMessages = DatabaseManager.getReceivedMessages(username);

         for (String message : sentMessages) {
            inboxTextArea.append(message + "\n\n");
        }
    }
 
}
