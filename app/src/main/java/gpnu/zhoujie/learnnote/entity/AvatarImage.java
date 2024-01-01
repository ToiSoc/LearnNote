package gpnu.zhoujie.learnnote.entity;

public class AvatarImage {
    private String account;

    private String imageId;

    public AvatarImage(){}
    public AvatarImage(String account, String imageId) {
        this.account = account;
        this.imageId = imageId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
