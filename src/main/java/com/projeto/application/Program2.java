package com.projeto.application;

import com.projeto.model.dao.DaoFactory;
import com.projeto.model.dao.DepartmentDao;

public class Program2 {

    public static void main(String[] args) {

        DepartmentDao depDao = DaoFactory.createDepartmentDao();

        System.out.println("\n=== Teste 1: findById ===");
        System.out.println(depDao.findById(2));

    }

}
