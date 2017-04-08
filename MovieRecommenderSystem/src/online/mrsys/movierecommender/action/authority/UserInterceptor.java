package online.mrsys.movierecommender.action.authority;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.service.MovieManager;
import online.mrsys.movierecommender.service.UserManager;
import online.mrsys.movierecommender.vo.UserBean;

public class UserInterceptor extends AbstractInterceptor {
    
    private static final long serialVersionUID = -5177665263045644572L;
    
    private UserManager userManager;
    private MovieManager movieManager;
    
    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }
    
    public void setMovieManager(MovieManager movieManager) {
        this.movieManager = movieManager;
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        ActionContext actionContext = ActionContext.getContext();
        
        // firstly check the session
        UserBean user = (UserBean) actionContext.getSession().get(WebConstant.USER);
        if (user != null) {
            return invocation.invoke();
        }
        
        // secondly check the cookie
        Cookie[] cookies = ServletActionContext.getRequest().getCookies();
        if (cookies != null) {
            String account = null;
            String password = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(WebConstant.ACCOUNT)) {
                    account = cookie.getValue();
                } else if (cookie.getName().equals(WebConstant.PASSWORD)) {
                    password = cookie.getValue();
                }
            }
            if (account != null && password != null) {
                User testUser = new User();
                testUser.setAccount(account);
                testUser.setPassword(password);
                int result = userManager.validLogin(testUser);
                if (result == UserManager.LOGIN_USER) {
                    actionContext.getSession().put(WebConstant.USER, userManager.getUserBeanByAccount(account, movieManager));
                    return invocation.invoke();
                }
            }
        }
        
        // otherwise reject
        actionContext.getSession().put(WebConstant.INTERCEPT, "Please login first");
        return Action.LOGIN;
    }

}
