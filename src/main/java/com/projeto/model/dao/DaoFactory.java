package com.projeto.model.dao;

import com.projeto.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(); //ao definir a interface como tipo de retorno, a implementação não é exposta.
    }

}
