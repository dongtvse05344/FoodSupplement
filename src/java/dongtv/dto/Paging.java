/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dto;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tran Dong
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "page")
public class Paging implements Serializable{
    private int current;
    private int max;
    private int min;
    private int total;
    private int rowsOfPage;
    private int[] loop;
    private String action;
    private String txtSearch;
    private String type;
    private String cateId;

    public Paging() {
    }

    
    
    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Paging(int current,int total,int rowsOfPage, String action, String txtSearch,String type, String cateId) {
        this.current = current;
        this.total = total;
        this.action = action;
        this.rowsOfPage = rowsOfPage;
        this.txtSearch = txtSearch;
        this.type = type;
        this.cateId = cateId;
        min = current -2;
        min = Math.max(1, min);
        max = min + 4 ;
        max = Math.min((int) Math.ceil(1.0*total/rowsOfPage), max);
        loop = new int[max-min+1];
        for(int i = min;i<=max;i++) {
            loop[i-min] = i;
        }
    }
    
    
    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMin() {
        return min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
    
}
