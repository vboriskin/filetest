package filetest;

import java.sql.*;


public class Database {


    public static void databaseQuery(String sqlQuery) {
        Connection connection = null;
        Statement statement = null;
        try {
            //Loads driver
            Class.forName("com.mysql.jdbc.Driver");
            //Creates connection to DB
            String url = "jdbc:mysql://localhost/mysql";
            connection = DriverManager.getConnection(url, "root", "32167");
            //To execute SQL-commands, creates a Statement.
            statement = connection.createStatement();

            statement.executeUpdate(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void databaseQuery(String sqlQuery, String databaseName) {
        Connection connection = null;
        Statement statement = null;
        try {
            //Loads driver
            Class.forName("com.mysql.jdbc.Driver");
            //Creates connection to DB
            String url = "jdbc:mysql://localhost/" +
                    databaseName +
                    "?autoReconnect=true&useUnicode=true&characterEncoding=utf8";
            connection = DriverManager.getConnection(url, "root", "32167");
            //To execute SQL-commands, creates a Statement.
            statement = connection.createStatement();

            statement.executeUpdate(sqlQuery);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void printDatabaseTable() {

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost/pairlist" +
                "?autoReconnect=true&useUnicode=true&characterEncoding=utf8";
        String name = "root";
        String password = "32167";
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            con = DriverManager.getConnection(url, name, password);
            System.out.println("Connected.");
            st = con.createStatement();
            String query = "select * from pairs";
            rs = st.executeQuery(query);

            printResults(rs);

            rs.close();
            System.out.println("Disconnected.");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void printResults(ResultSet rs) throws SQLException {

        String format = "| %-25s | %-25s | %-25s|";
        String border = "-----------------------------------------------------------------------------------";
        String id, name, value;
        System.out.println(border);
        System.out.printf(format, "id", "name", "value");
        System.out.print("\n" + border);
        while (rs.next()) {
            id = rs.getString("id");
            name = rs.getString("name");
            value = rs.getString("value");
            System.out.println();

            System.out.printf(format, id, name, value);
        }
        System.out.println("\n" + border);
    }
}