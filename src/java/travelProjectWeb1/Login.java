package travelProjectWeb1;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
//sessionScoped need to implement the interface Serializable
public class Login implements Serializable {

    //attributes
    public static final Scanner keyboard = new Scanner(System.in);
    private static final String URL
            = "jdbc:mysql://mis-sql.uhcl.edu/piccod6036";
    public static final List<String> validTags
            = Arrays.asList("history", "shopping",
                    "beach", "urban", "explorer", "nature", "family");

    private String id;
    private String password;
    private userAccount1 theLoginAccount;

    //get methods and set methods
    public String getId() {
        return id;
    }

    public userAccount1 getTheLoginAccount() {
        return theLoginAccount;
    }
    
    public String getPassword() {
        return password;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            //return to internalError.xhtml
            return ("internalError");
        }

        final String DATABASE_URL = "jdbc:mysql://mis-sql.uhcl.edu/piccod6036";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            //connect to the database with user name and password
            conn = DriverManager.getConnection(DATABASE_URL,
                    "piccod6036", "0969750");
            stat = conn.createStatement();

            rs = stat.executeQuery("Select * from travelaccount where accountID = '" + id + "' and password = '" + password + "'"); 

            if (rs.next()) {

                //if(id.equals(rs.getString(3)))
                if (id.equals("admin")) {

                    //TODO
                  /*  this.theLoginAccount = 
                     new adminAccount1(id, password,
                        rs.getString(3).split("#"),
                        rs.getString("favoriteattractions").split("#"),
                        rs.getString("favoritecities").split("#")); */
                    return "welcome";

                } else {
                    this.theLoginAccount = 
                     new userAccount1(id, password,
                        rs.getString(3).split("#"),
                        rs.getString("favoriteattractions").split("#"),
                        rs.getString("favoritecities").split("#")); 

                    return "welcome";
                }
            } else {
                id = "";
                password = "";
                //id is not found, display loginNotOK.xhtml
                return "loginNotOK";

            }

        } catch (SQLException e) {
            e.printStackTrace();
            return ("internalError");
        } finally {
            try {
                rs.close();
                stat.close();
                conn.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Connection openDatabase() {
        try {
            //connect to the db - login id and password below
            return DriverManager.getConnection(URL, "piccod6036", "0969750");
        } catch (SQLException e) {
            System.err.println("Database access failed!");
            e.printStackTrace();
        }
        return null;
    }

    public static void closeDatabase(ResultSet rs, Statement stat,
            Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (stat != null) {
            try {
                stat.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
