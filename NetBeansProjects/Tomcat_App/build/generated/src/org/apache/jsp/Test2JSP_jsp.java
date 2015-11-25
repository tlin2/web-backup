package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.sql.*;

public final class Test2JSP_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final JspFactory _jspxFactory = JspFactory.getDefaultFactory();

  private static java.util.List<String> _jspx_dependants;

  private org.glassfish.jsp.api.ResourceInjector _jspx_resourceInjector;

  public java.util.List<String> getDependants() {
    return _jspx_dependants;
  }

  public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    PageContext pageContext = null;
    HttpSession session = null;
    ServletContext application = null;
    ServletConfig config = null;
    JspWriter out = null;
    Object page = this;
    JspWriter _jspx_out = null;
    PageContext _jspx_page_context = null;

    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;
      _jspx_resourceInjector = (org.glassfish.jsp.api.ResourceInjector) application.getAttribute("com.sun.appserv.jsp.resource.injector");

      out.write("\n");
      out.write(" \n");
      out.write("\n");
      out.write("\n");
      out.write("<!DOCTYPE html>\n");
      out.write("<html>\n");
      out.write("    <head>\n");
      out.write("        <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n");
      out.write("        <title>Test 2</title>\n");
      out.write("    </head>\n");
      out.write("    <body>\n");
      out.write("        <h1>Test 2 - Book Search</h1>\n");
      out.write("        <form name='formname' onsubmit=\"location.reload()\">\n");
      out.write("        Enter author's first OR last name (Enter * to show all): <input name='txtinput' type='text' required pattern='[A-Za-z*]+'>\n");
      out.write("        <input type='submit' value='Submit'>\n");
      out.write("        </form>\n");
      out.write("        ");

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
        
      out.write("\n");
      out.write("    </body>\n");
      out.write("</html>\n");
    } catch (Throwable t) {
      if (!(t instanceof SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          out.clearBuffer();
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
