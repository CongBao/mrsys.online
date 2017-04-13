package online.mrsys.movierecommender.action;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.service.UserManager;

public class RegisterAction extends BaseAction {

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
		int result = userManager.validRegister(getUser());
		if (result == UserManager.LOGIN_FAIL) {
			actionContext.getSession().put(WebConstant.INTERCEPT, "User is existed");
			return ERROR;
		}
		actionContext.getSession().put(WebConstant.USER,userManager.getUserBeanByAccount(getUser().getAccount(), movieManager));
		if (result == UserManager.REGISTER_USER) {
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

}
