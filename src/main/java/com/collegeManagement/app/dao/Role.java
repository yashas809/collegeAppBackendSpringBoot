package com.collegeManagement.app.dao;


import lombok.*;

import java.io.Serializable;


public class Role implements Serializable {

    private String rolename;

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public Role(String rolename) {
        this.rolename = rolename;
    }

    public Role() {
    }
}
