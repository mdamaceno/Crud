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
import model.DeliveryMen;
import model.dao.DeliveryMenJpaController;
import model.dao.exceptions.NonexistentEntityException;

/**
 *
 * @author mdamaceno
 */
@ManagedBean(name = "entregadores")
@ViewScoped
public class EntregadoresManager {
  
  private EntityManagerFactory emf;
  private StatusCrud status;
  private DeliveryMen novoEntregador;

  /**
   * Creates a new instance of EntregadoresManager
   */
  public EntregadoresManager() {
    emf = Persistence.createEntityManagerFactory("CrudPU");
    status = StatusCrud.none;
  }
  
  public String callCadEntregadores() {
    return "cadEntregadorList";
  }
  
  public String gravaEntregador() {
    if (status == StatusCrud.insert) {
      new DeliveryMenJpaController(emf).create(novoEntregador);
    } else if (status == StatusCrud.edit) {
      try {
        new DeliveryMenJpaController(emf).edit(novoEntregador);
      } catch (Exception ex) {
        Logger.getLogger(EntregadoresManager.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
    
    status = StatusCrud.none;
    return null;
  }
  
  public String editarEntregador(DeliveryMen dm) {
    novoEntregador = dm;
    status = StatusCrud.edit;
    return null;
  }
  
  public String deletarEntregador(DeliveryMen dm) {
    try {
      new DeliveryMenJpaController(emf).destroy(dm.getId());
    } catch (NonexistentEntityException ex) {
      Logger.getLogger(EntregadoresManager.class.getName()).log(Level.SEVERE, null, ex);
    }
    
    status = StatusCrud.none;
    return null;
  }
  
  public String verEntregador(DeliveryMen dm) {
    novoEntregador = dm;
    status = StatusCrud.view;
    return null;
  }
  
  public String novoEntregadorCad() {
    status = StatusCrud.insert;
    novoEntregador = new DeliveryMen();
    return null;
  }
  
  public DataModel<DeliveryMen> getListTable() {
    return new ListDataModel<>(new DeliveryMenJpaController(emf).findDeliveryMenEntities());
  }

  public StatusCrud getStatus() {
    return status;
  }

  public void setStatus(StatusCrud status) {
    this.status = status;
  }

  public DeliveryMen getNovoEntregador() {
    return novoEntregador;
  }

  public void setNovoEntregador(DeliveryMen novoEntregador) {
    this.novoEntregador = novoEntregador;
  }
  
}
