package online.mrsys.movierecommender.action.user;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.service.UserManager;

public class EditPasswordAction extends BaseAction {

	private User user;
	private String newPassword;
	private String oldPassword;

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

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	@Override
	public String execute() throws Exception {
		ActionContext actionContext = ActionContext.getContext();
		getUser().setPassword(getOldPassword());
		int result = userManager.validLogin(getUser());
		if (result == UserManager.LOGIN_FAIL) {
			actionContext.getSession().put(WebConstant.INTERCEPT, "Wrong Current Password");
			return ERROR;
		}
		User newUser = userManager.updatePassword(getUser(), getNewPassword());
		actionContext.getSession().put(WebConstant.USER,
				userManager.getUserBeanByAccount(newUser.getAccount(), movieManager));
		return SUCCESS;
	}

}
