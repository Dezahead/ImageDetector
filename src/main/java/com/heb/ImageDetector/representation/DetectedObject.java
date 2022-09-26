package com.heb.ImageDetector.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class DetectedObject {
    @SerializedName("en")
    @Expose
    public String en;
}
