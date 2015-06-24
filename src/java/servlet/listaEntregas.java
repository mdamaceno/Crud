/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Deliveries;
import model.dao.DeliveriesJpaController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author mdamaceno
 */
@WebServlet(name = "listaEntregas", urlPatterns = {"/listaEntregas"})
public class listaEntregas extends HttpServlet {

  private EntityManagerFactory emf;

  public listaEntregas() {
    emf = Persistence.createEntityManagerFactory("CrudPU");
  }
  
  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");

    PrintWriter out = response.getWriter();

    JSONObject responseDetailsJson = new JSONObject();
    JSONArray arr = new JSONArray();
    
    Deliveries deliveries = new Deliveries();
    
    List<Deliveries> lista = new DeliveriesJpaController(emf).findDeliveriesEntities();
    
    for (Deliveries del:lista) {
      JSONObject obj = new JSONObject();
      obj.put("product_name", del.getProductName());
      obj.put("destination_address", del.getDestinationAddress());
      obj.put("client_name", del.getClientId().getName());
      obj.put("price", del.getPrice());
      obj.put("distance", del.getDistance());
      obj.put("delivery_man", del.getDeliveryManId().getName());
      arr.add(obj);
    }
    
    responseDetailsJson.put("deliveries", arr);
    out.print(responseDetailsJson);
  }

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
    processRequest(request, response);
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
    processRequest(request, response);
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
