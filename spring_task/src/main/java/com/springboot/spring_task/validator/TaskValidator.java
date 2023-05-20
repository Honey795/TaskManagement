package com.springboot.spring_task.validator;

import java.time.format.DateTimeParseException;
import java.util.Date;

public class TaskValidator {

    /**
     * Проверяет правильно ли задан период
     */
    public static boolean validateDates(Date startDate, Date endDate) {
        try {
            return endDate.after(startDate);
        }catch(DateTimeParseException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * Проверяет дату планирования задачи
     */
    public static boolean validateDate(Date startDate) {
        try {
            Date current = new Date();
            return (startDate != null || startDate.equals(current) || startDate.after(current));
        }catch(DateTimeParseException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
