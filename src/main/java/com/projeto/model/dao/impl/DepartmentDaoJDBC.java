package com.projeto.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.projeto.db.DB;
import com.projeto.db.DbException;
import com.projeto.model.dao.DepartmentDao;
import com.projeto.model.entities.Department;

public class DepartmentDaoJDBC implements DepartmentDao {

    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department department) {

    }

    @Override
    public void update(Department department) {

    }

    @Override
    public void deleteById(Integer id) {

    }

    @Override
    public Department findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        String query = "SELECT * FROM department WHERE Id = ?";

        try {

            st = conn.prepareStatement(query);

            st.setInt(1, id);

            rs = st.executeQuery();

            if (rs.next())
                return buildDepartment(rs);

            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

    }

    @Override
    public List<Department> findAll() {
        return null;
    }

    private Department buildDepartment(ResultSet rs) throws SQLException {

        Department dep = new Department();

        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));

        return dep;

    }

}
