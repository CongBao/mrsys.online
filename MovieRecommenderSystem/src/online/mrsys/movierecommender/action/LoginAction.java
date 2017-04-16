package online.mrsys.movierecommender.action;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.service.UserManager;

public class LoginAction extends BaseAction {
	
    private static final long serialVersionUID = 6272451479323922861L;
    
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
	    int result = userManager.validLogin(getUser());
	    if (result != UserManager.LOGIN_FAIL) {
	        actionContext.getSession().put(WebConstant.USER, userManager.getUserBeanByAccount(getUser().getAccount()));
	        if (result == UserManager.LOGIN_USER) { // only set cookies for normal users
	            Cookie usrCookie = new Cookie(WebConstant.ACCOUNT, getUser().getAccount());
	            Cookie pwdCookie = new Cookie(WebConstant.PASSWORD, getUser().getPassword());
	            final int expiry = 7 * 24 * 60 * 60; // one week
	            usrCookie.setMaxAge(expiry);
	            pwdCookie.setMaxAge(expiry);
	            ServletActionContext.getResponse().addCookie(usrCookie);
	            ServletActionContext.getResponse().addCookie(pwdCookie);
	        }
	        return SUCCESS;
	    }
	    actionContext.getSession().put(WebConstant.INTERCEPT, "Wrong account or password");
		return ERROR;
	}

}
