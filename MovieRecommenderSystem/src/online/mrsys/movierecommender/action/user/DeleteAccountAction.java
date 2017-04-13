package online.mrsys.movierecommender.action.user;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.User;

public class DeleteAccountAction extends BaseAction {

    private static final long serialVersionUID = 7477967349488351831L;
    
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String execute() throws Exception {
        ActionContext actionContext = ActionContext.getContext();
        if (userManager.deleteUser(getUser())) {
            actionContext.getSession().put(WebConstant.USER, null);
            Cookie[] cookies = ServletActionContext.getRequest().getCookies();
            for (Cookie cookie : cookies) {
                cookie.setMaxAge(0);
                ServletActionContext.getResponse().addCookie(cookie);
            }
            return SUCCESS;
        }
        actionContext.getSession().put(WebConstant.INTERCEPT, "Error in deleting account");
        return ERROR;
    }

}
