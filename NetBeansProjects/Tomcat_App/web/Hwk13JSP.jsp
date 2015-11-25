<%--
    Document   : Hwk13JSP
    Created on : Nov 16, 2015, 6:42:31 PM
    Author     : Tommy
--%>
 
<%@page import="java.sql.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Hwk13</title>
    </head>
    <body>
        <h1>Hwk13</h1>
        <form onsubmit="location.reload()">
            Create a record:
            <input name="createid" type="text" placeholder="Student ID" required pattern="[0-9]{3}">
            <input name="lname" type="text" placeholder="Last Name" required pattern="[A-Za-z]+">
            <input name="fname" type="text" placeholder="First Name" required pattern="[A-Za-z]+">
            <input name="gpa" type="text" placeholder="GPA" required pattern="[0-4]{1}\.[0-9]{1}">
            <input name="csubmit" type="submit" value="Insert Record"><br>
        </form>
        <form onsubmit="location.reload()">
            Read a record:
            <input name="readid" type="text" placeholder="Student ID" required pattern="[0-9]{3}">
            <input name="rsubmit" type="submit" value="Get Record"><br>
        </form>
        <form onsubmit="location.reload()">
            Update a record:
            <input name="updateid" type="text" placeholder="Student ID" required pattern="[0-9]{3}">
            <input name="updategpa" type="text" placeholder="GPA to change to" required pattern="[0-4]{1}\.[0-9]{1}">
            <input name="usubmit" type="submit" value="Update Record"><br>
        </form>
        <form onsubmit="location.reload()">
            Delete a record:
            <input name="deleteid" type="text" placeholder="Student ID" required pattern="[0-9]{3}">
            <input name="dsubmit" type="submit" value="Delete Record"><br>
        </form>
        <form>
            <script>
               
            </script>
        <%
            String dbURL = "jdbc:mysql://localhost:3306/hwk13";
            String username="root";
            String password="monroe";
            Class.forName("com.mysql.jdbc.Driver");//load driver
            Connection con = DriverManager.getConnection(dbURL, username, password);
            Connection con2 = DriverManager.getConnection(dbURL, username, password);
            Statement stmt, stmt2;
            String query = "SELECT * FROM hwk13table";
            String secondquery = "";
            String idbank, lnamebank, fnamebank, gpabank;
            if(request.getQueryString() != null){
                if(request.getQueryString().endsWith("Insert+Record")){
                    idbank = request.getParameter("createid");
                    lnamebank = request.getParameter("lname");
                    fnamebank = request.getParameter("fname");
                    gpabank = request.getParameter("gpa");
                    secondquery = "INSERT INTO hwk13table(ID, Last_Name, First_Name, GPA) "
                            + "VALUES('" + idbank + "','" + lnamebank + "','" + fnamebank + "','" + gpabank + "')";
                }else if(request.getQueryString().endsWith("Get+Record")){
                    idbank = request.getParameter("readid");
                    query = "SELECT * FROM hwk13table WHERE ID='" + idbank + "'";
                }else if(request.getQueryString().endsWith("Update+Record")){
                    idbank = request.getParameter("updateid");
                    gpabank = request.getParameter("updategpa");
                    secondquery = "UPDATE hwk13table SET GPA='" + gpabank + "' WHERE ID='" + idbank + "'";
                }else if(request.getQueryString().endsWith("Delete+Record")){
                    idbank = request.getParameter("deleteid");
                    secondquery = "DELETE FROM hwk13table WHERE ID='" + idbank + "'";
                }
            }
            try{
                stmt2 = con.createStatement();
                stmt2.executeUpdate(secondquery);
            }catch(SQLException e){
                e.printStackTrace();;
            }
            try{
                stmt = con2.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                out.println("<br><table border=\"1px\">");
                out.println("<tr><td>ID</td>"
                        + "<td>Last_Name</td>"
                        + "<td>First_Name</td>"
                        + "<td>GPA</td></tr>");
                while(rs.next()){
                    out.println("<tr>"
                            + "<td>" + rs.getString("ID") + "</td>"
                            + "<td>" + rs.getString("Last_Name") + "</td>"
                            + "<td>" + rs.getString("First_Name") + "</td>"
                            + "<td>" + rs.getString("GPA") + "</td>"
                            + "</tr>");
                }
                out.println("</table>");
            }catch(SQLException e){
                e.printStackTrace();
            }
        %>
        </form>
    </body>
</html>