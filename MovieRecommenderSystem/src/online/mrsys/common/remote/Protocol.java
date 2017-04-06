package online.mrsys.common.remote;

public interface Protocol {
    
    String SYS_NAME = "mrsys";
    String RES_SUFFIX = ".result";
    
    String TOPIC = "MRSYSCOMMUNICATION";
    String BROKER = "tcp://www.mrsys.online:1883";
    
    String REQUEST = "request";
    String UPDATE = "update";
    String RESULT = "result";
    String CONFIRM = "confirm";
    
    String NEW_PREFIX = "new";
    String UPDATE_PREFIX = "update";

}
