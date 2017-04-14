package online.mrsys.movierecommender.action.user;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;

public class RemoveFavoriteAction extends BaseAction {
    
    private static final long serialVersionUID = 4364661103933417469L;
    
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
    	userManager.deleteFavorite(getId());
    	actionContext.getSession().put(WebConstant.FAVORITES, null);
    	return SUCCESS;
    }
    
}
