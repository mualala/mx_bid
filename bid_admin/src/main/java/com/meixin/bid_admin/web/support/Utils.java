package com.meixin.bid_admin.web.support;

import org.apache.commons.lang3.StringUtils;
import org.quartz.Job;

import javax.servlet.http.HttpSession;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Desc：
 * @Author： yanghm
 * @Date： 13:08 2018/5/24 0024
 */
public class Utils {

    /**
     * 自增id
      */
    public static class ID {
        private static AtomicLong id = new AtomicLong(0);
        public static long incre() {
            if (id.get() > 1000000)
                id.set(0);

            return id.incrementAndGet();
        }

        public static String taskGroupId(String uid) {
            String groupId = String.format("%06d-%s", incre(), uid);
            return groupId;
        }
    }

    public static void main(String[] args) {

        String a = "a".concat("-b");
        System.err.println(a);
    }

    public static String UUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static Job getClass(String classname) throws Exception {
        Class<?> clazz = Class.forName(classname);
        return (Job)clazz.newInstance();
    }

    public static int uidFromSession(HttpSession session) {
        Integer uid = (Integer) session.getAttribute("uid");
        return uid;
    }


    /**
     * 后台的延时
     */
    public static class Delay {
        private static AtomicInteger delay = new AtomicInteger(3); //默认是延时 3min

        /**
         * @Desc:   更新延时的时间
         * @Author: yanghm
         * @Param:
         * @Date:   15:04 2018/6/22 0022
         * @Return: 延时的毫秒数 /ms
         */
        public static long setDelay(int val) {
            if (val > 0) {
                delay.getAndSet(val);
            }
            return getDelay();
        }

        public static long getDelay() {
            long d = delay.get() * 60 * 1000;
            return d;
        }
    }

}
