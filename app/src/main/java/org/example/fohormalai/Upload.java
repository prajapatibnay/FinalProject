package org.example.fohormalai;

public class Upload {
    private String username;
    private String phone;
    private String image;

    public Upload() {
    }

    public Upload(String username, String phone, String image) {
        this.username = username;
        this.phone = phone;
        this.image = image;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}