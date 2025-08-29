package com.nisum.usermanagement.utils;

public class GenericUtils {


    public static String extractBearer(String auth) {
        if (auth == null || auth.isBlank()){ 
            return null;
        }

        if (!auth.startsWith("Bearer ")){ 
            throw new IllegalArgumentException("Authorization debe ser Bearer <token>");
        }

        String t = auth.substring(7).trim();
        
        if (t.isEmpty()){ 
            throw new IllegalArgumentException("Token Bearer vacío");
        }

        return t;
    }
}
