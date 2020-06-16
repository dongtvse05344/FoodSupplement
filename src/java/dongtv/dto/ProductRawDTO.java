/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dto;

import dongtv.contanst.ProductStatus;
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
 * @author Tran Dong
 */
@Entity
@Table(name = "product_raws", catalog = "FoodSupplementDB", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ProductRawDTO.findAll", query = "SELECT p FROM ProductRawDTO p"),
    @NamedQuery(name = "ProductRawDTO.findById", query = "SELECT p FROM ProductRawDTO p WHERE p.id = :id"),
    @NamedQuery(name = "ProductRawDTO.findByRawxml", query = "SELECT p FROM ProductRawDTO p WHERE p.rawxml = :rawxml"),
    @NamedQuery(name = "ProductRawDTO.findByName", query = "SELECT p FROM ProductRawDTO p WHERE p.name = :name"),
    @NamedQuery(name = "ProductRawDTO.findByImage", query = "SELECT p FROM ProductRawDTO p WHERE p.image = :image"),
    @NamedQuery(name = "ProductRawDTO.findByOriginalLink", query = "SELECT p FROM ProductRawDTO p WHERE p.originalLink = :originalLink"),
    @NamedQuery(name = "ProductRawDTO.findByCategoryId", query = "SELECT p FROM ProductRawDTO p WHERE p.categoryId = :categoryId"),
    @NamedQuery(name = "ProductRawDTO.findByPrice", query = "SELECT p FROM ProductRawDTO p WHERE p.price = :price"),
    @NamedQuery(name = "ProductRawDTO.findByStatus", query = "SELECT p FROM ProductRawDTO p WHERE p.status = :status"),
    @NamedQuery(name = "ProductRawDTO.getTotalRows", query = "SELECT Count(p) FROM ProductRawDTO p"),
    @NamedQuery(name = "ProductRawDTO.deleteAll", query = "DELETE FROM ProductRawDTO p"),
})
public class ProductRawDTO implements Serializable {

    @Column(name = "description", length = 2147483647)
    private String description;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "rawxml", length = 2147483647)
    private String rawxml;
    @Column(name = "name", length = 500)
    private String name;
    @Column(name = "image", length = 2147483647)
    private String image;
    @Column(name = "originalLink", length = 2147483647)
    private String originalLink;
    @Column(name = "categoryId")
    private Integer categoryId;
    @Column(name = "price")
    private Integer price;
    @Basic(optional = false)
    @Column(name = "status", nullable = false)
    private int status;

    public ProductRawDTO() {
    }

    public ProductRawDTO(String rawxml, String name, String image, String originalLink, Integer price) {
        this.rawxml = rawxml;
        this.name = name;
        this.image = image;
        this.originalLink = originalLink;
        this.price = price;
        this.status = ProductStatus.NEW.getValue();
    }

    public ProductRawDTO(Integer id, String name, String image, String originalLink, Integer price, Integer status) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.originalLink = originalLink;
        this.price = price;
        this.status = status;
    }

    public ProductRawDTO(Integer id) {
        this.id = id;
    }

    public ProductRawDTO(Integer id, int status) {
        this.id = id;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRawxml() {
        return rawxml;
    }

    public void setRawxml(String rawxml) {
        this.rawxml = rawxml;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getOriginalLink() {
        return originalLink;
    }

    public void setOriginalLink(String originalLink) {
        this.originalLink = originalLink;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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
        if (!(object instanceof ProductRawDTO)) {
            return false;
        }
        ProductRawDTO other = (ProductRawDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dongtv.dto.ProductRawDTO[ id=" + id + " ]";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
