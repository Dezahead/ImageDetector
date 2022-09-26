package com.heb.ImageDetector.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Tag {
    @SerializedName("confidence")
    @Expose
    public Double confidence;
    @SerializedName("tag")
    @Expose
    public DetectedObject tag;
}
