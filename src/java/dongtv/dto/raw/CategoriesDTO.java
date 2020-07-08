/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dto.raw;

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
@XmlRootElement(name = "categories")
public class CategoriesDTO { 
    @XmlElement(name = "category")
    List<CategoryDTO> categoryDTOs;

    public List<CategoryDTO> getCategoryDTOs() {
        return categoryDTOs;
    }

    public void setCategoryDTOs(List<CategoryDTO> categoryDTOs) {
        this.categoryDTOs = categoryDTOs;
    }
}