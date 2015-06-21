/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Clients;
import model.Deliveries;
import model.DeliveryMen;
import model.Transports;
import model.dao.ClientsJpaController;
import model.dao.DeliveriesJpaController;
import model.dao.DeliveryMenJpaController;
import model.dao.TransportsJpaController;
import model.dao.exceptions.NonexistentEntityException;

/**
 *
 * @author mdamaceno
 */
@ManagedBean(name = "entregas")
@ViewScoped
public class EntregasManager {

  private final EntityManagerFactory emf;
  private StatusCrud status;
  private Deliveries novaEntrega;
  private int idCliente, idTransporte, idEntregador;

  /**
   * Creates a new instance of EntregasManager
   */
  public EntregasManager() {
    emf = Persistence.createEntityManagerFactory("CrudPU");
    status = StatusCrud.none;
  }

  public String callCadEntregas() {
    return "cadEntregaList";
  }

  public String gravaEntrega() {
    if (status == StatusCrud.insert) {
      novaEntrega.setClientId(new ClientsJpaController(emf).findClients(idCliente));
      novaEntrega.setTransportId(new TransportsJpaController(emf).findTransports(idTransporte));
      novaEntrega.setDeliveryManId(new DeliveryMenJpaController(emf).findDeliveryMen(idEntregador));

      new DeliveriesJpaController(emf).create(novaEntrega);
    } else if (status == StatusCrud.edit) {
      try {
        new DeliveriesJpaController(emf).edit(novaEntrega);
      } catch (Exception ex) {
        Logger.getLogger(EntregasManager.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    status = StatusCrud.none;
    return null;
  }

  public DataModel<Deliveries> getListTable() {
    return new ListDataModel<>(new DeliveriesJpaController(emf).findDeliveriesEntities());
  }

  public String editarEntrega(Deliveries dl) {
    novaEntrega = dl;
    status = StatusCrud.edit;
    return null;
  }

  public String deletarEntrega(Deliveries dl) {
    try {
      new DeliveriesJpaController(emf).destroy(dl.getId());
    } catch (NonexistentEntityException ex) {
      Logger.getLogger(EntregasManager.class.getName()).log(Level.SEVERE, null, ex);
    }

    status = StatusCrud.none;
    return null;
  }

  public String verEntrega(Deliveries dl) {
    novaEntrega = dl;
    status = StatusCrud.view;
    return null;
  }

  public String novaEntregaCad() {
    status = StatusCrud.insert;
    novaEntrega = new Deliveries();
    novaEntrega.setClientId(new Clients());
    novaEntrega.setTransportId(new Transports());
    novaEntrega.setDeliveryManId(new DeliveryMen());
    return null;
  }

  public List<Clients> getListaClientes() {
    return new ClientsJpaController(emf).findClientsEntities();
  }

  public List<Transports> getListaTransportes() {
    return new TransportsJpaController(emf).findTransportsEntities();
  }

  public List<DeliveryMen> getListaEntregadores() {
    return new DeliveryMenJpaController(emf).findDeliveryMenEntities();
  }

  public StatusCrud getStatus() {
    return status;
  }

  public void setStatus(StatusCrud status) {
    this.status = status;
  }

  public Deliveries getNovaEntrega() {
    return novaEntrega;
  }

  public void setNovaEntrega(Deliveries novaEntrega) {
    this.novaEntrega = novaEntrega;
  }

  public int getIdCliente() {
    return idCliente;
  }

  public void setIdCliente(int idCliente) {
    this.idCliente = idCliente;
  }

  public int getIdTransporte() {
    return idTransporte;
  }

  public void setIdTransporte(int idTransporte) {
    this.idTransporte = idTransporte;
  }

  public int getIdEntregador() {
    return idEntregador;
  }

  public void setIdEntregador(int idEntregador) {
    this.idEntregador = idEntregador;
  }

}
