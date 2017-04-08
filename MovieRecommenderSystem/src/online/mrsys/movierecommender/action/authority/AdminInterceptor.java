package online.mrsys.movierecommender.action.authority;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.vo.RoleBean;
import online.mrsys.movierecommender.vo.UserBean;

public class AdminInterceptor extends AbstractInterceptor {

    private static final long serialVersionUID = -1034285317191167735L;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ActionContext actionContext = ActionContext.getContext();
        UserBean user = (UserBean) actionContext.getSession().get(WebConstant.USER);
        if (user == null) {
            actionContext.getSession().put(WebConstant.INTERCEPT, "Please login first");
            return Action.LOGIN;
        }
        if (!user.getRole().equals(RoleBean.ADMIN)) {
            actionContext.getSession().put(WebConstant.INTERCEPT, "Permission denied");
            return WebConstant.REJECT;
        }
        return invocation.invoke();
    }

}
