/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.validation;

import dongtv.dto.ProductRawsDTO;
import dongtv.dto.ProductsDTO;
import java.io.File;
import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.Validator;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author Tran Dong
 */
public class XSDValidation {

    public static ProductsDTO validation(String filePath, String xsdPath) throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ProductsDTO.class);
        Unmarshaller u = jc.createUnmarshaller();

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(xsdPath));
        u.setEventHandler(new ValidationHandler());
        u.setSchema(schema);
//            File f = new File("src\\java\\console/customer.xml");
        File f = new File(filePath);
        return (ProductsDTO) u.unmarshal(f);
    }
}
