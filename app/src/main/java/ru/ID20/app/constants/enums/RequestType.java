package ru.ID20.app.constants.enums;

/**
 * Created by Sergey on 08.11.2014.
 */
public enum RequestType {

    LOGIN_REQUEST("https://id20.ru/api/auth/login/"),

    LOGOUT_REQUEST("https://id20.ru/api/auth/logout/"),

    CLAIM_INFO_REQUEST("https://id20.ru/api/claim/view/"),

    CLAIM_CREATE_REQUEST("https://id20.ru/api/claim/create/"),

    CLAIM_LIST_REQUEST("https://id20.ru/api/claim/list/"),

    TASK_LIST_REQUEST("https://id20.ru/api/task/list/"),

    START_REQUEST(""),
    START_UPDATE(""),

    NEW_CLAIM_ACCEPT("https://id20.ru/api/claim/accept/"),

    NEW_TASK_ACCEPT("https://id20.ru/api/task/accept/"),
    TASK_COMPLETE("https://id20.ru/api/task/complete/"),

    WAYBILL_LIST_REQUEST("https://id20.ru/api/waybill/list/"),
    CREATE_WAYBILL("https://id20.ru/api/waybill/open/"),
    CLOSE_WAYBILL("https://id20.ru/api/waybill/close/"),
    EDIT_WAYBILL("https://id20.ru/api/waybill/update/"),

    CAR_DATA_REQUEST("https://id20.ru/api/user/availableCarsData/"),
    FUEL_DATA_REQUEST("https://id20.ru/api/common/fuelList/"),
    FUEL_CARD_REQUEST("https://id20.ru/api/user/availableFuelCards/"),

    USER_DATA_REQUEST("https://id20.ru/api/user/profile/"),
    USER_UPDATE_PROFILE_REQUEST("https://id20.ru/api/user/updateProfile/"),
    USER_CHANGE_PASS_REQUEST("https://id20.ru/api/user/changePassword/");

    private String url;

    RequestType(String url) {
        this.url = url;

    }

    public String getUrl() {
        return url;
    }
}
