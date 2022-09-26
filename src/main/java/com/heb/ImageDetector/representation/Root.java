package com.heb.ImageDetector.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Root {
    @SerializedName("result")
    @Expose
    public Result result;
    @SerializedName("status")
    @Expose
    public Status status;
}
