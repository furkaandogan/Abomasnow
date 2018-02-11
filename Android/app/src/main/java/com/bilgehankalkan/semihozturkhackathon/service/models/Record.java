package com.bilgehankalkan.semihozturkhackathon.service.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bilgehan on 11.02.2018.
 */

public class Record {

    @SerializedName("_id")
    @Expose
    private Id id;
    @SerializedName("totalCount")
    @Expose
    private Integer totalCount;

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }
}
