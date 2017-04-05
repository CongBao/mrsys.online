package online.mrsys.movierecommender.action;

import online.mrsys.movierecommender.action.base.UserBaseAction;

public class TestAction extends UserBaseAction {

    @Override
    public String execute() throws Exception {
        userManager.recommendMovies();
        //System.out.println(userManager.getUserBeanByAccount("testuser96", movieManager));
        return SUCCESS;
    }

}
