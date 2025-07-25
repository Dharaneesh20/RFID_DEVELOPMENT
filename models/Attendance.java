package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Attendance {
    public int id;
    public int userId;
    public LocalDate date;
    public LocalDateTime loginTime;
    public LocalDateTime logoutTime;
    public String status;

    // ...constructor, getters, setters...
}
