package com.eattendances.ditms_attendance;

public class Student {
    String studentId;
    String studentName;
    String studentRoll;
    String studentRoom;
    String studentStatus;

    String lecture;
    String status;


    public Student(){

    }



    public String getLecture() {
        return lecture;
    }

    public void setLecture(String lecture) {
        this.lecture = lecture;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Student(String lecture, String status) {
        this.lecture = lecture;
        this.status = status;
    }

    public Student(String studentId, String studentName, String studentRoll, String studentRoom, String studentStatus) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.studentRoll = studentRoll;
        this.studentRoom = studentRoom;
        this.studentStatus = studentStatus;
    }

    public String getStudentStatus() { return studentStatus; }

    public void setStudentStatus(String studentStatus) {
        this.studentStatus = studentStatus;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentRoll() {
        return studentRoll;
    }

    public void setStudentRoll(String studentRoll) {
        this.studentRoll = studentRoll;
    }

    public String getStudentRoom() {
        return studentRoom;
    }

    public void setStudentRoom(String studentRoom) {
        this.studentRoom = studentRoom;
    }
}
