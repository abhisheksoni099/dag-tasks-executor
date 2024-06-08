package com.abhisheksoni.task;

public class SleepTask extends BaseTask {
    private int sleepTimeInSeconds;

    public SleepTask(String name, int sleepTimeInSeconds) {
        this.name = name;
        this.sleepTimeInSeconds = sleepTimeInSeconds;
    }

    @Override
    public void execute() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Runing task: ");
        stringBuffer.append(name);
        System.out.println(stringBuffer.toString());
        sleep(sleepTimeInSeconds);
    }

    private void sleep(int sleepTimeInSeconds) {
        try {
            Thread.sleep(sleepTimeInSeconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException("Sleeping thread interrupted " + e.getMessage());
        }
    }
}
