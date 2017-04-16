package online.mrsys.movierecommender.action;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.Role;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.service.UserManager;

public class RegisterAction extends BaseAction {

    private static final long serialVersionUID = -8649176431531359726L;
    
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
        User user = getUser();
        user.setMailVerified(false);
        user.setRole(Role.USER);
        int result = userManager.validRegister(user);
        if (result == UserManager.LOGIN_FAIL) {
            actionContext.getSession().put(WebConstant.INTERCEPT, "User is existed");
            return ERROR;
        }
        actionContext.getSession().put(WebConstant.USER, userManager.getUserBeanByAccount(user.getAccount()));
        if (result == UserManager.REGISTER_USER) {
            Cookie usrCookie = new Cookie(WebConstant.ACCOUNT, user.getAccount());
            Cookie pwdCookie = new Cookie(WebConstant.PASSWORD, user.getPassword());
            final int expiry = 7 * 24 * 60 * 60; // one week
            usrCookie.setMaxAge(expiry);
            pwdCookie.setMaxAge(expiry);
            ServletActionContext.getResponse().addCookie(usrCookie);
            ServletActionContext.getResponse().addCookie(pwdCookie);
        }
        return SUCCESS;
    }

}
