package com.jl.jlapp.eneity;

/**
 * Created by 柳亚婷 on 2018/3/15 0015.
 */

public class UpdataInfo {

    private String version;
    private String url;
    private String description;
    private Integer isMustUpdate;
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIsMustUpdate() {
        return isMustUpdate;
    }

    public void setIsMustUpdate(Integer isMustUpdate) {
        this.isMustUpdate = isMustUpdate;
    }
}
