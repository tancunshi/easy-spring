package org.easyspring.test.entity;

import org.easyspring.stereotype.Component;

/**
 * @author tancunshi
 */
@Component
public class School {

    private String schoolName;

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }
}
