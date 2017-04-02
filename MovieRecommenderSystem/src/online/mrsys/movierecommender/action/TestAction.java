package online.mrsys.movierecommender.action;

import java.util.List;

import online.mrsys.movierecommender.action.base.UserBaseAction;
import online.mrsys.movierecommender.domain.Movie;
import online.mrsys.movierecommender.domain.User;

public class TestAction extends UserBaseAction {

    @Override
    public String execute() throws Exception {
        System.out.println("TestAction called");
        User testUser = userManager.getUserByAccount("testuser7");
        List<Movie> movies = movieManager.recommendMoviesToUser(testUser);
        for (Movie movie : movies) {
            System.out.println(movie.getTitle());
        }
        return SUCCESS;
    }

}
