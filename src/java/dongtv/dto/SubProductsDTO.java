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
 * @author Tran Dong
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "products")
public class SubProductsDTO {
    @XmlElement(name = "product")
    private List<SubProductDTO> productDTOs;

    public List<SubProductDTO> getProductDTOs() {
        return productDTOs;
    }

    public void setProductDTOs(List<SubProductDTO> productDTOs) {
        this.productDTOs = productDTOs;
    }

   
}
