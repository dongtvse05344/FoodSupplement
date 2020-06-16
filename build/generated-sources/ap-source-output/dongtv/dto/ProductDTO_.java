package dongtv.dto;

import dongtv.dto.CategoryDTO;
import dongtv.dto.VolumeDTO;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-06-14T19:54:40")
@StaticMetamodel(ProductDTO.class)
public class ProductDTO_ { 

    public static volatile SingularAttribute<ProductDTO, String> originalLink;
    public static volatile SingularAttribute<ProductDTO, String> image;
    public static volatile SingularAttribute<ProductDTO, Integer> price;
    public static volatile SingularAttribute<ProductDTO, String> name;
    public static volatile SingularAttribute<ProductDTO, String> description;
    public static volatile SingularAttribute<ProductDTO, Integer> id;
    public static volatile CollectionAttribute<ProductDTO, VolumeDTO> volumeDTOCollection;
    public static volatile SingularAttribute<ProductDTO, CategoryDTO> categoryId;

}