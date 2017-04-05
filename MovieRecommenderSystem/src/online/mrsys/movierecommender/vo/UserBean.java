package online.mrsys.movierecommender.vo;

import java.io.Serializable;
import java.util.List;

import online.mrsys.movierecommender.function.Serializer;

public class UserBean implements Serializable {

    private static final long serialVersionUID = -2989704113036651774L;

    private Integer id;
    private String account;
    private String email;
    private Boolean mailVerified;
    private List<MovieBean> recommendation;
    private RoleBean role;

    public UserBean() {
    }

    public UserBean(Integer id, String account, String email, Boolean mailVerified, List<MovieBean> recommendation,
            RoleBean role) {
        super();
        this.id = id;
        this.account = account;
        this.email = email;
        this.mailVerified = mailVerified;
        this.recommendation = recommendation;
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

    public List<MovieBean> getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(List<MovieBean> recommendation) {
        this.recommendation = recommendation;
    }

    public RoleBean getRole() {
        return role;
    }

    public void setRole(RoleBean role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return new String(Serializer.serialize(this));
    }

}
