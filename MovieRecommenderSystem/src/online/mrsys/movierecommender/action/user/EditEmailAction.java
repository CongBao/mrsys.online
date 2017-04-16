package online.mrsys.movierecommender.action.user;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.User;

public class EditEmailAction extends BaseAction {
    
    private static final long serialVersionUID = 3987719328185994810L;
    
    private String id;
    private String newEmail;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmail) {
        this.newEmail = newEmail;
    }

    @Override
    public String execute() throws Exception {
        ActionContext actionContext = ActionContext.getContext();
        if (userManager.isUserExist(getNewEmail())) {
            actionContext.getSession().put(WebConstant.INTERCEPT_1, "Email address exists");
            return ERROR;
        }
        User user = userManager.getUserById(Integer.parseInt(getId()));
        userManager.updateEmail(user, getNewEmail());
        actionContext.getSession().put(WebConstant.USER, userManager.getUserBeanByAccount(user.getAccount()));
        return SUCCESS;
    }
    
}
