package online.mrsys.common.remote;

/**
 * The protocol used to connect server and scheduler.
 * Both the server and the scheduler should contain this interface.
 * 
 * @author Cong Bao
 *
 */
public interface Protocol {
    
    String SYS_NAME = "mrsys";
    String RES_SUFFIX = ".result";
    
    String TOPIC = "MRSYSCOMMUNICATION";
    String BROKER = "tcp://sub.mrsys.online:1883";
    
    String REQUEST = "request";
    String UPDATE = "update";
    String RESULT = "result";
    String CONFIRM = "confirm";
    
    String NEW_PREFIX = "new";
    String UPDATE_PREFIX = "update";
    
    String NULL = "null";

}
