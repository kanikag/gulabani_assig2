package com.lnu.auth.model;

import java.util.Date;
import java.util.List;

public class Test {
    private Date startTime;
    private List<TestSession> testSessions;

    public Test(Date startTime, List<TestSession> testSessions) {
        this.startTime = startTime;
        this.testSessions = testSessions;
    }

    public Date getStartTime() {
        return startTime;
    }

    public List<TestSession> getTestSessions() {
        return testSessions;
    }
}
