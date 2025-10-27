package com.projeto.application;

import java.util.List;

import com.projeto.model.dao.DaoFactory;
import com.projeto.model.dao.DepartmentDao;
import com.projeto.model.entities.Department;

public class Program2 {

    public static void main(String[] args) {

        DepartmentDao depDao = DaoFactory.createDepartmentDao();

        System.out.println("\n=== Teste 1: findById ===");
        System.out.println(depDao.findById(2));

        System.out.println("\n=== Teste 2: findAll ===");
        List<Department> list = depDao.findAll();
        list.forEach(System.out::println);

        /* System.out.println("\n=== Teste 3: Insert ===");
        Department dep = new Department(null, "Clothes");
        depDao.insert(dep);
        System.out.println(dep); */

        System.out.println("\n=== Teste 4: update ===");
		Department dep = depDao.findById(6);
		dep.setName("Food");
		depDao.update(dep);
		System.out.println("updated!");

        System.out.println("\n=== Teste 5: deleteById ===");
		int id = 9;
		depDao.deleteById(id);

    }

}
