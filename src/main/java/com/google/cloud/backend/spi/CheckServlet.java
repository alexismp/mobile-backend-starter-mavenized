package com.google.cloud.backend.spi;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.ThreadManager;

public class CheckServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger log = Logger.getLogger(CheckServlet.class.getName());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        final AtomicLong counter = new AtomicLong();
        Thread thread = ThreadManager.createBackgroundThread(new Runnable() {
            public void run() {
                try {
                    while (true) {
                        counter.incrementAndGet();

                        log.info("-- Run counter: " + counter.intValue());

                        Thread.sleep(1000);
                    }
                } catch (InterruptedException ex) {
                    throw new RuntimeException("Interrupted in loop:", ex);
                }
            }
        });
        thread.start();
    }


}
