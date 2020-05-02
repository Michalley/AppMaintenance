package com.example.appmaintenance;

/**
 * The type User.
 */
public class User {

    /**
     * The Name.
     */
    public String name;
    /**
     * The L name.
     */
    public String lName;
    /**
     * The Email.
     */
    public String email;
    /**
     * The Phone.
     */
    public String phone;
    /**
     * The Level.
     */
    public int level;
    /**
     * The Uid.
     */
    public String uid;

    /**
     * Instantiates a new User.
     */
    public User () { }

    /**
     * Instantiates a new User.
     *
     * @param name  the name
     * @param lName the l name
     * @param email the email
     * @param phone the phone
     * @param level the level
     * @param uid   the uid
     */
    public User (String name,String lName,String email,String phone,int level,String uid){
        this.name=name;
        this.lName=lName;
        this.email=email;
        this.phone=phone;
        this.level=level;
        this.uid=uid;
    }

    /**
     * Get name string.
     *
     * @return the string
     */
    public String getName(){
        return name;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getlName() {
        return lName;
    }

    /**
     * Gets uid.
     *
     * @return the uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Sets name.
     *
     * @param lName the l name
     */
    public void setlName(String lName) {
        this.lName = lName;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets uid.
     *
     * @param uid the uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * Gets phone.
     *
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Sets phone.
     *
     * @param phone the phone
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

}
