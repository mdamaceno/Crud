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
@Table(catalog = "coelhorapido", schema = "")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Whereis.findAll", query = "SELECT w FROM Whereis w"),
  @NamedQuery(name = "Whereis.findById", query = "SELECT w FROM Whereis w WHERE w.id = :id"),
  @NamedQuery(name = "Whereis.findByLatitude", query = "SELECT w FROM Whereis w WHERE w.latitude = :latitude"),
  @NamedQuery(name = "Whereis.findByLongitude", query = "SELECT w FROM Whereis w WHERE w.longitude = :longitude"),
  @NamedQuery(name = "Whereis.findByDeliveryId", query = "SELECT w FROM Whereis w WHERE w.deliveryId = :deliveryId")})
public class Whereis implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(nullable = false)
  private Integer id;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Column(precision = 12)
  private Float latitude;
  @Column(precision = 12)
  private Float longitude;
  @Column(name = "delivery_id")
  private Integer deliveryId;

  public Whereis() {
  }

  public Whereis(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Float getLatitude() {
    return latitude;
  }

  public void setLatitude(Float latitude) {
    this.latitude = latitude;
  }

  public Float getLongitude() {
    return longitude;
  }

  public void setLongitude(Float longitude) {
    this.longitude = longitude;
  }

  public Integer getDeliveryId() {
    return deliveryId;
  }

  public void setDeliveryId(Integer deliveryId) {
    this.deliveryId = deliveryId;
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
    if (!(object instanceof Whereis)) {
      return false;
    }
    Whereis other = (Whereis) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "model.Whereis[ id=" + id + " ]";
  }
  
}
