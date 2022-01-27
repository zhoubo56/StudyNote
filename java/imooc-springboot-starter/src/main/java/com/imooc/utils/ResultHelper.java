package com.imooc.utils;

import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class ResultHelper {

    public static Map<String, String> format(BindingResult bindingResult) {
        var resultMap = new HashMap<String, String>();
        for (var error :
                bindingResult.getFieldErrors()
        ) {
            resultMap.put(error.getField(), error.getDefaultMessage());
        }

        return resultMap;
    }
}
