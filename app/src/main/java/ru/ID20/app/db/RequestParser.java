package ru.ID20.app.db;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import ru.ID20.app.db.models.CollectionsModel;

/**
 * Created by Sergey on 09.11.2014.
 */
public class RequestParser {


    private final static Gson gson = new Gson();

    /**
     * Метод для парсинга ответа /api/auth/login/ запроса
     *
     * @param responseString ответ сервера содержащий JSON объект с данными ответа
     */

    public static ResponseData parseSimpleRequest(String responseString) {
        ResponseData responseData = new ResponseData();
        try {
            JSONObject response = new JSONObject(responseString);
            if (isRequestSuccess(response)) {
                responseData.setSuccess(true);
                responseData.setResponseJson(response.getJSONObject("data"));
            } else {
                responseData.setErrorString(response.getString("data"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return responseData;
    }

//    /**
//     * Метод для парсинга ответа /api/task/accept/ или /api/task/complete/ запроса и записи новых данных в БД
//     *
//     * @param responseString ответ сервера содержащий JSON объект с данными ответа
//     * @param newStatus      новый статус документа
//     * @see ru.ID20.app.net.models.Task
//     */
//    public static ResponseData parseTaskStatusRequest(String responseString, String newStatus, boolean isComplete) {
//        ResponseData responseData = new ResponseData();
//        try {
//            JSONObject response = new JSONObject(responseString);
//            if (isRequestSuccess(response)) {
//                TaskTable.changeStatus(getIdFromRequest(response), newStatus, getDateFromRequest(response), isComplete);
//                responseData.setSuccess(true);
//            } else {
//                responseData.setErrorString(response.getString("data"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return responseData;
//    }
//
//    /**
//     * Метод для парсинга ответа /api/claim/accept/ или /api/claim/complete/ запроса и записи новых данных в БД
//     *
//     * @param responseString ответ сервера содержащий JSON объект с данными ответа
//     * @param newStatus      новый статус документа
//     * @see ru.ID20.app.net.models.Claim
//     */
//    public static ResponseData parseClaimStatusRequest(String responseString, String newStatus) {
//        ResponseData responseData = new ResponseData();
//        try {
//            JSONObject response = new JSONObject(responseString);
//            if (isRequestSuccess(response)) {
//                ClaimTable.changeStatus(getIdFromRequest(response), newStatus, getDateFromRequest(response));
//                responseData.setSuccess(true);
//            } else {
//                responseData.setErrorString(response.getString("data"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return responseData;
//    }
//
//    /**
//     * Метод для парсинга ответа /api/common/fuelList/ запроса
//     *
//     * @param responseString ответ сервера содержащий JSON объект с данными ответа
//     * @see ru.ID20.app.net.models.FuelType
//     */
//    public static ResponseData parseFuelTypeRequest(String responseString) {
//        ResponseData responseData = new ResponseData();
//        try {
//            JSONObject response = new JSONObject(responseString);
//            if (isRequestSuccess(response)) {
//                Gson gson = new Gson();
//                FuelTypeGsonModel gsonModel = gson.fromJson(responseString, FuelTypeGsonModel.class);
//                FuelTypeTable.deleteAll();
//                List<FuelTypeTable> fuelTypes = new ArrayList<FuelTypeTable>();
//                for (FuelType fuelType : gsonModel.getFuelTypeList()) {
//                    fuelTypes.add(new FuelTypeTable(fuelType));
//                }
//                DbUtils.bulkSave(fuelTypes);
//                responseData.setSuccess(true);
//            } else {
//                responseData.setErrorString(response.getString("data"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return responseData;
//    }
//
//    /**
//     * Метод для парсинга ответа /api/user/availableFuelCards/ запроса
//     *
//     * @param responseString ответ сервера содержащий JSON объект с данными ответа
//     * @see ru.ID20.app.net.models.FuelType
//     */
//    public static ResponseData parseFuelCardsRequest(String responseString) {
//        ResponseData responseData = new ResponseData();
//        try {
//            JSONObject response = new JSONObject(responseString);
//            if (isRequestSuccess(response)) {
//                Gson gson = new Gson();
//                FuelCardsGsonModel gsonModel = gson.fromJson(responseString, FuelCardsGsonModel.class);
//                FuelCardTable.deleteAll();
//                List<FuelCardTable> fuelCardsList = new ArrayList<FuelCardTable>();
//                for (FuelCards fuelCard : gsonModel.getFuelCardsList()) {
//                    fuelCardsList.add(new FuelCardTable(fuelCard));
//                }
//                DbUtils.bulkSave(fuelCardsList);
//                responseData.setSuccess(true);
//            } else {
//                responseData.setErrorString(response.getString("data"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return responseData;
//    }
//
//
//    /**
//     * Метод для получения id измененного документа при запросах изменяющих статус документов
//     *
//     * @param response JSON объект с данными ответа от сервера
//     */
//    private static int getIdFromRequest(JSONObject response) {
//        int id = -1;
//        try {
//            JSONObject dataObject = response.getJSONObject("data");
//            id = dataObject.optInt("id");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return id;
//    }
//
//    /**
//     * Метод для получения даты измененного документа при запросах изменяющих статус документов
//     *
//     * @param response JSON объект с данными ответа от сервера
//     */
//    private static String getDateFromRequest(JSONObject response) {
//        String newDate = null;
//        try {
//            JSONObject dataObject = response.getJSONObject("data");
//            newDate = dataObject.optString("updated");
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return newDate;
//    }
//
//    /**
//     * Метод для парсинга ответа /api/claim/list/ запроса и записи новых данных в БД
//     *
//     * @param responseString ответ сервера содержащий JSON объект с данными ответа
//     * @param isUpdate       true если параметр updatedOnly = 1 иначе false
//     * @see ru.ID20.app.net.models.Claim
//     */
//    public static ResponseData parseClaimListRequest(String responseString, boolean isUpdate) {
//        ResponseData responseData = new ResponseData();
//        try {
//            JSONObject response = new JSONObject(responseString);
//            if (isRequestSuccess(response)) {
//                CollectionsModel collectionsModel = getCollectionModel(response);
//                List<ClaimTable> claims = claims = new ArrayList<ClaimTable>();
//                if (!isUpdate) {
//                    ClaimTable.deleteAll();
//                }
//                for (Claim claim : collectionsModel.getClaimList()) {
//                    if (isUpdate) {
//                        claims.add(ClaimTable.updateClaim(claim));
//                    } else {
//                        claims.add(new ClaimTable(claim));
//
//                    }
//                }
//                DbUtils.bulkSave(claims);
//                makeNextPageRequests(collectionsModel, RequestType.CLAIM_LIST_REQUEST, isUpdate);
//                responseData.setSuccess(true);
//            } else {
//                responseData.setErrorString(response.getString("data"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return responseData;
//    }
//
//    /**
//     * Метод для парсинга ответа /api/claim/list/ запроса для архивных документов
//     *
//     * @param responseString ответ сервера содержащий JSON объект с данными ответа
//     * @see ru.ID20.app.net.models.Claim
//     */
//    public static ResponseData parseArchiveClaimDocRequest(String responseString) {
//        ResponseData responseData = new ResponseData();
//        try {
//            JSONObject response = new JSONObject(responseString);
//            if (isRequestSuccess(response)) {
//                CollectionsModel collectionsModel = getCollectionModel(response);
//                responseData.setCollectionsModel(collectionsModel);
//                responseData.setSuccess(true);
//            } else {
//                responseData.setErrorString(response.getString("data"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return responseData;
//    }
//
//    /**
//     * Метод для парсинга ответа /api/waybill/list/ запроса и записи новых данных в БД
//     *
//     * @param responseString ответ сервера содержащий JSON объект с данными ответа
//     * @param isUpdate       true если параметр updatedOnly = 1 иначе false
//     * @see ru.ID20.app.net.models.Task
//     */
//    public static ResponseData parseWaybillListRequest(String responseString, boolean isUpdate) {
//        ResponseData responseData = new ResponseData();
//        try {
//            JSONObject response = new JSONObject(responseString);
//            if (isRequestSuccess(response)) {
//                CollectionsModel collectionsModel = getCollectionModel(response);
//                if (!isUpdate) {
//                    WaybillsTable.deleteAll();
//                }
//                List<WaybillsTable> waybillsTables = new ArrayList<WaybillsTable>();
//                for (Waybill waybill : collectionsModel.getWaybillList()) {
//                    if (isUpdate) {
//                        waybillsTables.add(WaybillsTable.updateWaybill(waybill));
//                    } else {
//                        waybillsTables.add(new WaybillsTable(waybill));
//                    }
//                }
//                DbUtils.bulkSave(waybillsTables);
//                makeNextPageRequests(collectionsModel, RequestType.WAYBILL_LIST_REQUEST, isUpdate);
//                responseData.setSuccess(true);
//            } else {
//                responseData.setErrorString(response.getString("data"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return responseData;
//    }
//
//
//   /**
//     * Метод для парсинга ответа /api/claim/list/запроса и записи новых данных в БД при наличии параметра currentPage > 0
//     *
//     * @param responseString ответ сервера содержащий JSON объект с данными ответ
//     * @param isUpdate       true если параметр updatedOnly = 1 иначе false
//     * @see ru.ID20.app.net.models.Task
//     */
//    public static ResponseData parseClaimLoadPageRequest(String responseString, boolean isUpdate) {
//        ResponseData responseData = new ResponseData();
//        try {
//            JSONObject response = new JSONObject(responseString);
//            if (isRequestSuccess(response)) {
//                CollectionsModel collectionsModel = getCollectionModel(response);
//                List<ClaimTable> claims = new ArrayList<ClaimTable>();
//                for (Claim claim : collectionsModel.getClaimList()) {
//                    if (isUpdate) {
//                        claims.add(ClaimTable.updateClaim(claim));
//                    } else {
//                        claims.add(new ClaimTable(claim));
//                    }
//
//                }
//                DbUtils.bulkSave(claims);
//                responseData.setSuccess(true);
//            } else {
//                responseData.setErrorString(response.getString("data"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return responseData;
//    }
//
//
//    /**
//     * Метод для парсинга ответа /api/waybill/list/ запроса и записи новых данных в БД при наличии параметра currentPage > 0
//     *
//     * @param responseString ответ сервера содержащий JSON объект с данными ответ
//     * @param isUpdate       true если параметр updatedOnly = 1 иначе false
//     * @see ru.ID20.app.net.models.Task
//     */
//    public static ResponseData parseWaybillLoadPageRequest(String responseString, boolean isUpdate) {
//        ResponseData responseData = new ResponseData();
//        try {
//            JSONObject response = new JSONObject(responseString);
//            if (isRequestSuccess(response)) {
//                CollectionsModel collectionsModel = getCollectionModel(response);
//                List<WaybillsTable> waybillsTables = new ArrayList<WaybillsTable>();
//                for (Waybill waybill : collectionsModel.getWaybillList()) {
//                    if (isUpdate) {
//                        waybillsTables.add(WaybillsTable.updateWaybill(waybill));
//                    } else {
//                        waybillsTables.add(new WaybillsTable(waybill));
//                    }
//                }
//                DbUtils.bulkSave(waybillsTables);
//                responseData.setSuccess(true);
//            } else {
//                responseData.setErrorString(response.getString("data"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return responseData;
//    }
//
//    /**
//     * Метод для парсинга ответа /api/user/availableCarsData/ запроса и записи новых данных в БД при наличии параметра currentPage > 0
//     *
//     * @param responseString ответ сервера содержащий JSON объект с данными ответ
//     * @see ru.ID20.app.net.models.CarData
//     */
//    public static ResponseData parseCarDataRequest(String responseString) {
//        ResponseData responseData = new ResponseData();
//        try {
//            JSONObject response = new JSONObject(responseString);
//            if (isRequestSuccess(response)) {
//                Gson gson = new Gson();
//                CarDataGsonModel model = gson.fromJson(response.toString(), CarDataGsonModel.class);
//                List<CarDataTable> carDataTableList = new ArrayList<CarDataTable>();
//                for (CarData carData : model.getCarDataList()) {
//                    carDataTableList.add(new CarDataTable(carData));
//                }
//                CarDataTable.deleteAll();
//                DbUtils.bulkSave(carDataTableList);
//                responseData.setSuccess(true);
//            } else {
//                responseData.setErrorString(response.getString("data"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return responseData;
//    }
//
    /**
     * Метод для перевода JSON объекта в ORM при помощи GSON библиотеки
     *
     * @param response JSON объект с данными ответа
     */
    private synchronized static CollectionsModel getCollectionModel(JSONObject response) {
        JSONObject data = null;
        try {
            data = response.getJSONObject("data");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gson.fromJson(data.toString(), CollectionsModel.class);
    }
//
//    /**
//     * Метод для парсинга ответа /api/claim/create/ запроса
//     *
//     * @param responseString ответ сервера содержащий JSON объект с данными ответа
//     * @see ru.ID20.app.net.models.Task
//     */
//    public static ResponseData parseClaimAddRequest(String responseString) {
//        ResponseData responseData = new ResponseData();
//        try {
//            JSONObject response = new JSONObject(responseString);
//            if (isRequestSuccess(response)) {
//                responseData.setResponseJson(response.getJSONObject("data"));
//                responseData.setSuccess(true);
//            } else {
//                responseData.setErrorString(response.getString("data"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return responseData;
//    }
//
//    /**
//     * Метод для парсинга ответа /api/waybill/open/ запроса
//     *
//     * @param responseString ответ сервера содержащий JSON объект с данными ответа
//     * @see ru.ID20.app.net.models.Task
//     */
//    public static ResponseData parseCreateWaybillRequest(String responseString) {
//        ResponseData responseData = new ResponseData();
//        try {
//            JSONObject response = new JSONObject(responseString);
//            if (isRequestSuccess(response)) {
//                responseData.setResponseJson(response.getJSONObject("data"));
//                responseData.setSuccess(true);
//            } else {
//                responseData.setErrorString(response.getString("data"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return responseData;
//    }
//
//    /**
//     * Метод для парсинга ответа /api/user/updateProfile/ или /api/user/changePassword/запроса
//     *
//     * @param responseString ответ сервера содержащий JSON объект с данными ответа
//     * @see ru.ID20.app.net.models.Task
//     */
//    public static ResponseData parseUpdateProfileRequest(String responseString) {
//        ResponseData responseData = new ResponseData();
//        try {
//            JSONObject response = new JSONObject(responseString);
//            if (isRequestSuccess(response)) {
//                responseData.setSuccess(true);
//            } else {
//                responseData.setErrorString(response.getString("data"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return responseData;
//    }
//
//    /**
//     * Метод для парсинга ответа /api/waybill/close/ и /api/waybill/update/ запроса
//     *
//     * @param responseString ответ сервера содержащий JSON объект с данными ответа
//     * @see ru.ID20.app.net.models.Task
//     */
//    public static ResponseData parseCloseWaybillRequest(String responseString) {
//        ResponseData responseData = new ResponseData();
//        try {
//            JSONObject response = new JSONObject(responseString);
//            if (isRequestSuccess(response)) {
//                responseData.setSuccess(true);
//                responseData.setResponseJson(response.getJSONObject("data"));
//            } else {
//                responseData.setErrorString(response.getString("data"));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return responseData;
//    }

    /**
     * Метод проверки валидности запроса
     *
     * @param response ответ сервера содержащий JSON объект с данными ответа
     * @see
     */
    private synchronized static boolean isRequestSuccess(JSONObject response) {
        boolean isSuccess = false;
        try {
            isSuccess = response.getBoolean("success");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return isSuccess;
    }
}
