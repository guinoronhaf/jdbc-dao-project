package com.projeto.model.dao;

import com.projeto.db.DB;
import com.projeto.model.dao.impl.DepartmentDaoJDBC;
import com.projeto.model.dao.impl.SellerDaoJDBC;

public class DaoFactory {

    public static SellerDao createSellerDao() {
        return new SellerDaoJDBC(DB.getConnection()); //ao definir a interface como tipo de retorno, a implementação não é exposta.
    }

    public static DepartmentDao createDepartmentDao() {
        return new DepartmentDaoJDBC(DB.getConnection());
    }

}
