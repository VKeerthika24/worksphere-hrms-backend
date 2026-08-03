package com.worksphere.hrms.util;

public class EmployeeCodeGenerator {

    private EmployeeCodeGenerator() {
    }

    public static String generate(Long id) {
        return String.format("EMP%04d", id);
    }
}