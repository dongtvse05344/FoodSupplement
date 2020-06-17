/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.validation;

import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.bind.ValidationEventLocator;

/**
 *
 * @author Tran Dong
 */
public class ValidationHandler implements ValidationEventHandler {
    
    private StringBuilder errorMess;

    public ValidationHandler(StringBuilder errorMess) {
        this.errorMess = errorMess;
    }
    
    

    @Override
    public boolean handleEvent(ValidationEvent event) {
        if (event.getSeverity() == ValidationEvent.FATAL_ERROR
                || event.getSeverity() == ValidationEvent.ERROR) {
            ValidationEventLocator locator = event.getLocator();
            //print
            this.errorMess.append("Invalid booking document: " + locator.getURL()+"\n");

            this.errorMess.append( "Error: " + event.getMessage()+"\n");
            this.errorMess.append("Error at column "
                    + locator.getColumnNumber()
                    + ", line "
                    + locator.getLineNumber() +"\n");
        }
        return true;
    }
}
