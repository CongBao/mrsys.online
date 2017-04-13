package online.mrsys.movierecommender.action.user;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.User;

public class DeleteAccountAction extends BaseAction {

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
			if (actionContext.getSession().get(WebConstant.USER)
					.equals(userManager.getUserBeanByAccount(getUser().getAccount(), movieManager)))
				actionContext.getSession().put(WebConstant.USER, null);
			return SUCCESS;
		}
		actionContext.getSession().put(WebConstant.INTERCEPT, "Error in deleting account");
		return ERROR;
	}

}
