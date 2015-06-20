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
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import model.Transports;
import model.dao.TransportsJpaController;
import model.dao.exceptions.NonexistentEntityException;

/**
 *
 * @author mdamaceno
 */
@ManagedBean(name = "transportes")
@ViewScoped
public class TransportesManager {
  
  private EntityManagerFactory emf;
  private StatusCrud status;
  private Transports novoTransporte;

  /**
   * Creates a new instance of TransportesManager
   */
  public TransportesManager() {
    emf = Persistence.createEntityManagerFactory("CrudPU");
    status = StatusCrud.none;
  }
  
  public String callCadTransportes() {
    return "cadTransporteList";
  }
  
  public String gravaTransporte() {
    if (status == StatusCrud.insert) {
      new TransportsJpaController(emf).create(novoTransporte);
    } else if (status == StatusCrud.edit) {
      try {
        new TransportsJpaController(emf).edit(novoTransporte);
      } catch (Exception ex) {
        Logger.getLogger(TransportesManager.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    status = StatusCrud.none;
    return null;
  }
  
  public String editarTransporte(Transports tr) {
    novoTransporte = tr;
    status = StatusCrud.edit;
    return null;
  }
  
  public String deletarTransporte(Transports tr) {
    try {
      new TransportsJpaController(emf).destroy(tr.getId());
    } catch (NonexistentEntityException ex) {
      Logger.getLogger(TransportesManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    status = StatusCrud.none;
    return null;
  }
  
  public String verTransporte(Transports tr) {
    novoTransporte = tr;
    status = StatusCrud.view;
    return null;
  }
  
  public String novoTransporteCad() {
    status = StatusCrud.insert;
    novoTransporte = new Transports();
    return null;
  }
  
  public DataModel<Transports> getListTable() {
    return new ListDataModel<>(new TransportsJpaController(emf).findTransportsEntities());
  }

  public StatusCrud getStatus() {
    return status;
  }

  public void setStatus(StatusCrud status) {
    this.status = status;
  }

  public Transports getNovoTransporte() {
    return novoTransporte;
  }

  public void setNovoTransporte(Transports novoTransporte) {
    this.novoTransporte = novoTransporte;
  }
  
  
  
}
