package com.example.duty.team.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class ShiftInfo {
    private long id;
    private String workerName;
    private Status status;
    private boolean isBegin;

    public enum Status {
        DAY,
        EVENING,
        NIGHT,
        OFF;

        private static final List<Status> VALUES =
                Collections.unmodifiableList(Arrays.asList(values()));
        private static final int SIZE = VALUES.size();
        private static final Random RANDOM = new Random();

        public static Status randomStatus() {
            return VALUES.get(RANDOM.nextInt(SIZE));
        }
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isBegin() {
        return isBegin;
    }

    public void setBegin(boolean begin) {
        isBegin = begin;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}

