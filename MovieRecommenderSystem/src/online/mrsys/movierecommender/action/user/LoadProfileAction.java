package online.mrsys.movierecommender.action.user;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;
import online.mrsys.movierecommender.domain.User;
import online.mrsys.movierecommender.util.Serializer;
import online.mrsys.movierecommender.vo.FavoriteBean;
import online.mrsys.movierecommender.vo.MovieBean;
import online.mrsys.movierecommender.vo.UserBean;

/**
 * @version 1.1
 * @author Cong Bao
 *
 */
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
    public String execute() throws Exception {
        ActionContext actionContext = ActionContext.getContext();
        UserBean userBean = (UserBean) actionContext.getSession().get(WebConstant.USER);
        if (userBean == null) {
            return Action.LOGIN;
        }
        // load favorites
        List<FavoriteBean> favoriteBeans = userManager.getFavoriteBeansByUserBean(userBean, movieManager);
        if (favoriteBeans != null && !favoriteBeans.isEmpty()) {
            actionContext.getSession().put(WebConstant.FAVORITES, favoriteBeans);
        } else {
            actionContext.getSession().put(WebConstant.FAVORITES, null);
        }
        // load recommendations
        User user = userManager.getUserById(userBean.getId());
        byte[] recommends = user.getRecommendation();
        if (recommends != null && recommends.length > 0) {
            Object obj = Serializer.deserialize(recommends);
            if (obj != null) {
                @SuppressWarnings("unchecked")
                List<String> recomList = (List<String>) obj;
                List<MovieBean> movieBeans = new ArrayList<>();
                int len = recomList.size() >= 10 ? 10 : recomList.size();
                for (int i = 0; i < len; i++) {
                    String[] recom = recomList.get(i).split("&");
                    Integer movieId = Integer.parseInt(recom[0]);
                    Integer neighbours = Integer.parseInt(recom[1]);
                    MovieBean movieBean = movieManager.getMovieBeanById(movieId);
                    movieBean.setNeighbours(neighbours);
                    movieBeans.add(movieBean);
                }
                actionContext.getSession().put(WebConstant.RECOMMENDATIONS, movieBeans);
            } else {
                actionContext.getSession().put(WebConstant.RECOMMENDATIONS, null);
            }
        } else {
            actionContext.getSession().put(WebConstant.RECOMMENDATIONS, null);
        }
        return SUCCESS;
    }
    
}
