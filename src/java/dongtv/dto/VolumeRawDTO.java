/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tran Dong
 */
@Entity
@Table(name = "volume_raws", catalog = "FoodSupplementDB", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VolumeRawDTO.findAll", query = "SELECT v FROM VolumeRawDTO v"),
    @NamedQuery(name = "VolumeRawDTO.findById", query = "SELECT v FROM VolumeRawDTO v WHERE v.id = :id"),
    @NamedQuery(name = "VolumeRawDTO.findByProductRawId", query = "SELECT v FROM VolumeRawDTO v WHERE v.productRawId = :productRawId"),
    @NamedQuery(name = "VolumeRawDTO.findByL", query = "SELECT v FROM VolumeRawDTO v WHERE v.l = :l"),
    @NamedQuery(name = "VolumeRawDTO.findByW", query = "SELECT v FROM VolumeRawDTO v WHERE v.w = :w"),
    @NamedQuery(name = "VolumeRawDTO.findByH", query = "SELECT v FROM VolumeRawDTO v WHERE v.h = :h"),
    @NamedQuery(name = "VolumeRawDTO.findByPrice", query = "SELECT v FROM VolumeRawDTO v WHERE v.price = :price"),
    @NamedQuery(name = "VolumeRawDTO.findByXmlRaw", query = "SELECT v FROM VolumeRawDTO v WHERE v.xmlRaw = :xmlRaw"),
    @NamedQuery(name = "VolumeRawDTO.findByStatus", query = "SELECT v FROM VolumeRawDTO v WHERE v.status = :status")})
public class VolumeRawDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "product_raw_id")
    private Integer productRawId;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "L", precision = 53)
    private Double l;
    @Column(name = "W", precision = 53)
    private Double w;
    @Column(name = "H", precision = 53)
    private Double h;
    @Column(name = "price", precision = 53)
    private Double price;
    @Column(name = "xml_raw", length = 2147483647)
    private String xmlRaw;
    @Basic(optional = false)
    @Column(name = "status", nullable = false)
    private int status;

    public VolumeRawDTO() {
    }

    public VolumeRawDTO(Integer id) {
        this.id = id;
    }

    public VolumeRawDTO(Integer id, int status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductRawId() {
        return productRawId;
    }

    public void setProductRawId(Integer productRawId) {
        this.productRawId = productRawId;
    }

    public Double getL() {
        return l;
    }

    public void setL(Double l) {
        this.l = l;
    }

    public Double getW() {
        return w;
    }

    public void setW(Double w) {
        this.w = w;
    }

    public Double getH() {
        return h;
    }

    public void setH(Double h) {
        this.h = h;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getXmlRaw() {
        return xmlRaw;
    }

    public void setXmlRaw(String xmlRaw) {
        this.xmlRaw = xmlRaw;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
        if (!(object instanceof VolumeRawDTO)) {
            return false;
        }
        VolumeRawDTO other = (VolumeRawDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dongtv.dto.VolumeRawDTO[ id=" + id + " ]";
    }
    
}
