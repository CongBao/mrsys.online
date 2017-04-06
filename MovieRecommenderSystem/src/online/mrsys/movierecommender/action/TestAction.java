package online.mrsys.movierecommender.action;

import online.mrsys.movierecommender.action.base.UserBaseAction;

public class TestAction extends UserBaseAction {

    @Override
    public String execute() throws Exception {
        userManager.recommendMovies();
        //System.out.println(userManager.getUserBeanByAccount("testuser96", movieManager));
//        Movie testMovie = new Movie(5, 5, 5, "test5", null, null);
//        movieManager.addMovie(testMovie);
//        User testUser = new User(1, "test1", "test1", "test1", false, null, new Role(2, "user", null), null, null);
//        userManager.validRegister(testUser);
//        movieManager.addRating(new Rating(1, 4.0f, testUser, testMovie));
        return SUCCESS;
    }

}
