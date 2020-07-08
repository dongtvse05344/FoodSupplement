/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dto.raw;

import dongtv.dto.ProductDTO;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "categories", catalog = "FoodSupplementDB", schema = "dbo")
@XmlRootElement(name = "category")
@NamedQueries({
    @NamedQuery(name = "CategoryDTO.findAll", query = "SELECT c FROM CategoryDTO c ORDER BY c.name"),
    @NamedQuery(name = "CategoryDTO.findById", query = "SELECT c FROM CategoryDTO c WHERE c.id = :id"),
    @NamedQuery(name = "CategoryDTO.findByName", query = "SELECT c FROM CategoryDTO c WHERE c.name = :name"),
    @NamedQuery(name = "CategoryDTO.getTotalRows", query = "SELECT Count(c) FROM CategoryDTO c")

})
public class CategoryDTO implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoryId")
    private Collection<ProductRawDTO> productRawDTOCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name", length = 255)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "categoryId")
    private Collection<ProductDTO> productDTOCollection;

    public CategoryDTO(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public CategoryDTO() {
    }

    public CategoryDTO(Integer id) {
        this.id = id;
    }

    public CategoryDTO(String name) {
        this.name = name;
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

    @XmlTransient
    public Collection<ProductDTO> getProductDTOCollection() {
        return productDTOCollection;
    }

    public void setProductDTOCollection(Collection<ProductDTO> productDTOCollection) {
        this.productDTOCollection = productDTOCollection;
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
        if (!(object instanceof CategoryDTO)) {
            return false;
        }
        CategoryDTO other = (CategoryDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dongtv.dto.CategoryDTO[ id=" + id + " ]";
    }

    @XmlTransient
    public Collection<ProductRawDTO> getProductRawDTOCollection() {
        return productRawDTOCollection;
    }

    public void setProductRawDTOCollection(Collection<ProductRawDTO> productRawDTOCollection) {
        this.productRawDTOCollection = productRawDTOCollection;
    }

}
