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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mdamaceno
 */
@Entity
@Table(catalog = "coelhorapido", schema = "", name = "deliveries")
@XmlRootElement
@NamedQueries({
  @NamedQuery(name = "Deliveries.findAll", query = "SELECT d FROM Deliveries d"),
  @NamedQuery(name = "Deliveries.findById", query = "SELECT d FROM Deliveries d WHERE d.id = :id"),
  @NamedQuery(name = "Deliveries.findByProductName", query = "SELECT d FROM Deliveries d WHERE d.productName = :productName"),
  @NamedQuery(name = "Deliveries.findByDestinationAddress", query = "SELECT d FROM Deliveries d WHERE d.destinationAddress = :destinationAddress"),
  @NamedQuery(name = "Deliveries.findByPrice", query = "SELECT d FROM Deliveries d WHERE d.price = :price"),
  @NamedQuery(name = "Deliveries.findByDistance", query = "SELECT d FROM Deliveries d WHERE d.distance = :distance"),
  @NamedQuery(name = "Deliveries.findByTransportId", query = "SELECT d FROM Deliveries d WHERE d.transportId = :transportId"),
  @NamedQuery(name = "Deliveries.findByDeliveryManId", query = "SELECT d FROM Deliveries d WHERE d.deliveryManId = :deliveryManId"),
  @NamedQuery(name = "Deliveries.findByClientId", query = "SELECT d FROM Deliveries d WHERE d.clientId = :clientId")})
public class Deliveries implements Serializable {
  private static final long serialVersionUID = 1L;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Basic(optional = false)
  @Column(nullable = false)
  private Integer id;
  @Column(name = "product_name", length = 255)
  private String productName;
  @Column(name = "destination_address", length = 255)
  private String destinationAddress;
  // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
  @Column(precision = 12, name = "price")
  private Float price;
  @Column(precision = 12, name = "distance")
  private Float distance;
  @JoinColumn(name = "client_id", referencedColumnName = "id", nullable = false)
  @ManyToOne(optional = false)
  private Clients clientId;
  @JoinColumn(name = "delivery_man_id", referencedColumnName = "id", nullable = false)
  @ManyToOne(optional = false)
  private DeliveryMen deliveryManId;
  @JoinColumn(name = "transport_id", referencedColumnName = "id", nullable = false)
  @ManyToOne(optional = false)
  private Transports transportId;

  public Deliveries() {
  }

  public Deliveries(Integer id) {
    this.id = id;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getDestinationAddress() {
    return destinationAddress;
  }

  public void setDestinationAddress(String destinationAddress) {
    this.destinationAddress = destinationAddress;
  }

  public Float getPrice() {
    return price;
  }

  public void setPrice(Float price) {
    this.price = price;
  }

  public Float getDistance() {
    return distance;
  }

  public void setDistance(Float distance) {
    this.distance = distance;
  }

  public Transports getTransportId() {
    return transportId;
  }

  public void setTransportId(Transports transportId) {
    this.transportId = transportId;
  }

  public DeliveryMen getDeliveryManId() {
    return deliveryManId;
  }

  public void setDeliveryManId(DeliveryMen deliveryManId) {
    this.deliveryManId = deliveryManId;
  }

  public Clients getClientId() {
    return clientId;
  }

  public void setClientId(Clients clientId) {
    this.clientId = clientId;
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
    if (!(object instanceof Deliveries)) {
      return false;
    }
    Deliveries other = (Deliveries) object;
    if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "model.Deliveries[ id=" + id + " ]";
  }
  
}
