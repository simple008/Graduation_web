package com.lukong.model.flink_web;

import java.util.List;

/**
 * Created by lukong on 2016/10/19.
 */
public class Job {
    private String jid;
    private String name;
    private String state;
    private long start_time;
    private long end_time;
    private long duration;

    public String getJid() {
        return jid;
    }

    public void setJid(String jid) {
        this.jid = jid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Job job = (Job) o;

        if (start_time != job.start_time) return false;
        if (end_time != job.end_time) return false;
        if (duration != job.duration) return false;
        if (last_modification != job.last_modification) return false;
        if (!jid.equals(job.jid)) return false;
        if (!name.equals(job.name)) return false;
        if (!state.equals(job.state)) return false;
        return tasks.equals(job.tasks);

    }

    @Override
    public int hashCode() {
        int result = jid.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + state.hashCode();
        result = 31 * result + (int) (start_time ^ (start_time >>> 32));
        result = 31 * result + (int) (end_time ^ (end_time >>> 32));
        result = 31 * result + (int) (duration ^ (duration >>> 32));
        result = 31 * result + (int) (last_modification ^ (last_modification >>> 32));
        result = 31 * result + tasks.hashCode();
        return result;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getStart_time() {
        return start_time;
    }

    public void setStart_time(long start_time) {
        this.start_time = start_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getLast_modification() {
        return last_modification;
    }

    public void setLast_modification(long last_modification) {
        this.last_modification = last_modification;
    }

    public List<Integer> getTasks() {
        return tasks;
    }

    public void setTasks(List<Integer> tasks) {
        this.tasks = tasks;
    }

    private long last_modification;
    private List<Integer> tasks;

}
