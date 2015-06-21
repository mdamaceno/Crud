/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.Clients;
import model.dao.ClientsJpaController;

/**
 *
 * @author mdamaceno
 */
@ManagedBean(name = "login")
@ViewScoped
public class LoginManager {

  private String email, senha;
  private EntityManagerFactory emf;
  private Clients cliente;

  /**
   * Creates a new instance of LoginManager
   */
  public LoginManager() {
    emf = Persistence.createEntityManagerFactory("CrudPU");
  }

  public String loginAction() {
    cliente = new ClientsJpaController(emf).findByEmailAndSenha(email, senha);

    if (cliente == null) {
      FacesContext.getCurrentInstance().addMessage(null,
              new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error!", "Login ou Senha Incorretos"));
      return "";
    } else {
      if (cliente.getPermission() == 1) {
        return "index";
      } else {
        return "index";
      }
    }
  }

  public String sair() {
    cliente = null;
    return "index";
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getSenha() {
    return senha;
  }

  public void setSenha(String senha) {
    this.senha = senha;
  }

  public Clients getCliente() {
    return cliente;
  }

  public void setCliente(Clients cliente) {
    this.cliente = cliente;
  }

}
