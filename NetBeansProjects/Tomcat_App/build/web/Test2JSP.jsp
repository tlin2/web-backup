<%--
    Document   : Test2JSP
    Created on : Nov 24, 2015, 8:08:41 AM
    Author     : T
--%>
 
<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test 2</title>
    </head>
    <body>
        <h1>Test 2 - Book Search</h1>
        <form name='formname' onsubmit="location.reload()">
        Enter author's first OR last name (Enter * to show all): <input name='txtinput' type='text' required pattern='[A-Za-z*]+'>
        <input type='submit' value='Submit'>
        </form>
        <%
            String dbURL = "jdbc:mysql://localhost:3306/test2";
            String username="root";
            String password="monroe";
            Class.forName("com.mysql.jdbc.Driver");//load driver
            Connection con = DriverManager.getConnection(dbURL, username, password);
            Statement stmt;
            String bank = request.getParameter("txtinput");
            String query = "";
            if(bank != null && !bank.equals("*")){
                query = "SELECT * FROM test2table WHERE \"" + bank + "\" IN (`First`,`Last`)";
            }else if(bank != null && bank.equals("*")){
                query = "SELECT * FROM test2table";
            }
            out.println("<br><table border=\"1px\">");
                out.println("<tr><th colspan=\"2\">Author</th>"
                        + "<th rowspan=\"2\">Title</th>"
                        + "<th rowspan=\"2\">Publisher</th>"
                        + "<th rowspan=\"2\">Year</th>"
                        + "<th rowspan=\"2\">ISBN</th></tr>"
                        + "<tr><th>Last</th>"
                        + "<th>First</th>"
                        + "</tr>");
            try{
                stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                while(rs.next()){
                    out.println("<tr>"
                            + "<td>" + rs.getString("Last") + "</td>"
                            + "<td>" + rs.getString("First") + "</td>"
                            + "<td>" + rs.getString("Title") + "</td>"
                            + "<td>" + rs.getString("Publisher") + "</td>"
                            + "<td>" + rs.getString("Year") + "</td>"
                            + "<td>" + rs.getString("ISBN") + "</td>"
                            + "</tr>");
                }
            }catch(SQLException e){
                e.printStackTrace();
            }
            out.println("</table>");
        %>
    </body>
</html>
