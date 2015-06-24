/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import controller.StatusCrud;
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
import model.Clients;
import model.dao.ClientsJpaController;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author mdamaceno
 */
@WebServlet(name = "listaClientes", urlPatterns = {"/listaClientes"})
public class listaClientes extends HttpServlet {

  private EntityManagerFactory emf;

  public listaClientes() {
    emf = Persistence.createEntityManagerFactory("CrudPU");
  }

  protected void processRequest(HttpServletRequest request, HttpServletResponse response)
          throws ServletException, IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("utf-8");

    PrintWriter out = response.getWriter();

    JSONObject responseDetailsJson = new JSONObject();
    JSONArray arr = new JSONArray();

    Clients clients = new Clients();

    List<Clients> lista = new ClientsJpaController(emf).findClientsEntities();
    
    for (Clients cli : lista) {
      JSONObject obj = new JSONObject();
      obj.put("name", cli.getName());
      obj.put("address", cli.getAddress());
      obj.put("email", cli.getEmail());
      obj.put("gender", cli.getGender());
      obj.put("permission", cli.getPermission());
      arr.add(obj);
    }
    
    responseDetailsJson.put("clients", arr);

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
