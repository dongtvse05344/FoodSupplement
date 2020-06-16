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
public class ProductRawsDTO {
    @XmlElement(name = "product")
    private List<ProductRawDTO> productDTOs;

    public List<ProductRawDTO> getProductDTOs() {
        return productDTOs;
    }

    public void setProductDTOs(List<ProductRawDTO> productDTOs) {
        this.productDTOs = productDTOs;
    }

   
    
}