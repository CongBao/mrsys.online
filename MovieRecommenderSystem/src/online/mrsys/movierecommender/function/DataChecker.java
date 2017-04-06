package online.mrsys.movierecommender.function;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class DataChecker {
    
    @After(value = "execution(* online.mrsys.common.dao.BaseDao.save(..)) && args(entity)")
    public void afterSave(Object entity) {
        // TODO
    }
    
    @After(value = "execution(* online.mrsys.common.dao.BaseDao.update(..)) && args(entity)")
    public void afterUpdate(Object entity) {
        // TODO
    }
    
}
