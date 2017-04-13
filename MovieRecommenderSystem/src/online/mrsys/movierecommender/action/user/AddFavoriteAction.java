package online.mrsys.movierecommender.action.user;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.Favorite;

public class AddFavoriteAction extends BaseAction {

    private static final long serialVersionUID = 3109301412733073128L;
    
    private Favorite favorite;

	public Favorite getFavorite() {
		return favorite;
	}

	public void setFavorite(Favorite favorite) {
		this.favorite = favorite;
	}

	@Override
	public String execute() throws Exception {
		ActionContext actionContext = ActionContext.getContext();
		if (userManager.addFavorite(getFavorite())) {
			return SUCCESS;
		}
		actionContext.getSession().put(WebConstant.INTERCEPT, "Favorite exists");
		return ERROR;
	}

}
