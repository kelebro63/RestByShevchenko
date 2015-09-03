package ru.ID20.app.constants.enums;

/**
 * Created by s.shevchenko on 08.05.2015.
 */
public enum RequestStatuses {

    SENT(1),
    SUCCESS(2),
    LOGGED(3),
    EMPTY(4),
    PREPARED_FOR_SENDING(5),
    ERROR(-1);

    int status;

    RequestStatuses(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public static RequestStatuses getStatus(int status){
        for (RequestStatuses currentStatus : RequestStatuses.values()) {
            if (status == currentStatus.getStatus()){
                return currentStatus;
            }
        }
        return ERROR;
    }
}
