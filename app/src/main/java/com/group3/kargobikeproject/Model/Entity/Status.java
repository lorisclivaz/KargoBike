package com.group3.kargobikeproject.Model.Entity;

public class Status {

    //Variables about Route
    private String idStatus;
    private String unChecked;
    private String inProgress;
    private String checked;


    //Constructor

    public Status()
    {

    }

    public Status(String unChecked, String inProgress, String checked )
    {

        this.unChecked = unChecked;
        this.inProgress = inProgress;
        this.checked = checked;
    }


    //Getters and setters


    public String getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(String idStatus) {
        this.idStatus = idStatus;
    }

    public String getUnChecked() {
        return unChecked;
    }

    public void setUnChecked(String unChecked) {
        this.unChecked = unChecked;
    }

    public String getInProgress() {
        return inProgress;
    }

    public void setInProgress(String inProgress) {
        this.inProgress = inProgress;
    }

    public String getChecked() {
        return checked;
    }

    public void setChecked(String checked) {
        this.checked = checked;
    }
}
