/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Clients;
import model.dao.ClientsJpaController;
import model.dao.exceptions.NonexistentEntityException;

/**
 *
 * @author mdamaceno
 */
@ManagedBean(name = "clientes")
@ViewScoped
public class ClientesManager {
  
  private EntityManagerFactory emf;
  private StatusCrud status;
  private Clients novoCliente;

  /**
   * Creates a new instance of ClientesManager
   */
  public ClientesManager() {
    emf  = Persistence.createEntityManagerFactory("CrudPU");
    status = StatusCrud.none;
  }
  
  public String callCadClientes() {
    return "cadClienteList";
  }
  
  public String gravaCliente() {
    if (status == StatusCrud.insert) {
      new ClientsJpaController(emf).create(novoCliente);
    } else if (status == StatusCrud.edit) {
      try {
        new ClientsJpaController(emf).edit(novoCliente);
      } catch (Exception ex) {
        Logger.getLogger(ClientesManager.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    status = StatusCrud.none;
    return null;
  }
  
  public String editarCliente(Clients cl) {
    novoCliente = cl;
    status = StatusCrud.edit;
    return null;
  }
  
  public String deletarCliente(Clients cl) {
    try {
      new ClientsJpaController(emf).destroy(cl.getId());
    } catch (NonexistentEntityException ex) {
      Logger.getLogger(ClientesManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    status = StatusCrud.none;
    return null;
  }
  
  public String verCliente(Clients cl) {
    novoCliente = cl;
    status = StatusCrud.view;
    return null;
  }
  
  public String novoClienteCad() {
    status = StatusCrud.insert;
    novoCliente = new Clients();
    return null;
  }
  
  public DataModel<Clients> getListTable() {
    return new ListDataModel<>(new ClientsJpaController(emf).findClientsEntities());
  }

  public StatusCrud getStatus() {
    return status;
  }

  public void setStatus(StatusCrud status) {
    this.status = status;
  }

  public Clients getNovoCliente() {
    return novoCliente;
  }

  public void setNovoCliente(Clients novoCliente) {
    this.novoCliente = novoCliente;
  }
  
  
  
}
