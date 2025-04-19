package com.devtony.app.services.validations;

import com.devtony.app.exception.AttendanceException;
import com.devtony.app.exception.ExceptionDetails;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class AttendanceValidation {
    public void checkInOutReturn(Instant chekIn) throws AttendanceException {
        if (chekIn == null) {
            throw new AttendanceException("CheckIn not found",
                    new ExceptionDetails("No se ha realizado el checkIn", "low"));
        }
    }
}
