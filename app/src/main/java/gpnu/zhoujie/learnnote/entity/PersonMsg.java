package gpnu.zhoujie.learnnote.entity;

import android.app.Person;

public class PersonMsg {

    private String account = null;
    private String pwd = null;
    private String name = null;
    private String sex = null;
    private String phone = null;
    private String age = null;

    public PersonMsg(String account,String pwd,String name,String sex,String phone,String age)
    {
        this.account = account;
        this.pwd = pwd;
        this.sex = sex;
        this.name = name;
        this.phone = phone;
        this.age = age;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
