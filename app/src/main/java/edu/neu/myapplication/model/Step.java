package edu.neu.myapplication.model;

import android.net.Uri;

import java.util.List;

class Step {
    private Integer num;
    private List<Description> step;

    public Step() {
    }

    public Step(Integer num, List<Description> step) {
        this.num = num;
        this.step = step;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public List<Description> getStep() {
        return step;
    }

    public void setStep(List<Description> step) {
        this.step = step;
    }
}
