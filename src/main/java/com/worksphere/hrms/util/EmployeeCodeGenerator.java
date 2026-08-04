package com.worksphere.hrms.util;

import java.util.UUID;

public class EmployeeCodeGenerator {

    private EmployeeCodeGenerator() {
    }

    public static String generate() {
        return "EMP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}