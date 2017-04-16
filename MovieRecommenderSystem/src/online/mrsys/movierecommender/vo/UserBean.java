package online.mrsys.movierecommender.vo;

import java.io.Serializable;

import online.mrsys.movierecommender.util.Serializer;

public class UserBean implements Serializable {
    
    private static final long serialVersionUID = 1034682106851713781L;
    
    private Integer id;
    private String account;
    private String email;
    private Boolean mailVerified;
    private RoleBean role;

    public UserBean() {
    }

    public UserBean(Integer id, String account, String email, Boolean mailVerified, RoleBean role) {
        super();
        this.id = id;
        this.account = account;
        this.email = email;
        this.mailVerified = mailVerified;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getMailVerified() {
        return mailVerified;
    }

    public void setMailVerified(Boolean mailVerified) {
        this.mailVerified = mailVerified;
    }

    public RoleBean getRole() {
        return role;
    }

    public void setRole(RoleBean role) {
        this.role = role;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserBean other = (UserBean) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return new String(Serializer.serialize(this));
    }

}
