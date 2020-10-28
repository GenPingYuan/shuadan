package com.exp.shuadan.entity;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.seepine.http.lang.ArrayUtil;

import java.lang.reflect.Array;

public class ReturnMessage {

    public String status;

    public int totalCount;

    public String message;

    public Object result;


    public ReturnMessage() {
    }

    public ReturnMessage(String status, String totalCount, String message, Object result) {
        this.status = status;
        this.totalCount = Integer.parseInt(totalCount);
        this.message = message;
        this.result = result;
    }

    @Override
    public String toString() {
        return "ReturnMessage{" +
                "status='" + status + '\'' +
                ", totalCount='" + totalCount + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = Integer.parseInt(totalCount);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public <T> T[] resultToArr(Class<T> input) {
        System.out.println(this.result);
        String json = JSON.toJSONString(this.result);
        T[] array = (T[]) Array.newInstance(input, totalCount);
            try {
                array[0] = JSONObject.parseObject(json).toJavaObject(input);
            }catch (Exception e) {
                Object[] objArr = JSONArray.parseArray(json).toArray();
                for (int i = 0; i < objArr.length; i++) {
                    String tJson = JSON.toJSONString(objArr[i]);
                    array[i] = JSONObject.parseObject(tJson).toJavaObject(input);
                }
            }
        return array;
    }
}
