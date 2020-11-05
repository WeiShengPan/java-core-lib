/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */
package quartz;

import org.apache.commons.lang3.time.DateUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;

/**
 * @author weisheng.pws
 * @version $Id: SimpleTest.java, v 0.1 2019年08月13日 8:05 PM weisheng.pws Exp $
 */
public class SimpleTest {

    private static int count = 1;


    public static void main(String[] args) throws Exception {

        try {

            Scheduler scheduler = new StdSchedulerFactory().getScheduler();

            Date time1 = DateUtils.parseDate("2019-08-19 15:14:10", "yyyy-MM-dd HH:mm:ss");
            Date time2 = DateUtils.parseDate("2019-08-19 15:14:20", "yyyy-MM-dd HH:mm:ss");

            Date now = new Date();
            //System.out.println(time1.after(now));

            JobDetail jobDetail1 = JobBuilder.newJob(TestJob.class).withIdentity("job1", "group1").build();
            Trigger trigger1 = TriggerBuilder.newTrigger()
                    .withIdentity("trigger1", "group1")
                    .withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?")).startNow()
                    //withSchedule(CronScheduleBuilder.cronSchedule("*/5 * * * * ?"))
                    //.withSchedule(SimpleScheduleBuilder.simpleSchedule()).startAt(time1)
                    .build();

            //JobDetail jobDetail2 = JobBuilder.newJob(TestJob.class).withIdentity("jobName2", "groupName2").build();
            //SimpleTrigger trigger2 = TriggerBuilder.newTrigger().withIdentity("triggerName2", "groupName2").withSchedule(
            //        SimpleScheduleBuilder.simpleSchedule()).startAt(time2).build();

            scheduler.scheduleJob(jobDetail1, trigger1);
            //scheduler.scheduleJob(jobDetail2, trigger2);

            //countDownLatch.await();

            scheduler.start();

            Thread.sleep(10000);

            scheduler.shutdown(true);

            System.out.println(count);
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private class TestJob implements Job {
        @Override
        public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

            try {

                System.out.println("hi");
                count++;
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

}