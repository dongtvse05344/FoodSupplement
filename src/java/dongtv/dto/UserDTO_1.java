/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dongtv.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Tran Dong
 */
@Entity
@Table(name = "users", catalog = "FoodSupplementDB", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserDTO_1.findAll", query = "SELECT u FROM UserDTO_1 u"),
    @NamedQuery(name = "UserDTO_1.findById", query = "SELECT u FROM UserDTO_1 u WHERE u.id = :id"),
    @NamedQuery(name = "UserDTO_1.findByUsername", query = "SELECT u FROM UserDTO_1 u WHERE u.username = :username"),
    @NamedQuery(name = "UserDTO_1.findByPassword", query = "SELECT u FROM UserDTO_1 u WHERE u.password = :password"),
    @NamedQuery(name = "UserDTO_1.findByFullname", query = "SELECT u FROM UserDTO_1 u WHERE u.fullname = :fullname"),
    @NamedQuery(name = "UserDTO_1.findByIsAdmin", query = "SELECT u FROM UserDTO_1 u WHERE u.isAdmin = :isAdmin")})
public class UserDTO_1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "username", length = 250)
    private String username;
    @Column(name = "password", length = 250)
    private String password;
    @Column(name = "fullname", length = 250)
    private String fullname;
    @Basic(optional = false)
    @Column(name = "isAdmin", nullable = false)
    private boolean isAdmin;

    public UserDTO_1() {
    }

    public UserDTO_1(Integer id) {
        this.id = id;
    }

    public UserDTO_1(Integer id, boolean isAdmin) {
        this.id = id;
        this.isAdmin = isAdmin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserDTO_1)) {
            return false;
        }
        UserDTO_1 other = (UserDTO_1) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dongtv.dto.UserDTO_1[ id=" + id + " ]";
    }
    
}
