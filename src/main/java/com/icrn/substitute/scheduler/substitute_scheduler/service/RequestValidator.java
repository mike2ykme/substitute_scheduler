package com.icrn.substitute.scheduler.substitute_scheduler.service;

import com.icrn.substitutes.model.Request;

public class RequestValidator {
    public static boolean validateNewRequest(Request request){
        if (request.getRequestId() == 0L &&
            request.getEndTime() != null &&
            request.getStartTime() != null &&
            request.getRequestorId() != 0L &&
//            request.getSubstituteId() != 0L &&
            request.getStatus() != null)
                return true;

        return false;
    }
    public static boolean validateFullRequest(Request request){
        if (request.getRequestId() != 0L &&
                request.getEndTime() != null &&
                request.getStartTime() != null &&
                request.getRequestorId() != 0L &&
                request.getSubstituteId() != 0L &&
                request.getStatus() != null
                )
            return true;

        return false;
    }
    public static boolean validateUnscheduledRequest(Request request){
        if (
//                request.getRequestId() != 0L &&
                request.getEndTime() != null &&
                request.getStartTime() != null &&
                request.getRequestorId() != 0L &&
                request.getSubstituteId() != 0L
                )
            return true;

        return false;
    }
}
