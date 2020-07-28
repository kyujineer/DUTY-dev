package com.example.duty;

public class User {


    // Variable declaration
    private String ID;
    private String name;
    private String teamId;
    private String teamName;
    private String role;
    private boolean isAdmin;

    User() {
        this.ID = "Guest";
        this.name = "Guest "+String.valueOf(Math.random()*100);
        this.teamId = "No Team Id";
        this.teamName = "No Team Name";
        this.role = "Guest";
        this.isAdmin = false;
    }
    User(String ID, String name, String teamId, String teamName, String role, boolean isAdmin) {
        this.ID = ID;
        this.name = name;
        this.teamId = teamId;
        this.teamName = teamName;
        this.role = role;
        this.isAdmin = isAdmin;
    }

    // public methods
    protected String getID() {
        return this.ID;
    }
    protected String getName() {
        return this.name;
    }
    protected String getTeamId() {
        return this.teamId;
    }
    protected String getTeamName() {
        return this.teamName;
    }
    protected String getRole() {
        return this.role;
    }
    protected boolean isAdmin() {
        return this.isAdmin;
    }

}
