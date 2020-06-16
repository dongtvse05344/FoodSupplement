package dongtv.dto;

import dongtv.dto.ProductDTO;
import dongtv.dto.ProductRawDTO;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-14T19:54:40")
@StaticMetamodel(CategoryDTO.class)
public class CategoryDTO_ { 

    public static volatile CollectionAttribute<CategoryDTO, ProductDTO> productDTOCollection;
    public static volatile CollectionAttribute<CategoryDTO, ProductRawDTO> productRawDTOCollection;
    public static volatile SingularAttribute<CategoryDTO, String> name;
    public static volatile SingularAttribute<CategoryDTO, Integer> id;

}