package com.example.Lesson8;

import org.springframework.stereotype.Component;

@Timer
@Component
public class TestTimerClassB {

    public void testSeconds(int seconds) throws InterruptedException {
        int milSeconds = seconds * 1000;
        Thread.sleep(milSeconds);
    }

    public String testMinutes(int minutes) throws InterruptedException {
        int milSeconds = minutes * 60000;
        Thread.sleep(milSeconds);
        return "Test for Minutes is finished!";
    }
}
