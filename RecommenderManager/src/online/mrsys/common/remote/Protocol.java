package online.mrsys.common.remote;

public interface Protocol {

    String SYS_NAME = "mrsys";
    String RES_SUFFIX = ".result";
    
    String TOPIC = "RecommendedMovieList";
    String BROKER = "tcp://www.mrsys.online:1883";
    
    String REQUEST = "request";
    String RESULT = "result";
    String CONFIRM = "confirm";
    
}
