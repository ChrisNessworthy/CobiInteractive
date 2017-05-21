package com.cobi.cobiinteractive.classes;

public class AndroidVersionObject {

    private String name;
    private String version;
    private String released;
    private String api;
    private String image;

    public AndroidVersionObject(String name, String version, String released, String api, String image) {
        this.name = name;
        this.version = version;
        this.released = released;
        this.api = api;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getReleased() {
        return released;
    }

    public void setReleased(String released) {
        this.released = released;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
