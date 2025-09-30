package com.projeto.application;

import java.util.Date;

import com.projeto.model.dao.DaoFactory;
import com.projeto.model.dao.SellerDao;
import com.projeto.model.entities.Department;
import com.projeto.model.entities.Seller;

public class Program {

    public static void main(String[] args) {

        SellerDao sellerDao = DaoFactory.createSellerDao();

        System.out.println(sellerDao.findById(8));

    }

}
