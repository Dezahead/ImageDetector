package com.heb.ImageDetector.representation;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class Result {
    @SerializedName("tags")
    @Expose
    public List<Tag> tags = null;
}
