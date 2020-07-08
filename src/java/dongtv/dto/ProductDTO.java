/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dto;

import dongtv.dto.raw.CategoryDTO;
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
    @NamedQuery(name = "ProductDTO.findAll", query = "SELECT p FROM ProductDTO p "),
    @NamedQuery(name = "ProductDTO.findByName", query = "SELECT p FROM ProductDTO p WHERE p.name LIKE :name"),
    @NamedQuery(name = "ProductDTO.findById", query = "SELECT p FROM ProductDTO p WHERE p.id = :id"),
    @NamedQuery(name = "ProductDTO.findByCateId", query = "SELECT p FROM ProductDTO p WHERE p.categoryId = :cateId"),
    @NamedQuery(name = "ProductDTO.findRowByCateId", query = "SELECT COUNT(p) FROM ProductDTO p WHERE p.categoryId = :cateId"),

    @NamedQuery(name = "ProductDTO.findByBrandId", query = "SELECT p FROM ProductDTO p WHERE p.brandId = :brandId"),
    @NamedQuery(name = "ProductDTO.findRowByBrandId", query = "SELECT COUNT(p) FROM ProductDTO p WHERE p.brandId = :brandId"),

    @NamedQuery(name = "ProductDTO.findTopAll", query = "SELECT p FROM ProductDTO p WHERE p.name LIKE :name ORDER BY p.qDpg + p.qIso + p.qFps DESC"), //ORDER BY p.qDpg + p.qIso + p.qFps DESC
    @NamedQuery(name = "ProductDTO.findTopDpg", query = "SELECT p FROM ProductDTO p WHERE p.name LIKE :name ORDER BY p.qDpg DESC "), //ORDER BY p.qDpg DESC
    @NamedQuery(name = "ProductDTO.findTopIso", query = "SELECT p FROM ProductDTO p WHERE p.name LIKE :name ORDER BY p.qIso DESC"), //ORDER BY p.qIso DESC
    @NamedQuery(name = "ProductDTO.findTopFps", query = "SELECT p FROM ProductDTO p WHERE p.name LIKE :name ORDER BY p.qFps DESC"), //ORDER BY p.qFps DESC

    @NamedQuery(name = "ProductDTO.getTotalRows", query = "SELECT Count(p) FROM ProductDTO p WHERE p.name  LIKE :name"),
    @NamedQuery(name = "ProductDTO.getMeanDpg", query = "SELECT SUM(p.dpg)/COUNT(p.dpg) FROM ProductDTO p"),
    @NamedQuery(name = "ProductDTO.getExxDpg", query = "SELECT SUM(p.dpg*p.dpg)/COUNT(p.dpg) FROM ProductDTO p"),
    @NamedQuery(name = "ProductDTO.getMeanIso", query = "SELECT SUM(p.iso)/COUNT(p.iso) FROM ProductDTO p"),
    @NamedQuery(name = "ProductDTO.getExxIso", query = "SELECT SUM(p.iso*p.iso)/COUNT(p.iso) FROM ProductDTO p"),
    @NamedQuery(name = "ProductDTO.getMeanFps", query = "SELECT SUM(p.fps)/COUNT(p.fps) FROM ProductDTO p"),
    @NamedQuery(name = "ProductDTO.getExxFps", query = "SELECT SUM(p.fps*p.fps)/COUNT(p.fps) FROM ProductDTO p"),})
public class ProductDTO implements Serializable, Cloneable {

    @JoinColumn(name = "brandId", referencedColumnName = "id")
    @ManyToOne
    private BrandDTO brandId;

    @Column(name = "display", precision = 53)
    private Double display;

    public Object clone() throws
            CloneNotSupportedException {
        return super.clone();
    }

    @Column(name = "qDpg", precision = 53)
    private Double qDpg;
    @Column(name = "qIso", precision = 53)
    private Double qIso;
    @Column(name = "qFps", precision = 53)
    private Double qFps;
    @Column(name = "qTan", precision = 53)
    private Double qTan;

    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "dpg", precision = 53)
    private Double dpg;
    @Column(name = "iso", precision = 53)
    private Double iso;
    @Column(name = "fps", precision = 53)
    private Double fps;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "productId")
    private Collection<SubProductDTO> subProductDTOCollection;

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
    @JoinColumn(name = "categoryId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private CategoryDTO categoryId;

    public ProductDTO() {
    }

    public ProductDTO(Integer price, Integer id, String name, String description, String image, String originalLink, CategoryDTO categoryId) {
        this.price = price;
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.originalLink = originalLink;
        this.categoryId = categoryId;
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
        return "dongtv.dto.ProductDTO[ dpg=" + qDpg + " iso=" + qIso + " fps=" + qFps + " ]"
                + "dongtv.dto.ProductDTO[ dpg=" + dpg + " iso=" + iso + " fps=" + fps + " ]";

    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
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

    @XmlTransient
    public Collection<SubProductDTO> getSubProductDTOCollection() {
        return subProductDTOCollection;
    }

    public void setSubProductDTOCollection(Collection<SubProductDTO> subProductDTOCollection) {
        this.subProductDTOCollection = subProductDTOCollection;
    }

    public Double getQDpg() {
        return qDpg;
    }

    public void setQDpg(Double qDpg) {
        this.qDpg = qDpg;
    }

    public Double getQIso() {
        return qIso;
    }

    public void setQIso(Double qIso) {
        this.qIso = qIso;
    }

    public Double getQFps() {
        return qFps;
    }

    public void setQFps(Double qFps) {
        this.qFps = qFps;
    }

    public Double getQTan() {
        return qTan;
    }

    public void setQTan(Double qTan) {
        this.qTan = qTan;
    }

    public Double getDisplay() {
        return display;
    }

    public void setDisplay(Double display) {
        this.display = display;
    }

    public BrandDTO getBrandId() {
        return brandId;
    }

    public void setBrandId(BrandDTO brandId) {
        this.brandId = brandId;
    }
}
