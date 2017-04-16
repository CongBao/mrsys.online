package online.mrsys.movierecommender.action.user;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.service.UserManager;

public class EditPasswordAction extends BaseAction {

    private static final long serialVersionUID = 2341137608466154567L;
    
    private User user;
    private String newPassword;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    @Override
    public String execute() throws Exception {
        ActionContext actionContext = ActionContext.getContext();
        int result = userManager.validLogin(getUser());
        if (result == UserManager.LOGIN_FAIL) {
            actionContext.getSession().put(WebConstant.INTERCEPT_2, "Wrong Current Password");
            return ERROR;
        }
        userManager.updatePassword(userManager.getUserByAccount(getUser().getAccount()), getNewPassword());
        actionContext.getSession().put(WebConstant.USER, userManager.getUserBeanByAccount(getUser().getAccount()));
        if (result == UserManager.LOGIN_USER) {
            Cookie usrCookie = new Cookie(WebConstant.ACCOUNT, getUser().getAccount());
            Cookie pwdCookie = new Cookie(WebConstant.PASSWORD, getNewPassword());
            final int expiry = 7 * 24 * 60 * 60; // one week
            usrCookie.setMaxAge(expiry);
            pwdCookie.setMaxAge(expiry);
            ServletActionContext.getResponse().addCookie(usrCookie);
            ServletActionContext.getResponse().addCookie(pwdCookie);
        }
        return SUCCESS;
    }

}
