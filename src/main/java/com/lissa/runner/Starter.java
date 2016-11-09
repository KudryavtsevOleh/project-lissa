package com.lissa.runner;


public class Starter {

    private Runner runner;

    public Starter(Runner runner) {
        this.runner = runner;
    }

    public void start() {
        runner.run();
    }

}
