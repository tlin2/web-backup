package Project1ServPackage;

import java.io.*;
import java.text.*;
import java.util.logging.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
 
/**
 * @author Tommy Lin
 */
@WebServlet(urlPatterns = {"/Project1Serv"})
public class Project1Serv extends HttpServlet {
 
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, SAXException, IOException{
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String zpid;
            try {
                zpid = getZpid(request.getQueryString());
                out.println("That property's zpid is " + zpid + "<br>");
                out.println(getValue(zpid) + "<br>");
                out.println("<img src=\"" + getChartUrl(zpid) + "\"/>");
            } catch (NullPointerException e) {
                out.println("No properties found matching your entered location");
            }
        }
    }
    private static final String SEARCH_URL = "http://www.zillow.com/webservice/GetSearchResults.htm";
    private static final String ZESTIMATE_URL = "http://www.zillow.com/webservice/GetZestimate.htm";
    private static final String CHART_URL = "http://www.zillow.com/webservice/GetChart.htm";
    private static final String ZWSID = "X1-ZWz1a0zhxgs3kb_1is62";
    private static final DocumentBuilderFactory dbFac;
    private static final DocumentBuilder docBuilder;
    static{
        try{
            dbFac = DocumentBuilderFactory.newInstance();
            docBuilder = dbFac.newDocumentBuilder();
        }catch(ParserConfigurationException e){
            throw new RuntimeException(e);
        }
    }
    public static String getZpid(String address) throws IOException, SAXException{
        Document searchDoc = docBuilder.parse(SEARCH_URL + "?zws-id=" + ZWSID + "&" +
                address);
        Element firstResult = (Element)searchDoc.getElementsByTagName("results").item(0);
        String id = firstResult.getElementsByTagName("zpid").item(0).getTextContent();
        return id;
    }
//    http://www.zillow.com/webservice/GetSearchResults.htm?zws-id=X1-ZWz1a0zhxgs3kb_1is62&address=2114+Bigelow+Ave&citystatezip=Seattle%2C+WA
    public static String getValue(String zpid) throws SAXException, IOException{
        Document valueDoc = docBuilder.parse(ZESTIMATE_URL + "?zws-id=" + ZWSID +
                "&zpid=" + zpid);
        Element zestimate = (Element)valueDoc.getElementsByTagName("zestimate").item(0);
        Element amount = (Element)zestimate.getElementsByTagName("amount").item(0);
        DecimalFormat df = new DecimalFormat("###,###,###,###,###.00");
        String temp = amount.getTextContent();
        double cost = Double.parseDouble(temp);
        return "This property costs $" + df.format(cost);
    }
//    http://www.zillow.com/webservice/GetZestimate.htm?zws-id=X1-ZWz1a0zhxgs3kb_1is62&zpid=48749425
    public static String getChartUrl(String zpid) throws SAXException, IOException{
        Document chartDoc = docBuilder.parse(CHART_URL + "?zws-id=" + ZWSID +
                "&unit-type=percent&zpid=" + zpid + "&width=300&height=150");
        String url = chartDoc.getElementsByTagName("url").item(0).getTextContent();
        return url;
    }
//    http://www.zillow.com/webservice/GetChart.htm?zws-id=X1-ZWz1a0zhxgs3kb_1is62&unit-type=percent&zpid=48749425&width=300&height=150
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SAXException ex) {
            Logger.getLogger(Project1Serv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SAXException ex) {
            Logger.getLogger(Project1Serv.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}