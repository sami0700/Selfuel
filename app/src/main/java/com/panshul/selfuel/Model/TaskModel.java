package com.panshul.selfuel.Model;

public class TaskModel {
    String taskName;
    String taskContent;
    int time;
    String isCompleted;
    String taskId;

    public TaskModel(String taskName, String taskContent, int time, String isCompleted, String taskId) {
        this.taskName = taskName;
        this.taskContent = taskContent;
        this.time = time;
        this.isCompleted = isCompleted;
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public void setTaskContent(String taskContent) {
        this.taskContent = taskContent;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(String isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
}
