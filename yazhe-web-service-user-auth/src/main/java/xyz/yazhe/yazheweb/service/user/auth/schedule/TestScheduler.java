package xyz.yazhe.yazheweb.service.user.auth.schedule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author Yazhe
 * Created at 15:27 2018/12/11
 */
@Component
public class TestScheduler {

	private static final Logger logger = LoggerFactory.getLogger(TestScheduler.class);

	@Autowired
	private RedisTemplate<String,String> redisTemplate;

	@Autowired
	private ThreadPoolTaskScheduler taskScheduler;

//	@Bean
//	public void configureTasks() {
//		taskScheduler.schedule(getTask(),getTrigger());
//		taskScheduler.schedule(getTask(),getTrigger());
//	}

	private Runnable getTask(){
		return () -> {
			logger.info("测试定时任务");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
	}

	private Runnable getTask2(){
		return () -> {
			logger.info("测试2");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		};
	}

	private Trigger getTrigger(){
		return triggerContext -> {
			CronTrigger cronTrigger = new CronTrigger(getCron());
			return cronTrigger.nextExecutionTime(triggerContext);
		};
	}

	private String getCron(){
		String cron = redisTemplate.boundValueOps("test_scheduler").get();
		return StringUtils.isEmpty(cron) ? "0/1 * * * * ?" : cron;
	}

	@Bean
	public ThreadPoolTaskScheduler taskScheduler(){
		ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
		taskScheduler.setPoolSize(3);
		taskScheduler.setThreadNamePrefix("test");
		return taskScheduler;
	}
}
