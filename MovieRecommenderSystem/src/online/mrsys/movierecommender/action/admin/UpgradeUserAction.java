package online.mrsys.movierecommender.action.admin;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.Role;
import online.mrsys.movierecommender.domain.User;

public class UpgradeUserAction extends BaseAction {
    
    private static final long serialVersionUID = 5029624570647679161L;
    
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
        User user = userManager.getUserById(getId());
        if (user.getRole().equals(Role.ADMIN)) {
            actionContext.getSession().put(WebConstant.INTERCEPT, "User cannot be upgraded");
            return ERROR;
        }
        userManager.updateRole(user, Role.ADMIN);
        actionContext.getSession().put(WebConstant.INTERCEPT, "Success");
        return SUCCESS;
    }
    
}
