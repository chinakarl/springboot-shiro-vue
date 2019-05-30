package com.hover.common.util.listeners;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

/**
 * @author: zhaihx
 * @description:
 * @date:2019/5/29
 */
public class ApplicationHolderListener implements ApplicationListener {

    private static Thread holdThread;
    private static Boolean running = Boolean.FALSE;

    public ApplicationHolderListener() {
    }

    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof ApplicationPreparedEvent ||
                applicationEvent instanceof ApplicationReadyEvent) {
            if(running == Boolean.FALSE) {
                running = Boolean.TRUE;
            }

            if(holdThread == null) {
                holdThread = new Thread(new Runnable() {
                    public void run() {
                        while(ApplicationHolderListener.running.booleanValue() && !Thread.currentThread().isInterrupted()) {
                            try {
                                Thread.sleep(2000L);
                            } catch (InterruptedException var2) {
                                ;
                            }
                        }

                    }
                }, "application-holder");
                holdThread.setDaemon(false);
                holdThread.start();
            }
        }

        if(applicationEvent instanceof ContextClosedEvent) {
            running = Boolean.FALSE;
            if(null != holdThread) {
                holdThread.interrupt();
                holdThread = null;
            }
        }

    }

}
