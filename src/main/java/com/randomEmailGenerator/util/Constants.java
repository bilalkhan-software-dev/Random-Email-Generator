package com.randomEmailGenerator.util;

import java.util.concurrent.TimeUnit;

public class Constants {

    public static final String GMAIL_DOMAIN = "@gmail.com";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_HEADER = "Bearer ";
    public static String SECRET_KEY = "eyJhbGciOiJIUzI1NiJ9.eyJpZCI6MSwiZnVsbCBuYW1lIjoiQmlsYWwgS2hhbiIsInN1YiI6ImJpbGFsa2hhbkBnbWFpbC5jb20iLCJpYXQiOjE3NTQ1MDk2MzEsImV4cCI6MTc1NDUxMTc5MX0.Phs8JrWaLq5DQQeHoA2OKZnIECskjf1p2ceCz0xHOWo";
    public static final long JWT_EXPIRATION_TIME = TimeUnit.DAYS.toMillis(7);
    public static final String ADMIN = "bilalkhan.devse@gmail.com";


}
