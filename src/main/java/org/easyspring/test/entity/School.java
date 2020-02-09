package org.easyspring.test.entity;

import org.easyspring.beans.factory.annotation.Autowired;
import org.easyspring.stereotype.Component;

@Component
public class School {

    private String schoolName;
    @Autowired
    private SchoolMaster schoolMaster;
    @Autowired(required = false)
    private Dog dog;

    public String getSchoolName() {
        return schoolName;
    }

    public SchoolMaster getSchoolMaster() {
        return schoolMaster;
    }
}
