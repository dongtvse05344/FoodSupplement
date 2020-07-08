/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author shuu1
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "brands")
public class BrandsDTO {

    @XmlElement(name = "brand")
    List<BrandDTO> brandDTO;

    public String getSelected() {
        return selected;
    }

    public void setSelected(String selected) {
        this.selected = selected;
    }
    String selected;

    public List<BrandDTO> getBrandDTO() {
        return brandDTO;
    }

    public void setBrandDTO(List<BrandDTO> brandDTO) {
        this.brandDTO = brandDTO;
    }

}
