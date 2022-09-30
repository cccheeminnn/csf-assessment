package vttp2022.assessment.csf.orderbackend.models;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;

public class Response {

    private String message;
    private int code;
    private JsonArray data;

    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public JsonArray getData() {
        return data;
    }
    public void setData(JsonArray data) {
        this.data = data;
    }

    public JsonObject toJson() {
        if (data == null) {
            data = Json.createArrayBuilder().build();
        }
        return Json.createObjectBuilder()
            .add("message", message)
            .add("code", code)
            .add("data", data)
            .build();
    }

}
