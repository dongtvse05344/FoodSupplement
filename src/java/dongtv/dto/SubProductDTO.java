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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Tran Dong
 */
@Entity
@Table(name = "subproducts", catalog = "FoodSupplementDB", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "SubProductDTO.findAll", query = "SELECT s FROM SubProductDTO s"),
    @NamedQuery(name = "SubProductDTO.findById", query = "SELECT s FROM SubProductDTO s WHERE s.id = :id"),
    @NamedQuery(name = "SubProductDTO.findByProductId", query = "SELECT s FROM SubProductDTO s WHERE s.productId = :id"),
    @NamedQuery(name = "SubProductDTO.findByName", query = "SELECT s FROM SubProductDTO s WHERE s.name = :name"),
    @NamedQuery(name = "SubProductDTO.findByPrice", query = "SELECT s FROM SubProductDTO s WHERE s.price = :price")})
public class SubProductDTO implements Serializable {

    @Column(name = "originalLink", length = 2147483647)
    private String originalLink;
    @Column(name = "image", length = 2147483647)
    private String image;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", length = 2147483647)
    private String name;
    @Column(name = "price")
    private Integer price;
    @JoinColumn(name = "productId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private ProductDTO productId;

    public SubProductDTO() {
    }

    public SubProductDTO(Integer id) {
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

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @XmlTransient
    public ProductDTO getProductId() {
        return productId;
    }

    public void setProductId(ProductDTO productId) {
        this.productId = productId;
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
        if (!(object instanceof SubProductDTO)) {
            return false;
        }
        SubProductDTO other = (SubProductDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dongtv.dto.SubProductDTO[ id=" + id + " ]";
    }

    public String getOriginalLink() {
        return originalLink;
    }

    public void setOriginalLink(String originalLink) {
        this.originalLink = originalLink;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

}
