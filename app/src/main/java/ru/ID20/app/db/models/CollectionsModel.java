package ru.ID20.app.db.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Sergey on 11.11.2014.
 */
public class CollectionsModel {

    //    @SerializedName("claims")
//    private Claim[] claimArray;
//
    @SerializedName("tasks")
    private TaskModel[] taskArray;

    private List<TaskModel> taskModelList;
//
//    @SerializedName("waybills")
//    private Waybill[] waybillArray;

    @SerializedName("pagesCount")
    private int pagesCount;

    @SerializedName("currentPage")
    private int currentPage;

    public int getPagesCount() {
        return pagesCount;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    //    public List<Claim> getClaimList() {
//        return Arrays.asList(claimArray);
//    }
//
    public List<TaskModel> getTaskList() {
        if (taskModelList != null){
            return taskModelList;
        } else if (taskArray == null){
            taskModelList = new ArrayList<>();
            return taskModelList ;
        } else {
            return taskModelList = Arrays.asList(taskArray);
        }
    }
//
//    public List<Waybill> getWaybillList() {
//        return Arrays.asList(waybillArray);
//    }
}
