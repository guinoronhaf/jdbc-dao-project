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

        System.out.println("\n=== TEST 3: seller findyAll ===");
        List<Seller> list2 = sellerDao.findAll();
        list2.forEach(System.out::println);

        System.out.println("\n=== TEST 4: seller insertion ===");
        Seller newSeller = new Seller(null, "Greg", "greg@gmail.com", new Date(), 4000.0, department);
        sellerDao.insert(newSeller);
        System.out.println("Inserted! New id = " + newSeller.getId());

        System.out.println("\n=== TEST 5: seller update ===");
        seller = sellerDao.findById(1);
        seller.setName("Martha Wayne");
        sellerDao.update(seller);
        System.out.println("update completed!");

    }

}
