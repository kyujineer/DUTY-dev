package com.example.duty;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {

    // Variable declaration
    private String ID;
    private String name;
    private String teamId;
    private String teamName;
    private String role;
    private boolean isAdmin;
    private String documentId;

    User() {
        this.ID = "Guest";
        this.name = "Guest "+String.valueOf(Math.random()*100);
        this.teamId = "No Team Id";
        this.teamName = "No Team Name";
        this.role = "Guest";
        this.isAdmin = false;
        this.documentId = "";
    }
    User(String ID, String name, String teamId, String teamName, String role, boolean isAdmin, String documentId) {
        this.ID = ID;
        this.name = name;
        this.teamId = teamId;
        this.teamName = teamName;
        this.role = role;
        this.isAdmin = isAdmin;
        this.documentId = documentId;
    }

    protected User(Parcel in) {
        ID = in.readString();
        name = in.readString();
        teamId = in.readString();
        teamName = in.readString();
        role = in.readString();
        isAdmin = in.readByte() != 0;
        documentId = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // protected methods
    public String getID() {
        return this.ID;
    }
    public String getName() {
        return this.name;
    }
    public String getTeamId() {
        return this.teamId;
    }
    public String getTeamName() {
        return this.teamName;
    }
    public String getRole() {
        return this.role;
    }
    public boolean isAdmin() {
        return this.isAdmin;
    }
    public String getDocumentId() {return this.documentId;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(ID);
        parcel.writeString(name);
        parcel.writeString(teamId);
        parcel.writeString(teamName);
        parcel.writeString(role);
        parcel.writeByte((byte) (isAdmin ? 1 : 0));
        parcel.writeString(documentId);
    }
}
