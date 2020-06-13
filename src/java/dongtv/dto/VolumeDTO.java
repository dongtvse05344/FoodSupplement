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

/**
 *
 * @author Tran Dong
 */
@Entity
@Table(name = "volumes", catalog = "FoodSupplementDB", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VolumeDTO.findAll", query = "SELECT v FROM VolumeDTO v"),
    @NamedQuery(name = "VolumeDTO.findById", query = "SELECT v FROM VolumeDTO v WHERE v.id = :id"),
    @NamedQuery(name = "VolumeDTO.findByL", query = "SELECT v FROM VolumeDTO v WHERE v.l = :l"),
    @NamedQuery(name = "VolumeDTO.findByW", query = "SELECT v FROM VolumeDTO v WHERE v.w = :w"),
    @NamedQuery(name = "VolumeDTO.findByH", query = "SELECT v FROM VolumeDTO v WHERE v.h = :h"),
    @NamedQuery(name = "VolumeDTO.findByProductId", query = "SELECT v FROM VolumeDTO v WHERE v.productId = :productId")
})
public class VolumeDTO implements Serializable, Comparable<VolumeDTO> {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "L", precision = 53)
    private Double l;
    @Column(name = "W", precision = 53)
    private Double w;
    @Column(name = "H", precision = 53)
    private Double h;
    @Column(name = "price", precision = 53)
    private Double price;
    @JoinColumn(name = "productId", referencedColumnName = "id", nullable = false)
    @ManyToOne(optional = false)
    private ProductDTO productId;

    public VolumeDTO() {
    }

    public VolumeDTO(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public ProductDTO getProductId() {
        return productId;
    }

    public void setProductId(ProductDTO productId) {
        this.productId = productId;
    }

    public void pushData(String data) {
        String[] datas = data.split("x");
        this.w = Double.parseDouble(datas[0]);
        this.l = Double.parseDouble(datas[1]);
        if(datas.length> 2) {
            this.h = Double.parseDouble(datas[2]);
        }
    }
    
    private double getVolumes() {
        if(this.h !=null && this.h >0) {
            return this.h *this.l *this.w;
        }
        return this.l * this.w;
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
        if (!(object instanceof VolumeDTO)) {
            return false;
        }
        VolumeDTO other = (VolumeDTO) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dongtv.dto.VolumeDTO[ id=" + id + " ]";
    }

    @Override
    public int compareTo(VolumeDTO o) {
        return (int) ((int) this.getVolumes() - o.getVolumes());
    }

}
