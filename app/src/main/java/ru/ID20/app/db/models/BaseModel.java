package ru.ID20.app.db.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;

/**
 * Created  by  s.shevchenko  on  01.07.2015.
 */
public abstract class BaseModel extends Model {

    public static final String REQUEST_STATUS = "REQUEST_STATUS";
    @Column(name = REQUEST_STATUS)
    protected Integer requestStatus;

    public static final String REQUEST_ERROR = "REQUEST_ERROR";
    @Column(name = REQUEST_ERROR)
    protected String requestError;

    public BaseModel() {
        super();
    }

    public Integer getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(Integer requestStatus) {
        this.requestStatus = requestStatus;
    }

    public String getRequestError() {
        return requestError;
    }

    public void setRequestError(String requestError) {
        this.requestError = requestError;
    }
}
