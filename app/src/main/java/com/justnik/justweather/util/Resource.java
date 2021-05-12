package com.justnik.justweather.util;

public class Resource<T> {
    public T data;
    public String message;

    public  Resource(T data,String message){

    }

    public static class Success<T> extends Resource{

        public Success(Object data) {
            super(data, null);
        }
    }
    public static class Error<T> extends Resource{

        public Error(String message) {
            super(null, message);
        }
    }
}
