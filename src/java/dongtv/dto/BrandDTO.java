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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author shuu1
 */
@Entity
@Table(name = "brands", catalog = "FoodSupplementDB", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BrandDTO.findAll", query = "SELECT b FROM BrandDTO b"),
    @NamedQuery(name = "BrandDTO.findById", query = "SELECT b FROM BrandDTO b WHERE b.id = :id"),
    @NamedQuery(name = "BrandDTO.deleteAll", query = "DELETE FROM BrandDTO b"),
    @NamedQuery(name = "BrandDTO.findByName", query = "SELECT b FROM BrandDTO b WHERE b.name = :name")})
public class BrandDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "brandDTO1")

    @OneToMany(mappedBy = "brandId")
    private Collection<ProductDTO> productDTOCollection;

    public BrandDTO() {
    }

    public BrandDTO(Integer id) {
        this.id = id;
    }

    public BrandDTO(String name) {
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
        if (!(object instanceof BrandDTO)) {
            return false;
        }
        BrandDTO other = (BrandDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dongtv.dto.BrandDTO[ id=" + id + " ]";
    }

}
