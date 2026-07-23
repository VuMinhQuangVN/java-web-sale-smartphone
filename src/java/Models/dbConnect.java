/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;
import java.sql.*;
/**
 *
 * @author ACER
 */
public class dbConnect {

    public Connection get;

    public Connection getConnection(){
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection("jdbc:mysql://localhost:3306/qlbandienthoai", "root", "");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
