/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dto;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Tran Dong
 */
@Entity
@Table(name = "products", catalog = "FoodSupplementDB", schema = "dbo")
@XmlRootElement(name = "product")
@NamedQueries({
    @NamedQuery(name = "ProductDTO.findAll", query = "SELECT p FROM ProductDTO p"),
    @NamedQuery(name = "ProductDTO.findById", query = "SELECT p FROM ProductDTO p WHERE p.id = :id"),
    @NamedQuery(name = "ProductDTO.findByName", query = "SELECT p FROM ProductDTO p WHERE p.name = :name"),
    @NamedQuery(name = "ProductDTO.findByCategoryId", query = "SELECT p FROM ProductDTO p WHERE p.categoryId = :categoryId"),
})
public class ProductDTO implements Serializable {

    @Column(name = "price")
    private Integer price;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", length = 500)
    private String name;
    @Column(name = "description", length = 2147483647)
    private String description;
    @Column(name = "image", length = 2147483647)
    private String image;
    @Column(name = "originalLink", length = 2147483647)
    private String originalLink;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<VolumeDTO> volumeDTOCollection;
    @JoinColumn(name = "categoryId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private CategoryDTO categoryId;

    public ProductDTO() {
    }

    public ProductDTO(Integer price, String name, String image, String originalLink) {
        this.price = price;
        this.name = name;
        this.image = image;
        this.originalLink = originalLink;
    }
    

    public ProductDTO(Integer id) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    @XmlTransient
    public Collection<VolumeDTO> getVolumeDTOCollection() {
        return volumeDTOCollection;
    }

    public void setVolumeDTOCollection(Collection<VolumeDTO> volumeDTOCollection) {
        this.volumeDTOCollection = volumeDTOCollection;
    }

    public CategoryDTO getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(CategoryDTO categoryId) {
        this.categoryId = categoryId;
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
        if (!(object instanceof ProductDTO)) {
            return false;
        }
        ProductDTO other = (ProductDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dongtv.dto.ProductDTO[ id=" + id + " ]";
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
    
}
