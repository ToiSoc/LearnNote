package gpnu.zhoujie.learnnote.entity;

public class Note {

    private String Uuid;

    private String Account;

    private String Title;

    private String Time;

    private String Note;

    public Note(){}

    public Note(String uuid, String account, String title, String time, String note) {
        Uuid = uuid;
        Account = account;
        Title = title;
        Time = time;
        Note = note;
    }

    public String getUuid() {
        return Uuid;
    }

    public void setUuid(String uuid) {
        Uuid = uuid;
    }

    public String getAccount() {
        return Account;
    }

    public void setAccount(String account) {
        Account = account;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getNote() {
        return Note;
    }

    public void setNote(String note) {
        Note = note;
    }
}
