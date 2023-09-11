package n19dccn112.model.enumeration;

import lombok.Getter;

@Getter
public enum OrderStatus {
    PREPARE,
    CORFIRM,
    SHIPPING,
    SUCCESS,
    CANCELED
}