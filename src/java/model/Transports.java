/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mdamaceno
 */
@Entity
@Table(catalog = "coelhorapido", name = "transports", schema = "")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Transports.findAll", query = "SELECT t FROM Transports t"),
  @NamedQuery(name = "Transports.findById", query = "SELECT t FROM Transports t WHERE t.id = :id"),
  @NamedQuery(name = "Transports.findByName", query = "SELECT t FROM Transports t WHERE t.name = :name")})
public class Transports implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(nullable = false)
  private Integer id;
  @Column(length = 255)
  private String name;

  public Transports() {
  }

  public Transports(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public int hashCode() {
    int hash = 0;
    hash += (id != null ? id.hashCode() : 0);
    return hash;
  }

  @Override
  public boolean equals(Object object) {
    // TODO: Warning - this method won't work in the case the id fields are not set
    if (!(object instanceof Transports)) {
      return false;
    }
    Transports other = (Transports) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "model.Transports[ id=" + id + " ]";
  }
  
}
