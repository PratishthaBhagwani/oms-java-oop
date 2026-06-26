package com.oms.db;

import com.oms.model.product.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;


public class ProductDAO {
    private  Connection conn;
    public ProductDAO(){
        this.conn=DBConnection.getInstance().getConnection();
    }
    public void save(Product product) {
        String sql = """
               
                INSERT INTO products 
               (product_id,name,base_price,stock_qty, category, warranty_months, size, expiry_date)
               VALUES(?,?,?,?,?,?,?,?)
               ON DUPLICATE KEY UPDATE 
               stock_qty = VALUES(stock_qty),
               base_price = VALUES(base_price)       
               """;
        try(PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setString(1, product.getProductId());
            ps.setString(2, product.getName());
            ps.setDouble(3, product.getBasePrice());
            ps.setInt(4, product.getStockQuantity());
            ps.setString(5, product.getCategory());

            if(product instanceof Electronics e) {
                ps.setInt(6, e.getWarrantyMonths());
                ps.setNull(7, Types.VARCHAR);
                ps.setNull(8, Types.DATE );
            }else if(product instanceof Clothing c){
                ps.setNull(6,Types.INTEGER);
                ps.setString(7,c.getSize());
                ps.setNull(8,Types.DATE );
            }else if(product instanceof Grocery g){
                ps.setNull(6,Types.INTEGER);
                ps.setNull(7,Types.VARCHAR);
                ps.setNull(8, Types.DATE);
            }
            ps.executeUpdate() ;
        }catch (SQLException e){
            System.out.println("Error saving product: " + e.getMessage());
        }

    }
    public ArrayList<Product> findAll(){
        ArrayList<Product> list=new ArrayList<>();
        String sql="SELECT * FROM products";
        try(Statement st=conn.createStatement();
        ResultSet rs= st.executeQuery(sql)){
            while(rs.next()) list.add(mapRow(rs));
        } catch (SQLException e) {
            System.out.println("Error loading products: " + e.getMessage());
        }
        return list;
    }
    public ArrayList<Product> search(String keyword){
        ArrayList<Product> list=new ArrayList<>();
        String sql="SELECT * FROM products WHERE LOWER(name) LIKE ?";
        try(PreparedStatement ps=conn.prepareStatement(sql)){
            ps.setString(1,"%" + keyword.toLowerCase() + "%");
            ResultSet rs=ps.executeQuery();
            while(rs.next())  list.add(mapRow(rs));

        }catch (SQLException e) {
            System.out.println("Error searching: " + e.getMessage());
        }
        return list;
    }
    public boolean isEmpty() {
        String sql = "SELECT COUNT(*) FROM products";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            if (rs.next()) return rs.getInt(1) == 0;
        } catch (SQLException e) {
        }
        return true;
    }
    private Product mapRow(ResultSet rs) throws SQLException{
        String id       = rs.getString("product_id");
        String name     = rs.getString("name");
        double price    = rs.getDouble("base_price");
        int stock       = rs.getInt("stock_qty");
        String category = rs.getString("category");

        return switch (category) {
            case "Electronics" -> new Electronics(
                    id, name, price, stock,
                    rs.getInt("warranty_months"));
            case "Clothing" -> new Clothing(
                    id, name, price, stock,
                    rs.getString("size"));
            case "Grocery" -> new Grocery(
                    id, name, price, stock);
            default -> throw new IllegalStateException(
                    "Unknown category: " + category);
        };
    }
}
