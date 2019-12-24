package org.easyspring.entity;

/**
 * @author tancunshi
 */
public class Zoo {
    private String zooName;
    private Dog dog;

    public Dog getDog() {
        return dog;
    }

    public void setDog(Dog dog) {
        this.dog = dog;
    }

    public String getZooName() {
        return zooName;
    }

    public void setZooName(String zooName) {
        this.zooName = zooName;
    }
}
