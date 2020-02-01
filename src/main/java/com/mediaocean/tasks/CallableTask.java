package com.mediaocean.tasks;

import com.mediaocean.utils.Sequencer;

import java.util.concurrent.Callable;

public class CallableTask implements Callable<Integer> {

    private int index;

    public CallableTask(int index) {
        this.index = index;
    }

    @Override
    public Integer call() throws Exception {
        Sequencer.dryRun(index);
        return index;
    }
}
