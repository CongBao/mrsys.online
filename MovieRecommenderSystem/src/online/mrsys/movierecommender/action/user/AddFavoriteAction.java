package online.mrsys.movierecommender.action.user;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.domain.Favorite;

public class AddFavoriteAction extends BaseAction {
    
    private Favorite favorite;

    public Favorite getFavorite() {
        return favorite;
    }

    public void setFavorite(Favorite favorite) {
        this.favorite = favorite;
    }

    @Override
    public String execute() throws Exception {
        // TODO Auto-generated method stub
        return super.execute();
    }
    
}
