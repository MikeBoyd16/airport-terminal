package pdxservices;

/**
 * @author Michael Boyd
 * 
 * Date: 5/29/2015
 */

import java.sql.*;
import javax.swing.*;

public class SQLQueries 
{   
    /**
     * Data members.
     */
    private static Connection connect;
    
    /**
     * Constructor.
     */
    public SQLQueries()
    {
        establishConnection();
    }
    
    /**
     * Connects this Java application to the SQL Server database.
     * 
     */
    public static void establishConnection()
    {
        try
        {
            String url ="jdbc:jtds:sqlserver://RV-B-17-INS/PDX;instance=MSSQLSERVER";
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connect = DriverManager.getConnection(url);
        }
        catch(ClassNotFoundException | SQLException e)
        {
            e.printStackTrace(System.out);
        }
    }
    
    /**
     * Queries the database for an individual passenger.
     * 
     * @param firstName
     * @param lastName
     * @param textArea 
     */
    public void passengerLookup(String firstName, String lastName, JTextArea textArea)
    {
        Statement statement = null;
        
        // Removes all white space from the two Strings
        try
        {
            lastName = lastName.replaceAll("\\s+","");
            firstName = firstName.replaceAll("\\s+","");
        }
        catch(Exception e)
        {
            
        }
        
        String passengerLookupQuery = 
                "SELECT " +
                "PassFirstName, PassLastName, PassSeatClass, " +
                "PassResStatus, FlightNo " +
                "FROM " +
                "Passenger " +
                "WHERE " +
                "PassFirstName = '" + firstName + "' AND " +
                "PassLastName = '" + lastName + "'";
        
        try 
            {
                statement = connect.createStatement();
                ResultSet results = statement.executeQuery(passengerLookupQuery);
                
                // Add column headers to the textArea
                textArea.setText("\n      Name\t     Seat Type\tReservation\t"
                        + "Flight#\n\n");
                
                // Display an error message if the query doesn't return any data
                if (!results.isBeforeFirst() )
                {
                    textArea.setText("\n\n\n\n\n\n\n\tNo passenger was found by that name. "
                                + "Try again.");
                }
                else
                {
                    // Run through the result set and display its data in the textArea
                    while (results.next()) 
                    {
                        // Store all query values into new variables
                        String passFirstName = results.getString("PassFirstName");
                        String passLastName = results.getString("PassLastName");
                        String passSeatClass = results.getString("PassSeatClass");
                        String passResStatus = results.getString("PassResStatus");
                        String flightNo = results.getString("FlightNo");
                        
                        // Convert the A, B, and Cs of passenger seat class into
                        // it's alternative names
                        if(passSeatClass.contains("A"))
                        {
                            passSeatClass = "First Class";
                        }
                        else if(passSeatClass.contains("B"))
                        {
                            passSeatClass = "Business";
                        }
                        else
                        {
                            passSeatClass = "Economy";
                        }
                        
                        // Add the values of the returned query data to the textArea
                        textArea.append("   " + passFirstName + " " + passLastName + 
                                "\t     " + passSeatClass + "\t" +
                                passResStatus + "\t" + flightNo);

                    }
                }
            } 
            catch (SQLException e ) 
            {
                e.printStackTrace(System.out);
            } 
            finally 
            {
                if(statement != null) 
                { 
                    try
                    {
                        statement.close(); 
                    }
                    catch(SQLException e)
                    {
                        
                    }
                }
            }
    }
    
    /**
     * Queries the database for a list of flight information.
     * 
     * @param textArea 
     */
    public void flightSchedule(JTextArea textArea)
    {
        Statement statement = null;
        
        String flightScheduleQuery = 
                "SELECT " +
                "FlightNo, FlightDeparture, FlightArrival, " +
                "LEFT(CONVERT(VARCHAR(5), FlightDepartureTime, 131),5) + ' ' + "
                + "RIGHT(CONVERT(VARCHAR(30), FlightDepartureTime, 9),2) AS 'Departure', " +
                "LEFT(CONVERT(VARCHAR(5), FlightArrivalTime, 131),5) + ' ' + "
                + "RIGHT(CONVERT(VARCHAR(30), FlightArrivalTime, 9),2) AS 'Arrival' " +
                "FROM "
                + "Flight " +
                "ORDER BY "
                + "FlightDepartureTime ASC";
        
        try 
            {
                statement = connect.createStatement();
                ResultSet results = statement.executeQuery(flightScheduleQuery);
                
                // Add column headers to the textArea
                textArea.setText("\n   Flight#\tOrigin\tDestination\tDeparture\t"
                        + "Arrival\n\n");
                
                // Run through the result set and display its data in the textArea
                while (results.next()) 
                {
                    // Store all query values into new variables
                    String flightNo = results.getString("FlightNo");
                    String flightDeparture = results.getString("FlightDeparture");
                    String flightArrival = results.getString("FlightArrival");
                    String departure = results.getString("Departure");
                    String arrival = results.getString("Arrival");
                    
                    // Add the values of the returned query data to the textArea
                    textArea.append("   " + flightNo + "\t" + flightDeparture + "\t" + 
                            flightArrival + "\t" + departure + "\t" +
                            arrival + "\n\n");
                }
            } 
            catch (SQLException e ) 
            {
                e.printStackTrace(System.out);
            }
            // Close the connection if there is nothing left in statement
            finally 
            {
                if(statement != null) 
                { 
                    try
                    {
                        statement.close(); 
                    }
                    catch(SQLException e)
                    {
                        
                    }
                }
            }
                
    }
    
    /**
     * Queries the database for information related to the status of all flights.
     * 
     * @param textArea 
     */
    public void airportStatus(JTextArea textArea)
    {
        Statement statement = null;
        
        String airportStatusQuery =
                "SELECT " +
                "PortName, PortStatus, PortCity, PortStateProvince, PortCountry " +
                "FROM " +
                "Airport " +
                "ORDER BY " +
                "PortStatus DESC";
        
        try 
            {
                statement = connect.createStatement();
                ResultSet results = statement.executeQuery(airportStatusQuery);
                
                // Add column headers to the textArea
                textArea.setText("\n     Name\tStatus\tLocation\n\n");
                
                // Run through the result set and display its data in the textArea
                while (results.next()) 
                {
                    // Store all query values into new variables
                    String portName = results.getString("PortName");
                    String portStatus = results.getString("PortStatus");
                    String portCity = results.getString("PortCity");
                    String portStateProvince = results.getString("PortStateProvince");
                    String portCountry = results.getString("PortCountry");
                    
                    // Abbreviate all airport names
                    if(portName.contains("Portland International Airport"))
                    {
                        portName = "PDX";
                    }
                    else if(portName.contains("Los Angeles International Airport"))
                    {
                        portName = "LAX";
                    }
                    else if(portName.contains("San Francisco International Airport"))
                    {
                        portName = "SFO";
                    }
                    else if(portName.contains("Vancouver International Airport"))
                    {
                        portName = "YVR";
                    }
                    else if(portName.contains("McCarran International Airport"))
                    {
                        portName = "LAS";
                    }
                    else if(portName.contains("Denver International Airport"))
                    {
                        portName = "DIA";
                    }
                    else if(portName.contains("Honolulu International Airport"))
                    {
                        portName = "HNL";
                    }
                    
                    // Abbreviate "United States" to "USA"
                    if(portCountry.contains("United States"))
                    {
                        portCountry = "USA";
                    }

                    // Add the values of the returned query data to the textArea
                    textArea.append("     " + portName + "\t" + portStatus + "\t" + 
                            portCity + ", " + portStateProvince + " " +
                            portCountry + "\n\n");
                }
            } 
            catch (SQLException e ) 
            {
                e.printStackTrace(System.out);
            }
            // Close the connection if there is nothing left in statement
            finally 
            {
                if(statement != null) 
                { 
                    try
                    {
                        statement.close(); 
                    }
                    catch(SQLException e)
                    {
                        
                    }
                }
            }
    }
    
    /**
     * Queries the database for airline contact information.
     * 
     * @param textArea 
     */
    public void airlineInformation(JTextArea textArea)
    {
        Statement statement = null;
        
        String airlineInformationQuery =
                "SELECT " +
                "AirlineName, AirlinePhoneNo " +
                "FROM " +
                "Airline";
        
        try 
            {
                statement = connect.createStatement();
                ResultSet results = statement.executeQuery(airlineInformationQuery);
                
                // Add column headers to the textArea
                textArea.setText("\n     Name\t\tPhone #\n\n");
                
                // Run through the result set and display its data in the textArea
                while (results.next()) 
                {
                    // Store all query values into new variables
                    String airlineName = results.getString("AirlineName");
                    String airlinePhoneNo = results.getString("AirlinePhoneNo");
                    
                    // Format the airlinePhone number values base on the length
                    // of the airlineName
                    if(airlineName.length() > 11)
                    {
                        // Add the values of the returned query data to the textArea
                        textArea.append("     " + airlineName + "\t" + airlinePhoneNo + "\n\n");
                    }
                    else
                    {
                        // Add the values of the returned query data to the textArea
                        textArea.append("     " + airlineName + "\t\t" + airlinePhoneNo + "\n\n");
                    }   
                }
            } 
            catch (SQLException e ) 
            {
                e.printStackTrace(System.out);
            }
            // Close the connection if there is nothing left in statement
            finally 
            {
                if(statement != null) 
                { 
                    try
                    {
                        statement.close(); 
                    }
                    catch(SQLException e)
                    {
                        
                    }
                }
            }
    }
    
    /**
     * Queries the database for a list of all employees.
     * 
     * @param textArea 
     */
    public void employeeRoster(JTextArea textArea)
    {
        Statement statement = null;
        
        String employeeRosterQuery =
                "SELECT " +
                "EmpFirstName, EmpLastName, EmpNo " +
                "FROM " +
                "Employee " +
                "ORDER BY " +
                "EmpNo ASC";
        
        try 
            {
                statement = connect.createStatement();
                ResultSet results = statement.executeQuery(employeeRosterQuery);
                
                // Add column headers to the textArea
                textArea.setText("\n     Name\t\tEmployee#\n\n");
                
                // Run through the result set and display its data in the textArea
                while (results.next()) 
                {
                    // Store all query values into new variables
                    String empFirstName = results.getString("EmpFirstName");
                    String empLastName = results.getString("EmpLastName");
                    String empNo = results.getString("EmpNo");
                    
                    // Format the airlinePhone number values base on the length
                    // of the airlineName
                    if((empLastName + empFirstName).length() >= 10)
                    {
                        // Add the values of the returned query data to the textArea
                        textArea.append("     " + empFirstName + " " + empLastName + " \t" + 
                            empNo + "\n\n");
                    }
                    else
                    {
                        // Add the values of the returned query data to the textArea
                        textArea.append("     " + empFirstName + " " + empLastName + " \t\t" + 
                            empNo + "\n\n");
                    }   
                }
            } 
            catch (SQLException e ) 
            {
                e.printStackTrace(System.out);
            }
            // Close the connection if there is nothing left in statement
            finally 
            {
                if(statement != null) 
                { 
                    try
                    {
                        statement.close(); 
                    }
                    catch(SQLException e)
                    {
                        
                    }
                }
            }
    }
    
    /**
     * Queries the database for a list of all employee assignments.
     * 
     * @param textArea 
     */
    public void assignmentList(JTextArea textArea)
    {
        Statement statement = null;
        
        String assignmentListQuery =
                "SELECT " +
                "EmpFirstName, EmpLastName, PlaneNo " +
                "FROM " +
                "Employee " +
                "WHERE " +
                "PlaneNo IS NOT NULL " +
                "ORDER BY " +
                "PlaneNo ASC";
        
        try 
            {
                statement = connect.createStatement();
                ResultSet results = statement.executeQuery(assignmentListQuery);
                
                // Add column headers to the textArea
                textArea.setText("\n     Name\t\tPlane#\n\n");
                
                // Run through the result set and display its data in the textArea
                while (results.next()) 
                {
                    // Store all query values into new variables
                    String empFirstName = results.getString("EmpFirstName");
                    String empLastName = results.getString("EmpLastName");
                    String planeNo = results.getString("PlaneNo");
                    
                    // Format the airlinePhone number values base on the length
                    // of the airlineName
                    if((empLastName + empFirstName).length() >= 10)
                    {
                        // Add the values of the returned query data to the textArea
                        textArea.append("     " + empFirstName + " " + empLastName + " \t" + 
                            planeNo + "\n\n");
                    }
                    else
                    {
                        // Add the values of the returned query data to the textArea
                        textArea.append("     " + empFirstName + " " + empLastName + " \t\t" + 
                            planeNo + "\n\n");
                    }
                }
            } 
            catch (SQLException e ) 
            {
                e.printStackTrace(System.out);
            }
            // Close the connection if there is nothing left in statement
            finally 
            {
                if(statement != null) 
                { 
                    try
                    {
                        statement.close(); 
                    }
                    catch(SQLException e)
                    {
                        
                    }
                }
            }
    }
    
    /**
     * Queries the database for a list of all employees and their position.
     * 
     * @param textArea 
     */
    public void positionList(JTextArea textArea)
    {
        Statement statement = null;
        
        String positionListQuery =
                "SELECT " +
                "EmpFirstName, EmpLastName, EmpTitle " +
                "FROM " +
                "Employee " +
                "ORDER BY " +
                "EmpTitle ASC";
        
        try 
            {
                statement = connect.createStatement();
                ResultSet results = statement.executeQuery(positionListQuery);
                
                // Add column headers to the textArea
                textArea.setText("\n     Name\t\tPosition\n\n");
                
                // Run through the result set and display its data in the textArea
                while (results.next()) 
                {
                    // Store all query values into new variables
                    String empFirstName = results.getString("EmpFirstName");
                    String empLastName = results.getString("EmpLastName");
                    String empTitle = results.getString("EmpTitle");
                    
                    // Format the airlinePhone number values base on the length
                    // of the airlineName
                    if((empLastName + empFirstName).length() >= 10)
                    {
                        // Add the values of the returned query data to the textArea
                        textArea.append("     " + empFirstName + " " + empLastName + " \t" + 
                            empTitle + "\n\n");
                    }
                    else
                    {
                        // Add the values of the returned query data to the textArea
                        textArea.append("     " + empFirstName + " " + empLastName + " \t\t" + 
                            empTitle + "\n\n");
                    }   
                }
            } 
            catch (SQLException e ) 
            {
                e.printStackTrace(System.out);
            }
            // Close the connection if there is nothing left in statement
            finally 
            {
                if(statement != null) 
                { 
                    try
                    {
                        statement.close(); 
                    }
                    catch(SQLException e)
                    {
                        
                    }
                }
            }
    }
    
    /**
     * Queries the database for information on the repair status of airplanes.
     * 
     * @param textArea 
     */
    public void repairStatus(JTextArea textArea)
    {
        Statement statement = null;
        
        String repairStatusQuery = 
                "SELECT " +
                "PlaneNo, PlaneType, PlaneRepairStatus " +
                "FROM " +
                "Airplane " +
                "ORDER BY " +
                "PlaneRepairStatus ASC";
        
        try 
            {
                statement = connect.createStatement();
                ResultSet results = statement.executeQuery(repairStatusQuery);
                
                // Add column headers to the textArea
                textArea.setText("\n     Plane#\tPlane Type\t\tRepair Status\n\n");
                
                // Run through the result set and display its data in the textArea
                while (results.next()) 
                {
                    // Store all query values into new variables
                    String planeNo = results.getString("PlaneNo");
                    String planeType = results.getString("PlaneType");
                    String planeRepairStatus = results.getString("PlaneRepairStatus");
                    
                    if(planeType.length() > 12)
                    {
                        // Add the values of the returned query data to the textArea
                        textArea.append("     " + planeNo + "\t" + planeType + "\t" + 
                            planeRepairStatus + "\n\n");
                    }
                    else
                    {
                        // Add the values of the returned query data to the textArea
                        textArea.append("     " + planeNo + "\t" + planeType + "\t\t" + 
                            planeRepairStatus + "\n\n");
                    }
                }
            } 
            catch (SQLException e ) 
            {
                e.printStackTrace(System.out);
            }
            // Close the connection if there is nothing left in statement
            finally 
            {
                if(statement != null) 
                { 
                    try
                    {
                        statement.close(); 
                    }
                    catch(SQLException e)
                    {
                        
                    }
                }
            }
    }
    
    /**
     * Queries the database for the address information of all employees.
     * 
     * @param textArea 
     */
    public void employeeContact(JTextArea textArea)
    {
        Statement statement = null;
        
        String employeeContactQuery =
                "SELECT " +
                "EmpFirstName, EmpLastName, EmpStreet,\n" +
                "EmpCity, EmpState, EmpZip " +
                "FROM " +
                "Employee";
        
        try 
            {
                statement = connect.createStatement();
                ResultSet results = statement.executeQuery(employeeContactQuery);
                
                // Add column headers to the textArea
                textArea.setText("\n   Name\t\tMailing Address\n\n");
                
                // Run through the result set and display its data in the textArea
                while (results.next()) 
                {
                    // Store all query values into new variables
                    String empFirstName = results.getString("EmpFirstName");
                    String empLastName = results.getString("EmpLastName");
                    String empStreet = results.getString("EmpStreet");
                    String empCity = results.getString("EmpCity");
                    String empState = results.getString("EmpState");
                    String empZip = results.getString("EmpZip");
                    
                    // Format the airlinePhone number values base on the length
                    // of the airlineName
                    if((empLastName + empFirstName).length() >= 12)
                    {
                        // Add the values of the returned query data to the textArea
                        textArea.append("   " + empFirstName + " " + empLastName + " \t" + 
                            empStreet + "  " + empCity + ", " + empState + " " +
                                empZip + "\n\n");
                    }
                    else
                    {
                        // Add the values of the returned query data to the textArea
                        textArea.append("   " + empFirstName + " " + empLastName + " \t\t" + 
                            empStreet + "  " + empCity + ", " + empState + " " +
                                empZip + "\n\n");
                    }   
                }
            } 
            catch (SQLException e ) 
            {
                e.printStackTrace(System.out);
            }
            // Close the connection if there is nothing left in statement
            finally 
            {
                if(statement != null) 
                { 
                    try
                    {
                        statement.close(); 
                    }
                    catch(SQLException e)
                    {
                        
                    }
                }
            }
    }
    
    /**
     * Queries the database for the address information of all associated airlines.
     * 
     * @param textArea 
     */
    public void airlineContact(JTextArea textArea)
    {
        Statement statement = null;
        
        String airlineContactQuery =
                "SELECT " +
                "AirlineName, AirlineStreet, AirlineCity, " +
                "AirlineState, AirlineZip " +
                "FROM " +
                "Airline";
        
        try 
            {
                statement = connect.createStatement();
                ResultSet results = statement.executeQuery(airlineContactQuery);
                
                // Add column headers to the textArea
                textArea.setText("\n   Name\t\tMailing Address\n\n");
                
                // Run through the result set and display its data in the textArea
                while (results.next()) 
                {
                    // Store all query values into new variables
                    String airlineName = results.getString("AirlineName");
                    String airlineStreet = results.getString("AirlineStreet");
                    String airlineCity = results.getString("AirlineCity");
                    String airlineState = results.getString("AirlineState");
                    String airlineZip = results.getString("AirlineZip");
                    
                    // Format the airlinePhone number values base on the length
                    // of the airlineName
                    if(airlineName.length() > 14)
                    {
                        // Add the values of the returned query data to the textArea
                        textArea.append("   " + airlineName + "\t" + airlineStreet + " "
                                + airlineCity + ", " + airlineState + " " +
                                airlineZip + "\n\n");
                    }
                    else
                    {
                        // Add the values of the returned query data to the textArea
                        textArea.append("   " + airlineName + "\t\t" + airlineStreet + " "
                                + airlineCity + ", " + airlineState + " " +
                                airlineZip + "\n\n");
                    }   
                }
            } 
            catch (SQLException e ) 
            {
                e.printStackTrace(System.out);
            }
            // Close the connection if there is nothing left in statement
            finally 
            {
                if(statement != null) 
                { 
                    try
                    {
                        statement.close(); 
                    }
                    catch(SQLException e)
                    {
                        
                    }
                }
            }
    }
    
    public void ticketPrices(JTextArea textArea)
    {
        Statement statement = null;
        
        String ticketPricesQuery =
                "SELECT " +
                "FlightNo, FlightPriceA, FlightPriceB, FlightPriceC " +
                "FROM " +
                "Flight, Airplane " +
                "WHERE " +
                "Flight.PlaneNo = Airplane.PlaneNo " +
                "GROUP BY " +
                "FlightNo, FlightPriceA, FlightPriceB, FlightPriceC " +
                "ORDER BY " +
                "FlightPriceA DESC";
        
        try 
            {
                statement = connect.createStatement();
                ResultSet results = statement.executeQuery(ticketPricesQuery);
                
                // Add column headers to the textArea
                textArea.setText("\n   Flight#\tFirst Class\tBusiness\tEconomy\n\n");
                
                // Run through the result set and display its data in the textArea
                while (results.next()) 
                {
                    // Store all query values into new variables
                    String flightNo = results.getString("FlightNo");
                    String flightPriceA = results.getString("FlightPriceA");
                    String flightPriceB = results.getString("FlightPriceB");
                    String flightPriceC = results.getString("FlightPriceC");
                    
                    // Add the values of the returned query data to the textArea
                    textArea.append("   " + flightNo + "\t$" + flightPriceA + ".00\t$" + 
                            flightPriceB + ".00\t$" + flightPriceC + ".00\n\n");
                }
            } 
            catch (SQLException e ) 
            {
                e.printStackTrace(System.out);
            }
            // Close the connection if there is nothing left in statement
            finally 
            {
                if(statement != null) 
                { 
                    try
                    {
                        statement.close(); 
                    }
                    catch(SQLException e)
                    {
                        
                    }
                }
            }
    }
    
    public void dailyStats(JTextArea textArea)
    {
        Statement statement = null;
        
        String dailyStatsQuery =
                "SELECT " +
                "'Total flights' = (SELECT COUNT(DISTINCT Flight.FlightNo) FROM Flight), " +
                "'On Shift Employees' = (SELECT COUNT(DISTINCT EmpNo) FROM Employee), " +
                "'Total Passengers' = (SELECT COUNT(DISTINCT PassNo) FROM Passenger), " +
                "'Reservations Cancelled' = (SELECT COUNT(CASE WHEN PassResStatus = "
                + "'CANCELLED' THEN 1 END) FROM Passenger), " +
                "'Flights Cancelled' = (SELECT COUNT(CASE WHEN FlightCancellation = "
                + "'YES' THEN 1 END) FROM Flight)";
        
        try 
            {
                statement = connect.createStatement();
                ResultSet results = statement.executeQuery(dailyStatsQuery);
                
                // Add column headers to the textArea
                textArea.setText("\n\n\n   Total Flights\tOn Shift Employees    "
                        + "Total Passengers\n\n");
                
                // Run through the result set and display its data in the textArea
                while (results.next()) 
                {
                    // Store all query values into new variables
                    String totalFlights = results.getString("Total Flights");
                    String onShiftEmployees = results.getString("On Shift Employees");
                    String totalPassengers = results.getString("Total Passengers");
                    
                    // Add the values of the returned query data to the textArea
                    textArea.append("      " + totalFlights + "\t     " + 
                            onShiftEmployees + "\t                " + 
                            totalPassengers + "\t\n\n");
                }
                
                results = statement.executeQuery(dailyStatsQuery);
                
                // Add column headers to the textArea
                textArea.append("\n\n   Reservations Cancelled\tFlights Cancelled\n\n");
                
                // Run through the result set and display its data in the textArea
                while (results.next()) 
                {
                    // Store all query values into new variables
                    String reservationsCancelled = results.getString("Reservations Cancelled");
                    String flightsCancelled = results.getString("Flights Cancelled");
                    
                    // Add the values of the returned query data to the textArea
                    textArea.append("        " + reservationsCancelled + "\t\t    "
                            + "" + flightsCancelled + "\n\n");
                }
            } 
            catch (SQLException e ) 
            {
                e.printStackTrace(System.out);
            }
            // Close the connection if there is nothing left in statement
            finally 
            {
                if(statement != null) 
                { 
                    try
                    {
                        statement.close(); 
                    }
                    catch(SQLException e)
                    {
                        
                    }
                }
            }
    }
}