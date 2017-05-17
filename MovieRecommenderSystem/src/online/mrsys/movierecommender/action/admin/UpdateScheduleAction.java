package online.mrsys.movierecommender.action.admin;

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.triggers.CronTriggerImpl;

import online.mrsys.movierecommender.action.base.BaseAction;

public class UpdateScheduleAction extends BaseAction {

    private static final long serialVersionUID = -6101358954211054383L;
    
    private StdScheduler scheduler;
    
    public StdScheduler getScheduler() {
        return scheduler;
    }

    public void setScheduler(StdScheduler scheduler) {
        this.scheduler = scheduler;
    }
    
    private Integer hour;
    private Integer minute;
    private Integer second;

    public Integer getHour() {
        return hour;
    }

    public void setHour(Integer hour) {
        this.hour = hour;
    }

    public Integer getMinute() {
        return minute;
    }

    public void setMinute(Integer minute) {
        this.minute = minute;
    }

    public Integer getSecond() {
        return second;
    }

    public void setSecond(Integer second) {
        this.second = second;
    }

    @Override
    public String execute() throws Exception {
        TriggerKey key = new TriggerKey("cronTriggerRecommend");
        CronTriggerImpl trigger = (CronTriggerImpl) getScheduler().getTrigger(key);
        try {
            trigger.setCronExpression(getSecond() + " " + getMinute() + " " + getHour() + " * * ?");
            getScheduler().rescheduleJob(key, trigger);
        } catch (ParseException e) {
            Logger.getLogger(UpdateScheduleAction.class.getName()).log(Level.SEVERE, "Invalid cron expression");
            return ERROR;
        }
        Logger.getLogger(UpdateScheduleAction.class.getName()).log(Level.INFO, "Schedule time has changed. Next time: {0}", trigger.getNextFireTime());
        return SUCCESS;
    }
    
}
