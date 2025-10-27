package com.projeto.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.projeto.db.DB;
import com.projeto.db.DbException;
import com.projeto.model.dao.SellerDao;
import com.projeto.model.entities.Department;
import com.projeto.model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

    private Connection conn;

    public SellerDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Seller seller) {

        PreparedStatement st = null;

        String query = 
               "INSERT INTO seller "
               + "(Name, Email, BirthDate, BaseSalary, DepartmentId) "
               + "VALUES "
               + "(?, ?, ?, ?, ?)";

        try {

            st = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);

            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            //Date conversion is necessary, since we have java.sql.Date for setDate and java.util.Date for getBirthDate
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0) {

                ResultSet rs = st.getGeneratedKeys();

                if (rs.next()) {
                    int id = rs.getInt(1); //access to id
                    seller.setId(id); //associating seller to its id
                }

                DB.closeResultSet(rs); //closing ResultSet, since it doens't exist only on finally's scope

            } else {
                throw new DbException("Unexpected error! No rows affected!");
            }

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void update(Seller seller) {

        PreparedStatement st = null;

        String query =
              "UPDATE seller "
              + "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? "
              + "WHERE Id = ?";

        try {

            st = conn.prepareStatement(query);

            st.setString(1, seller.getName());
            st.setString(2, seller.getEmail());
            st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
            st.setDouble(4, seller.getBaseSalary());
            st.setInt(5, seller.getDepartment().getId());
            st.setInt(6, seller.getId());

			int rowsAffected = st.executeUpdate();

			System.out.println("Rows affected: " + rowsAffected);

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public void deleteById(Integer id) {

        PreparedStatement st = null;

        String query = 
               "DELETE FROM seller "
               + "WHERE Id = ?";

        try {
        
            st = conn.prepareStatement(query);

            st.setInt(1, id);

            int rowsAffected = st.executeUpdate();

            if (rowsAffected > 0)
                System.out.println("Delete complete!");
            else
                System.out.println("Id not found.");

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }

    }

    @Override
    public Seller findById(Integer id) {

        PreparedStatement st = null;
        ResultSet rs = null;

        String query = 
               "SELECT seller.*,department.Name as DepName "
               + "FROM seller INNER JOIN department "
               + "ON seller.DepartmentId = department.Id "
               + "WHERE seller.Id = ?";

        try {

            st = conn.prepareStatement(query);

            st.setInt(1, id); //atribui um valor ao placeholder

            rs = st.executeQuery(); //executa a query
            
            if (rs.next()) {
                Department department = buildDepartment(rs);
                Seller seller = buildSeller(rs, department);
                return seller;
            }

            return null;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
            // dont't close connection cause it can be used later
        }

    }

    @Override
    public List<Seller> findAll() {

        Statement st = null;
        ResultSet rs = null;

        String query = 
               "SELECT seller.*,department.Name as DepName "
               + "FROM seller INNER JOIN department "
               + "ON seller.DepartmentId = department.Id "
               + "ORDER BY Name";

        try {
            
            st = conn.createStatement();

            rs = st.executeQuery(query);

            List<Seller> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();

            while (rs.next()) {

                Department department = map.get(rs.getInt("DepartmentId"));

                if (department == null) {
                    department = buildDepartment(rs);
                    map.put(department.getId(), department);
                }

                list.add(buildSeller(rs, department));

            }

            return list;

        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }

    }

    @Override
    public List<Seller> findByDepartment(Department department) {

        PreparedStatement st = null;
        ResultSet rs = null;

        String query = 
               "SELECT seller.*,department.Name as DepName "
               + "FROM seller INNER JOIN department "
               + "ON seller.DepartmentId = department.Id "
               + "WHERE DepartmentId = ? "
               + "ORDER BY Name";

        try {

            st = conn.prepareStatement(query);

            st.setInt(1, department.getId());

            rs = st.executeQuery();

            List<Seller> list = new ArrayList<>();

            Map<Integer, Department> map = new HashMap<>(); //map to control repetitve department instantiations
            
            while (rs.next()) {

                Department dep = map.get(rs.getInt("DepartmentId"));
                //looking for DepartmentId on map

                if (dep == null) { //if it is null, take it to map
                    dep = buildDepartment(rs);
                    map.put(dep.getId(), dep);
                }

                list.add(buildSeller(rs, dep));

            }

            return list;

        } catch(SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeResultSet(rs);
            DB.closeStatement(st);
        }

    }

    private Seller buildSeller(ResultSet rs, Department department) throws SQLException {

        Seller seller = new Seller();

        seller.setId(rs.getInt("Id"));

        seller.setName(rs.getString("Name"));

        seller.setEmail(rs.getString("Email"));

        seller.setBirthDate(rs.getDate("BirthDate"));;

        seller.setBaseSalary(rs.getDouble("BaseSalary"));

        seller.setDepartment(department);

        return seller;

        //o throws no corpo do método faz com que não seja necessário tratar explicitamente a exceção, já que os outros métodos de acesso ao bd já tratam ela

    }

    private Department buildDepartment(ResultSet rs) throws SQLException {

        Department department = new Department();

        department.setId(rs.getInt("DepartmentId"));

        department.setName(rs.getString("DepName"));

        return department;

    }

}
