package online.mrsys.movierecommender.action.user;

import java.util.List;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.Rating;

public class DeleteAccountAction extends BaseAction {

    private static final long serialVersionUID = -2503295250787326579L;

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String execute() throws Exception {
        ActionContext actionContext = ActionContext.getContext();
        List<Rating> ratings = movieManager.getRatingsByUser(userManager.getUserById(getId()));
        for (Rating rating : ratings) {
            movieManager.deleteRating(rating.getId());
        }
        userManager.deleteUser(getId());
        actionContext.getSession().put(WebConstant.USER, null);
        Cookie[] cookies = ServletActionContext.getRequest().getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            ServletActionContext.getResponse().addCookie(cookie);
        }
        return SUCCESS;
    }

}
