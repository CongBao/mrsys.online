package online.mrsys.movierecommender.action.user;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.vo.UserBean;

public class LoadProfileAction extends BaseAction {
    
    private static final long serialVersionUID = -4961780923283262163L;
    
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    @Override
    public String execute() throws Exception {// TODO load favorite, recommendation list
        ActionContext actionContext = ActionContext.getContext();
        UserBean userBean = (UserBean) actionContext.getSession().get(WebConstant.USER);
        if (userBean.getAccount() != getAccount()) {
            actionContext.getSession().put(WebConstant.INTERCEPT, "Sorry, you have no permission to access this page");
            return WebConstant.REJECT;
        }
        return SUCCESS;
    }
    
}
