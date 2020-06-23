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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
    @NamedQuery(name = "ProductRawDTO.findAll", query = "SELECT p FROM ProductRawDTO p"), //order by p.name
    @NamedQuery(name = "ProductRawDTO.findAllWithSort", query = "SELECT p FROM ProductRawDTO p order by p.name"), //order by p.name
    @NamedQuery(name = "ProductRawDTO.findById", query = "SELECT p FROM ProductRawDTO p WHERE p.id = :id"),
    @NamedQuery(name = "ProductRawDTO.findByName", query = "SELECT p FROM ProductRawDTO p WHERE p.name = :name"),
    @NamedQuery(name = "ProductRawDTO.findByImage", query = "SELECT p FROM ProductRawDTO p WHERE p.image = :image"),
    @NamedQuery(name = "ProductRawDTO.findByOriginalLink", query = "SELECT p FROM ProductRawDTO p WHERE p.originalLink = :originalLink"),
    @NamedQuery(name = "ProductRawDTO.findByCategoryId", query = "SELECT p FROM ProductRawDTO p WHERE p.categoryId = :categoryId"),
    @NamedQuery(name = "ProductRawDTO.findByPrice", query = "SELECT p FROM ProductRawDTO p WHERE p.price = :price"),
    @NamedQuery(name = "ProductRawDTO.getTotalRows", query = "SELECT Count(p) FROM ProductRawDTO p"),
    @NamedQuery(name = "ProductRawDTO.deleteAll", query = "DELETE FROM ProductRawDTO p"),})
public class ProductRawDTO implements Serializable {

    @Column(name = "parentId")
    private Integer parentId;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dpg", precision = 53)
    private Double dpg;
    @Column(name = "iso", precision = 53)
    private Double iso;
    @Column(name = "fps", precision = 53)
    private Double fps;

    @Column(name = "description", length = 2147483647)
    private String description;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "name", length = 500)
    private String name;
    @Column(name = "image", length = 2147483647)
    private String image;
    @Column(name = "originalLink", length = 2147483647)
    private String originalLink;
    @JoinColumn(name = "categoryId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private CategoryDTO categoryId;
    @Column(name = "price")
    private Integer price;

    public ProductRawDTO() {
    }

    public ProductRawDTO(String name, String image, String originalLink, Integer price) {
        this.name = name;
        this.image = image;
        this.originalLink = originalLink;
        this.price = price;
    }

    public ProductRawDTO(Integer id, String name, String image, String originalLink, Integer price, Integer status) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.originalLink = originalLink;
        this.price = price;
    }

    public ProductRawDTO(Integer id) {
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

    public CategoryDTO getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(CategoryDTO categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    public Double getDpg() {
        return dpg;
    }

    public void setDpg(Double dpg) {
        this.dpg = dpg;
    }

    public Double getIso() {
        return iso;
    }

    public void setIso(Double iso) {
        this.iso = iso;
    }

    public Double getFps() {
        return fps;
    }

    public void setFps(Double fps) {
        this.fps = fps;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

}
