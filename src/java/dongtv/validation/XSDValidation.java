/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.validation;

import dongtv.dto.raw.ProductRawsDTO;
import dongtv.dto.ProductsDTO;
import java.io.File;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;

/**
 *
 * @author Tran Dong
 */
public class XSDValidation {

    public static ProductRawsDTO validation(String filePath, String xsdPath, StringBuilder erString) throws JAXBException, SAXException {
        JAXBContext jc = JAXBContext.newInstance(ProductRawsDTO.class);
        Unmarshaller u = jc.createUnmarshaller();

        SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Schema schema = sf.newSchema(new File(xsdPath));
        u.setEventHandler(new ValidationHandler(erString));
        u.setSchema(schema);
//            File f = new File("src\\java\\console/customer.xml");
        File f = new File(filePath);
        return (ProductRawsDTO) u.unmarshal(f);
    }
}
