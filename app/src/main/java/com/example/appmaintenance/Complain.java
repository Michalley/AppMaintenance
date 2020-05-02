package com.example.appmaintenance;

/**
 * The type Complain.
 */
public class Complain {

    /**
     * The User.
     */
    public String user;
    /**
     * The Date.
     */
    public String date;
    /**
     * The Time.
     */
    public String time;
    /**
     * The Category.
     */
    public String category;
    /**
     * The Zone.
     */
    public String zone;
    /**
     * The Emergency.
     */
    public int emergency; //false=emergency   true=not emergency
    /**
     * The State.
     */
    public int state;
    /**
     * The Notes.
     */
    public String notes;
    /**
     * The Name.
     */
    public String name;
    /**
     * The Pic.
     */
    public String pic;

    /**
     * Instantiates a new Complain.
     */
    public Complain (){}

    /**
     * Instantiates a new Complain.
     *
     * @param user      the user
     * @param date      the date
     * @param time      the time
     * @param category  the category
     * @param zone      the zone
     * @param emergency the emergency
     * @param state     the state
     * @param notes     the notes
     * @param name      the name
     * @param pic       the pic
     */
    public Complain (String user,String date,String time,String category,String zone,int emergency,int state,String notes,String name,String pic){
        this.category=category;
        this.date=date;
        this.emergency=emergency;
        this.name=name;
        this.zone=zone;
        this.user=user;
        this.time=time;
        this.state=state;
        this.notes=notes;
        this.pic=pic;
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
     * Gets pic.
     *
     * @return the pic
     */
    public String getPic() {
        return pic;
    }

    /**
     * Gets emergency.
     *
     * @return the emergency
     */
    public int getEmergency() {
        return emergency;
    }

    /**
     * Gets state.
     *
     * @return the state
     */
    public int getState() {
        return state;
    }

    /**
     * Gets category.
     *
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gets date.
     *
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Gets notes.
     *
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Gets time.
     *
     * @return the time
     */
    public String getTime() {
        return time;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public String getUser() {
        return user;
    }

    /**
     * Gets zone.
     *
     * @return the zone
     */
    public String getZone() {
        return zone;
    }

    /**
     * Sets category.
     *
     * @param category the category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * Sets date.
     *
     * @param date the date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * Sets emergency.
     *
     * @param emergency the emergency
     */
    public void setEmergency(int emergency) {
        this.emergency = emergency;
    }

    /**
     * Sets notes.
     *
     * @param notes the notes
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Sets pic.
     *
     * @param pic the pic
     */
    public void setPic(String pic) {
        this.pic = pic;
    }

    /**
     * Sets state.
     *
     * @param state the state
     */
    public void setState(int state) {
        this.state = state;
    }

    /**
     * Sets time.
     *
     * @param time the time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * Sets zone.
     *
     * @param zone the zone
     */
    public void setZone(String zone) {
        this.zone = zone;
    }

}
