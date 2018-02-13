package com.bilgehankalkan.semihozturkhackathon.service.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bilgehan on 11.02.2018.
 */

public class RequestBody {

    @SerializedName("startDate")
    @Expose
    private String startDate;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("minCount")
    @Expose
    private Integer minCount;
    @SerializedName("maxCount")
    @Expose
    private Integer maxCount;

    /**
     *
     * @param startDate
     * @param minCount
     * @param endDate
     * @param maxCount
     */
    public RequestBody(String startDate, String endDate, Integer minCount, Integer maxCount) {
        super();
        this.startDate = startDate;
        this.endDate = endDate;
        this.minCount = minCount;
        this.maxCount = maxCount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getMinCount() {
        return minCount;
    }

    public void setMinCount(Integer minCount) {
        this.minCount = minCount;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }
}
