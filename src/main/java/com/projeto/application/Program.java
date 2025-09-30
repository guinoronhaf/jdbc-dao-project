package com.projeto.application;

import java.util.Date;
import java.util.List;

import com.projeto.model.dao.DaoFactory;
import com.projeto.model.dao.SellerDao;
import com.projeto.model.entities.Department;
import com.projeto.model.entities.Seller;

public class Program {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println("=== TEST 1: seller findyId ===");
        Seller seller = sellerDao.findById(3);
        System.out.println(seller);

        System.out.println("\n=== TEST 2: seller findyDepartment ===");
        Department department = new Department(2, null);
        List<Seller> list = sellerDao.findByDepartment(department);
        list.forEach(System.out::println);

    }

}
