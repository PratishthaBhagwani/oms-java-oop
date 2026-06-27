package com.oms.test;

import com.oms.exceptions.OutOfStockException;
import com.oms.factory.ProductFactory;
import com.oms.model.order.CartItem;
import com.oms.model.order.ShoppingCart;
import com.oms.model.product.Electronics;
import com.oms.model.product.Grocery;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ShoppingCartTest {
    private ShoppingCart cart;
    private Electronics phone;
    private Grocery rice;

    @BeforeEach
    void setUp(){
        cart=new ShoppingCart("CUST-001");
        phone=(Electronics) ProductFactory.createElectronics("Samsung", 32999, 15, 12);
        rice=(Grocery) ProductFactory.createGrocery("Rice",450,100);
    }
    @Test
    void cartShouldBeEmptyInitially(){
        assertTrue(cart.isEmpty());
    }

    @Test
    void addingItemShouldMakeCartNonEmpty() {
        cart.addItem(new CartItem(phone, 1));
        assertFalse(cart.isEmpty());
    }

    @Test
    void totalShouldBeCorrect() {
        cart.addItem(new CartItem(phone, 1));
        cart.addItem(new CartItem(rice, 2));

        double expected = phone.getFinalPrice() * 1
                + rice.getFinalPrice()  * 2;
        assertEquals(expected, cart.getTotal(), 0.01);
    }

    @Test
    void removingItemShouldEmptyCart() {
        cart.addItem(new CartItem(phone, 1));
        cart.removeItem(0);
        assertTrue(cart.isEmpty());
    }

    @Test
    void groceryTaxShouldBeZero() {
        assertEquals(0.0, rice.calculateTax());
    }

    @Test
    void electronicsTaxShouldBe18Percent() {
        double expected = phone.getBasePrice() * 0.18;
        assertEquals(expected, phone.calculateTax(), 0.01);
    }

}
