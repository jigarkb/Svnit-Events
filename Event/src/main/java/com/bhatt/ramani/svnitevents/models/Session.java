package com.bhatt.ramani.svnitevents.models;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Session implements Serializable {

    private Date startTime;
    private Date endTime;
    private List<People> peoples;
    private String id;
    private String title;
    private String space;
    private String eventSummary;
    private String regUrl;

	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public List<People> getPeoples() {
		return peoples;
	}
	public void setPeoples(List<People> peoples) {
		this.peoples = peoples;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = space;
	}
	public String getEventSummary() {
		return eventSummary;
	}
	public void setEventSummary(String eventSummary) {
		this.eventSummary = eventSummary;
	}
    public String getRegUrl() {
        return regUrl;
    }
    public void setRegUrl(String regUrl) {
        this.regUrl = regUrl;
    }
}
