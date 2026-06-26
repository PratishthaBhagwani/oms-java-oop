package com.oms.db;

import com.oms.model.user.*;
import java.sql.*;
import java.util.ArrayList;

public class UserDAO {
    private Connection conn;
    public UserDAO(){
        this.conn=DBConnection.getInstance().getConnection();
    }

    public void save(User user) {
        String sql = """
                INSERT INTO users 
                (user_id, name, email, password_hash, role, wallet_balance, store_name)
                VALUES(?,?,?,?,?,?,?)
                ON DUPLICATE KEY UPDATE 
                name=VALUES(name),
                wallet_balance=VALUES(wallet_balance)
                """;
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, user.getUserId());
            ps.setString(2, user.getName());
            ps.setString(3, user.getEmail());
            ps.setString(4, getPasswordHash(user));
            ps.setString(5, user.getRole());
            ps.setDouble(6, user instanceof Customer c ? c.getWalletBalance() : 0.0);
            ps.setString(7, user instanceof Seller s ? s.getStoreName() : null);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error saving user: " + e.getMessage());
        }
    }
    public User findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapRow(rs);
        } catch (SQLException e) {
            System.out.println("Error finding user: " + e.getMessage());
        }
        return null;
    }
    public ArrayList<User> findAll(){
        ArrayList<User> users=new ArrayList<>();
        String sql="SELECT * FROM users";
        try(Statement st=conn.createStatement();
        ResultSet rs=st.executeQuery(sql)){
            while(rs.next())  users.add(mapRow(rs));
        }
        catch (SQLException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
        return users;
}
public boolean isEmpty(){
        String sql="SELECT COUNT(*) FROM users";
        try(Statement st= conn.createStatement();
        ResultSet rs=st.executeQuery(sql)){
            if(rs.next()) return rs.getInt(1)==0;
        } catch (SQLException e) { }
            return  true;
    }
    private User mapRow(ResultSet rs) throws SQLException{
        String role=rs.getString("role");
        String userId = rs.getString("user_id");
        String name   = rs.getString("name");
        String email  = rs.getString("email");
        double wallet = rs.getDouble("wallet_balance");
        String store  = rs.getString("store_name");

        return switch(role){
            case "CUSTOMER" -> {
                Customer c = new Customer(userId, name, email);
                c.topUpWallet(wallet);
                yield c;
            }
            case "SELLER" -> new Seller(userId,name,email,store);
            case "ADMIN" -> new Admin(userId,name, email);
            default -> throw new IllegalStateException("Unknown role: " + role);
        };
    }
    private String getPasswordHash(User user){
        try{
            var field = User.class.getDeclaredField("passwordHash");
            field.setAccessible(true);
            return (String) field.get(user);
        }catch(Exception e){
            return "";
        }
    }
}

