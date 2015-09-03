package ru.ID20.app.constants.enums;

import ru.ID20.app.R;

/**
 * Created  by  s.shevchenko  on  07.07.2015.
 */
public enum ItemStatus {

    STATUS_ALL("all", 0),
    STATUS_NEW("new", R.string.status_new),
    STATUS_PERFORMED("performed", R.string.status_performed),
    STATUS_ACCEPTED("accepted", R.string.status_accepted),
    STATUS_COMPLETED("completed", R.string.status_completed),
    STATUS_CANCELED("canceled", R.string.status_canceled),
    STATUS_ACTIVE("active", R.string.status_active),
    STATUS_CLOSED("closed", R.string.status_closed),
    STATUS_PREPARED("prepared", R.string.status_prepared);

    private String statusName;
    private int stringResId;

    ItemStatus(String statusName, int stringResId) {
        this.statusName = statusName;
        this.stringResId = stringResId;
    }

    public String getStatusName() {
        return statusName;
    }

    public int getStringResId() {
        return stringResId;
    }

    public static ItemStatus getStatus(String statusName){
        for (ItemStatus itemStatus : values()) {
            if (statusName.equals(itemStatus.getStatusName())) {
                return itemStatus;
            }
        }
        return STATUS_NEW;
    }
}
