package pdxservices;

/**
 * @author Michael Boyd
 * 
 * Date: 5/29/2015
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import static javax.swing.JFrame.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.plaf.ColorUIResource;

public class GUI extends JFrame implements ActionListener
{
    /**
     * Data members.
     */
    Authentication authenticate;
    SQLQueries sql = new SQLQueries();
    int constructed = 0; // Variable for checking if a GUI has already been constructed
    
    /**
     * Declare and define class-level GUI components
     */
    private final JFrame loginFrame = new JFrame("PDX Login");
    private final JButton loginButton = new JButton("Login");
    private final JLabel userNameLabel = new JLabel("Username:");
    private final JLabel passwordLabel = new JLabel("Password:");
    private final JTextField userNameField = new JTextField(10);
    private final JPasswordField passwordField = new JPasswordField(10);
    
    private final JFrame userFrame = new JFrame();
    private final JTextArea userTextArea = new JTextArea(28, 40);
    private final JLabel userHeader = new JLabel("Portland International Airport");
    private final JLabel userFooter = new JLabel("For questions or concerns please call"
            + " customer service at (207) 874-8877");
    private final JButton passengerLookupButton = new JButton("Passenger Lookup");
    private final JButton flightScheduleButton = new JButton("Flight Schedule");
    private final JButton airlineContactButton = new JButton("Airline Information");
    private final JButton airportStatusButton = new JButton("Airport Status");
    private final JButton dailyStatsButton = new JButton("Daily Stats");
    private final JButton ticketPricesButton = new JButton("Ticket Prices");
    private JPanel userPanel, topUserPanel, centerUserPanel, leftUserPanel,
            rightUserPanel, bottomUserPanel;
    
    private final JFrame adminFrame = new JFrame();
    private final JTextArea adminTextArea = new JTextArea(28, 40);
    private final JLabel adminHeader = new JLabel("Portland International Airport");
    private final JLabel adminFooter = new JLabel("(Administrator Mode) Technical"
            + " Support 1-800-532-8129");
    private final JButton employeeRosterButton = new JButton("Employee Roster");
    private final JButton assignmentListButton = new JButton("Assignments");
    private final JButton employeeMailingListButton = new JButton("Employee Contact");
    private final JButton airlineMailingListButton = new JButton("Airline Contact");
    private final JButton positionListButton = new JButton("Position List");
    private final JButton planeRepairStatusButton = new JButton("Repair Status");
    private JPanel adminPanel, topAdminPanel, centerAdminPanel, leftAdminPanel,
            rightAdminPanel, bottomAdminPanel;
    
    private final Border raisedBevel = BorderFactory.createRaisedBevelBorder();
    private final Border empty = BorderFactory.createEmptyBorder(10, 10, 10, 10);
    private final Border loweredEtched = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
    
    /**
     * Constructor
     */
    public GUI()
    {
        setTheme();
    }
    
    /**
     * Action events.
     * 
     * @param event 
     */
    @Override
    public void actionPerformed(ActionEvent event)
    {
        String command = event.getActionCommand();
        switch (command) 
        {
            // When the Login button is pressed...
            case "Login" :
                // A login attempt must have values for username and password
                if(userNameField.getText().length() > 0 && passwordField.getPassword().length > 0)
                {
                    // Call the Authentication constructor and set the userName and password values
                    authenticate = new Authentication(userNameField.getText(), 
                            passwordField.getPassword());
                    
                    // Determine which UI mode to use
                    authenticate.setUIMode();
                    
                    if(authenticate.isAdminMode() == true)
                    {
                        loginFrame.setVisible(false);
                        
                        // Checks if the adminGUI has already been constructed
                        if(constructed == 0)
                        {
                            userGUI();
                            adminGUI();
                            adminFrameVisible();
                        }
                        else
                        {
                            adminTextArea.setText("");
                            adminFrameVisible();
                        }
                    }
                    else
                    {
                        loginFrame.setVisible(false);
                        
                        // Checks if the adminGUI has already been constructed
                        if(constructed == 0)
                        {
                            userGUI();
                            adminGUI();
                            userFrameVisible();
                        }
                        else
                        {
                            userTextArea.setText("");
                            userFrameVisible();
                        }
                    }
                }
                else
                {
                    JOptionPane.showMessageDialog(loginFrame, 
                            "Error. You must enter a username and"
                            + " password to login.");
                } break;
                
            // When the Passenger Lookup button is pressed...
            case "Passenger Lookup" :
                // Get the first and last name of the passenger from the user
                String inputFirstName = JOptionPane.showInputDialog(userFrame, "First name?");
                String inputLastName = JOptionPane.showInputDialog(userFrame, "Last name?");
                
                try
                {
                    // Reset the text area for the next set of data
                    userTextArea.setText(" ");
                    
                    // Connect to the database and print the output from the query
                    sql.passengerLookup(inputFirstName, inputLastName, userTextArea);
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                } break;
            
            // When the Flight Schedule button is pressed...
            case "Flight Schedule" :
                try
                {
                    // Reset the text area for the next set of data
                    userTextArea.setText(" ");
                    
                    // Connect to the database and print the output from the query
                    sql.flightSchedule(userTextArea);
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                } break;
            
            // When the Airport Status button is pressed...
            case "Airport Status" :
                try
                {
                    // Reset the text area for the next set of data
                    userTextArea.setText(" ");
                    
                    // Connect to the database and print the output from the query
                    sql.airportStatus(userTextArea);
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                } break;
            
            // When the Airline Information button is pressed...
            case "Airline Information" :
                try
                {
                    // Reset the text area for the next set of data
                    userTextArea.setText(" ");
                    
                    // Connect to the database and print the output from the query
                    sql.airlineInformation(userTextArea);
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                } break;
            
            // When the Ticket Prices button is pressed...
            case "Ticket Prices" :
                try
                {
                    // Reset the text area for the next set of data
                    userTextArea.setText(" ");
                    
                    // Connect to the database and print the output from the query
                    sql.ticketPrices(userTextArea);
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                } break;
                
            // When the Daily Stats button is pressed...
            case "Daily Stats" :
                try
                {
                    // Reset the text area for the next set of data
                    userTextArea.setText(" ");
                    
                    // Connect to the database and print the output from the query
                    sql.dailyStats(userTextArea);
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                } break;
                
            // When the Employee Roster button is pressed...
            case "Employee Roster" :
                try
                {
                    // Reset the text area for the next set of data
                    adminTextArea.setText(" ");
                    
                    // Connect to the database and print the output from the query
                    sql.employeeRoster(adminTextArea);
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                } break;
            
            // When the Assignments button is pressed...
            case "Assignments" :
                try
                {
                    // Reset the text area for the next set of data
                    adminTextArea.setText(" ");
                    
                    // Connect to the database and print the output from the query
                    sql.assignmentList(adminTextArea);
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                } break;
                
            // When the Position button is pressed...
            case "Position List" :
                try
                {
                    // Reset the text area for the next set of data
                    adminTextArea.setText(" ");
                    
                    // Connect to the database and print the output from the query
                    sql.positionList(adminTextArea);
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                } break;
                
            // When the Repair Status button is pressed...
            case "Repair Status" :
                try
                {
                    // Reset the text area for the next set of data
                    adminTextArea.setText(" ");
                    
                    // Connect to the database and print the output from the query
                    sql.repairStatus(adminTextArea);
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                } break;
                
            // When the Employee Contact button is pressed...
            case "Employee Contact" :
                try
                {
                    // Reset the text area for the next set of data
                    adminTextArea.setText(" ");
                    
                    // Connect to the database and print the output from the query
                    sql.employeeContact(adminTextArea);
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                } break;
                
            // When the Airline Contact button is pressed...
            case "Airline Contact" :
                try
                {
                    // Reset the text area for the next set of data
                    adminTextArea.setText(" ");
                    
                    // Connect to the database and print the output from the query
                    sql.airlineContact(adminTextArea);
                }
                catch(Exception e)
                {
                    e.printStackTrace(System.out);
                } break;
                
            // When the Help menu option is pressed...
            case "Help" :
                // Display login information
                JOptionPane.showMessageDialog(loginFrame, "Normal users -\n"
                        + "Username: Any Password: Any\n"
                        + "Admins - \n"
                        + "Username: admin Password: 123"); break;
                
            // When the About menu option is pressed...
            case "About" :
                // Display airport information depending on which frame is visible
                if(authenticate.isAdminMode() == true)
                    {
                        JOptionPane.showMessageDialog(adminFrame, "Portland "
                                + "International Airport is a joint civil-military \n"
                                + "airport and the largest airport in the U.S. state \n"
                                + "of Oregon, accounting for 90% of passenger travel \n"
                                + "and more than 95% of air cargo of the state.");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(userFrame, "Portland "
                                + "International Airport is a joint civil-military \n"
                                + "airport and the largest airport in the U.S. state \n"
                                + "of Oregon, accounting for 90% of passenger travel \n"
                                + "and more than 95% of air cargo of the state.");
                    } break;
             
            // When Log Out menu option is pressed...    
            case "Log Out" :
                // Hide all frames
                userFrame.setVisible(false);
                adminFrame.setVisible(false);
                
                // Clear the username and password fields
                userNameField.setText("");
                passwordField.setText("");
                
                // Display the login screen
                loginFrame.setVisible(true); break;
            
            // When the Quit menu option is pressed...
            case "Quit" :
                System.exit(0); break;
        }
    }
    
    /**
     * Assembles the components of the loginFrame
     */
    public void loginGUI()
    {
        // JPanel for everything within this loginFrame
        JPanel loginPanel = new JPanel(new BorderLayout(10, 10));
        loginPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // JPanel for all label and field components
        JPanel labelsFields = new JPanel(new GridLayout(0, 2, 3, 3));
        labelsFields.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // JPanel for the loginButton
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        // Add the userName components
        labelsFields.add(userNameLabel);
        labelsFields.add(userNameField);
        
        // Add the password components
        labelsFields.add(passwordLabel);
        labelsFields.add(passwordField);
        
        // Add the labelsFields panel
        loginPanel.add(labelsFields, BorderLayout.CENTER);
        
        // Add the loginButton component
        buttonPanel.add(loginButton);
        loginPanel.add(buttonPanel, BorderLayout.PAGE_END);
        loginButton.addActionListener(this);
        
        // Add the loginPanel
        loginFrame.add(loginPanel);
        
        // Add the menu
        loginMenu();
        
        // Close operation for this loginFrame
        loginFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Set the size, position, and visibility of loginFrame
        loginFrame.pack();
        CenteredFrame(loginFrame);
        loginFrame.setVisible(true);
        
    }
    /**
     * Assembles the components of the userFrame.
     * 
     */
    public void userGUI()
    {
        // JPanel for everything within the userFrame
        userPanel = new JPanel(new BorderLayout(10,10));
        userPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // JPanel for the userFrame header
        topUserPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topUserPanel.setBorder(BorderFactory.createCompoundBorder(empty, loweredEtched));
        
        // JPanel for all button components on the left
        leftUserPanel = new JPanel(new GridLayout(4, 1, 10, 50));
        leftUserPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // JPanel for the textArea in the center
        centerUserPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerUserPanel.setBorder(BorderFactory.createCompoundBorder(empty, raisedBevel));
        
        // JPanel for all button components on the right
        rightUserPanel = new JPanel(new GridLayout(4, 1, 10, 50));
        rightUserPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // JPanel for the userFrame footer
        bottomUserPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomUserPanel.setBorder(BorderFactory.createCompoundBorder(empty, loweredEtched));
        
        // Add the userPanel components
        userPanel.add(topUserPanel, BorderLayout.PAGE_START);
        userPanel.add(leftUserPanel, BorderLayout.LINE_START);
        userPanel.add(centerUserPanel, BorderLayout.CENTER);
        userPanel.add(rightUserPanel, BorderLayout.LINE_END);
        userPanel.add(bottomUserPanel, BorderLayout.PAGE_END);
        
        // Add the topUserPanel components
        topUserPanel.add(userHeader);
        
        // Add the leftUserPanel components
        leftUserPanel.add(passengerLookupButton);
        passengerLookupButton.addActionListener(this);
        leftUserPanel.add(flightScheduleButton);
        flightScheduleButton.addActionListener(this);
        leftUserPanel.add(ticketPricesButton);
        ticketPricesButton.addActionListener(this);
        //leftUserPanel.add(buyTicketButton);
        //leftUserPanel.add(cancelReservationButton);
        
        // Add the centerUserPanel components
        centerUserPanel.add(userTextArea);
        userTextArea.setEditable(false);
        
        // Add the rightUserPanel components
        rightUserPanel.add(airportStatusButton);
        airportStatusButton.addActionListener(this);
        rightUserPanel.add(dailyStatsButton);
        dailyStatsButton.addActionListener(this);
        rightUserPanel.add(airlineContactButton);
        airlineContactButton.addActionListener(this);
        
        // Add the bottom userPanel components
        bottomUserPanel.add(userFooter);
        
        // Add the userPanel
        userFrame.add(userPanel);
        
        // Add the menu
        menu(userFrame);
        
        // Close operation for this userFrame
        userFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Set the size, position, and visibility of loginFrame
        userFrame.pack();
        CenteredFrame(userFrame);
        
        // Recognize that the user GUI has been constructed
        constructed = 1;
    }
    
    /**
     * Assembles the components of the adminFrame.
     */
    public void adminGUI()
    {
        // JPanel for everything within the userFrame
        adminPanel = new JPanel(new BorderLayout(10,10));
        adminPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // JPanel for the userFrame header
        topAdminPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        topAdminPanel.setBorder(BorderFactory.createCompoundBorder(empty, loweredEtched));
        
        // JPanel for all button components on the left
        leftAdminPanel = new JPanel(new GridLayout(4, 1, 10, 50));
        leftAdminPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // JPanel for the textArea in the center
        centerAdminPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerAdminPanel.setBorder(BorderFactory.createCompoundBorder(empty, raisedBevel));
        
        // JPanel for all button components on the right
        rightAdminPanel = new JPanel(new GridLayout(4, 1, 10, 50));
        rightAdminPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // JPanel for the userFrame footer
        bottomAdminPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        bottomAdminPanel.setBorder(BorderFactory.createCompoundBorder(empty, loweredEtched));
        
        // Add the userPanel components
        adminPanel.add(topAdminPanel, BorderLayout.PAGE_START);
        adminPanel.add(leftAdminPanel, BorderLayout.LINE_START);
        adminPanel.add(centerAdminPanel, BorderLayout.CENTER);
        adminPanel.add(rightAdminPanel, BorderLayout.LINE_END);
        adminPanel.add(bottomAdminPanel, BorderLayout.PAGE_END);
        
        // Add the topAdminPanel components
        topAdminPanel.add(adminHeader);
        
        // Add the leftAdminPanel components
        leftAdminPanel.add(employeeRosterButton);
        employeeRosterButton.addActionListener(this);
        leftAdminPanel.add(assignmentListButton);
        assignmentListButton.addActionListener(this);
        leftAdminPanel.add(positionListButton);
        positionListButton.addActionListener(this);
        
        // Add the centerAdminPanel components
        centerAdminPanel.add(adminTextArea);
        adminTextArea.setEditable(false);
        
        // Add the rightAdminPanel components
        rightAdminPanel.add(planeRepairStatusButton);
        planeRepairStatusButton.addActionListener(this);
        rightAdminPanel.add(employeeMailingListButton);
        employeeMailingListButton.addActionListener(this);
        rightAdminPanel.add(airlineMailingListButton);
        airlineMailingListButton.addActionListener(this);
        
        // Add the bottom adminPanel components
        bottomAdminPanel.add(adminFooter);
        
        // Add the adminPanel
        adminFrame.add(adminPanel);
        
        // Add the menu
        menu(adminFrame);
        
        // Close operation for this userFrame
        adminFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        // Set the size, position, and visibility of loginFrame
        adminFrame.pack();
        CenteredFrame(adminFrame);
        
        // Recognize that the admin GUI has been constructed
        constructed = 1;
    }
    
    /**
     * Assembles the components of this menu.
     * 
     * @param frame
     */
    public void menu(JFrame frame)
    {
        // Creates a menubar for a JFrame
        JMenuBar menuBar = new JMenuBar();
        
        // Add the menubar to the frame
        frame.setJMenuBar(menuBar);
        
        // Add and define two drop down menus to the menubar
        JMenu fileMenu = new JMenu("File");
        JMenu aboutMenu = new JMenu("Info");
        menuBar.add(fileMenu);
        menuBar.add(aboutMenu);
        
        // Create menu items for the fileMenu
        JMenuItem logOutAction = new JMenuItem("Log Out");
        JMenuItem exitAction = new JMenuItem("Quit");
        
        // Create menu items for the aboutMenu
        JMenuItem aboutAction = new JMenuItem("About");
        
        // Add all menu items to their respective menu
        fileMenu.add(logOutAction);
        fileMenu.addSeparator();
        fileMenu.add(exitAction);
        aboutMenu.add(aboutAction);
        
        // Add action listeners for each menu item
        logOutAction.addActionListener(this);
        exitAction.addActionListener(this);
        aboutAction.addActionListener(this);
    }
    
    public void loginMenu()
    {
        // Creates a menubar for a JFrame
        JMenuBar menuBar = new JMenuBar();
        
        // Add the menubar to the frame
        loginFrame.setJMenuBar(menuBar);
        
        // Add and define two drop down menus to the menubar
        JMenu fileMenu = new JMenu("Info");
        menuBar.add(fileMenu);
        
        // Create menu items for the fileMenu
        JMenuItem helpAction = new JMenuItem("Help");
        JMenuItem exitAction = new JMenuItem("Quit");
        
        // Add all menu items to their respective menu
        fileMenu.add(helpAction);
        fileMenu.addSeparator();
        fileMenu.add(exitAction);
        
        // Add action listeners for each menu item
        helpAction.addActionListener(this);
        exitAction.addActionListener(this);
    }
    
    /**
     * Adds visibility to all userFrame components.
     */
    public void userFrameVisible()
    {
        userFrame.setVisible(true);
        userPanel.setVisible(true);
        topUserPanel.setVisible(true);
        centerUserPanel.setVisible(true);
        leftUserPanel.setVisible(true);
        rightUserPanel.setVisible(true);
        bottomUserPanel.setVisible(true);
    }
    
    /**
     * Adds visibility to all adminFrame components.
     */
    public void adminFrameVisible()
    {
        adminFrame.setVisible(true);
        adminPanel.setVisible(true);
        topUserPanel.setVisible(true);
        centerUserPanel.setVisible(true);
        leftUserPanel.setVisible(true);
        rightUserPanel.setVisible(true);
        bottomUserPanel.setVisible(true);
    }
    
    /**
     * Centers this frame.
     * 
     * @param frame 
     */
    public void CenteredFrame(JFrame frame)
    {
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(dim.width/2-frame.getSize().width/2, dim.height/2-
                frame.getSize().height/2);
    }
    
    /**
     * Sets the theme for the UI.
     * 
     */
    public static void setTheme()
    {
        try
        {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) 
                {
                    if ("Nimbus".equals(info.getName())) 
                    {
                        UIManager.setLookAndFeel(info.getClassName());
                        break;
                    }
                }
        }
        catch(ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e)
        {
            e.printStackTrace(System.out);
        }
        
        UIDefaults defaults = UIManager.getLookAndFeelDefaults();
        defaults.put("Button.focus", new ColorUIResource(new Color(0, 0, 0, 0)));
    }
}
