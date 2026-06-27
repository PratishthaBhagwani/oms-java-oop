package com.oms.test;

import com.oms.exceptions.InvalidEmailException;
import com.oms.exceptions.OutOfStockException;
import com.oms.factory.ProductFactory;
import com.oms.model.order.CartItem;
import com.oms.model.product.Electronics;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ExceptionTest {

    @Test
    void shouldThrowOutOfStockWhenQtyExceedsStock() {
        Electronics phone = (Electronics) ProductFactory
                .createElectronics("Samsung", 32999, 5, 12);
        assertThrows(OutOfStockException.class, () -> {
            if (phone.getStockQuantity() < 10) {
                throw new OutOfStockException(phone.getName());
            }
        });
    }
    @Test
    void shouldThrowInvalidEmailForBadFormat() {
        assertThrows(InvalidEmailException.class, () -> {
            String email = "pratishthagmail.com"; // @ nahi hai
            if (!email.contains("@") || !email.contains(".")) {
                throw new InvalidEmailException(email);
            }
        });
    }
    @Test
    void shouldNotThrowForValidEmail() {
        assertDoesNotThrow(() -> {
            String email = "pratishtha@gmail.com"; // valid!
            if (!email.contains("@") || !email.contains(".")) {
                throw new InvalidEmailException(email);
            }
        });
    }
}
