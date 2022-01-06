package edu.neu.myapplication.model;

public class HomeRecipeModel {
    private String imageUrl, menuName, email, id, userId;



    public HomeRecipeModel(String imageUrl, String menuName, String email, String id, String userId) {
        this.imageUrl = imageUrl;
        this.menuName = menuName;
        this.email = email;
        this.id = id;
        this.userId = userId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
