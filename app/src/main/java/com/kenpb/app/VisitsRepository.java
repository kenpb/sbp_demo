package com.kenpb.app;

import java.util.concurrent.atomic.LongAdder;

import org.springframework.stereotype.Service;

@Service
public class VisitsRepository {
    private final LongAdder visits = new LongAdder();

    public void add() {
        visits.increment();
    }

    public long get() {
        return visits.longValue();
    }

}
