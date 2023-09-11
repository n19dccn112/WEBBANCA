package n19dccn112.model.Auth;

import com.n19dccn112.model.enumeration.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderStatusClass {
    Long id;
    OrderStatus orderStatus;
    BigDecimal amountOrderStatus;
}