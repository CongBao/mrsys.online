package online.mrsys.movierecommender.action.admin;

import java.text.ParseException;

import org.quartz.TriggerKey;
import org.quartz.impl.StdScheduler;
import org.quartz.impl.triggers.CronTriggerImpl;

import com.opensymphony.xwork2.ActionContext;

import online.mrsys.movierecommender.action.base.BaseAction;
import online.mrsys.movierecommender.action.base.WebConstant;

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
        ActionContext actionContext = ActionContext.getContext();
        TriggerKey key = new TriggerKey("cronTriggerRecommend");
        CronTriggerImpl trigger = (CronTriggerImpl) getScheduler().getTrigger(key);
        try {
            trigger.setCronExpression(getSecond() + " " + getMinute() + " " + getHour() + " * * ?");
            getScheduler().rescheduleJob(key, trigger);
        } catch (ParseException e) {
            actionContext.getSession().put(WebConstant.INTERCEPT_3, "Invalid cron expression");
            return ERROR;
        }
        actionContext.getSession().put(WebConstant.INTERCEPT_3, "Schedule time has changed to " + trigger.getStartTime());
        return SUCCESS;
    }
    
}
