package online.mrsys.movierecommender.action;

import java.util.Random;

import javax.servlet.http.Cookie;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.service.UserManager;

public class AutoLoginAction extends BaseAction {

    private static final long serialVersionUID = 8398494974487663951L;
    
    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String execute() throws Exception {
        ActionContext actionContext = ActionContext.getContext();
        actionContext.getSession().put(WebConstant.MOVIE, movieManager.getMovieBeanById(new Random().nextInt((int) movieManager.getMovieCount()) + 1));
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
                User toLogin = new User();
                toLogin.setAccount(account);
                toLogin.setPassword(password);
                int result = userManager.validLogin(toLogin);
                if (result == UserManager.LOGIN_USER) {
                    actionContext.getSession().put(WebConstant.USER, userManager.getUserBeanByAccount(account));
                    return SUCCESS;
                }
            }
        }
        return ERROR;
    }
    
}
