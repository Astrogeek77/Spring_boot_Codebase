package com.gautam.SOPE.orderengine.util;

import com.gautam.SOPE.orderengine.state.*;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OrderStateConverter implements AttributeConverter<OrderState, String> {

    @Override
    public String convertToDatabaseColumn(OrderState state) {
        if (state instanceof NewState) return "NEW";
        if (state instanceof PaidState) return "PAID";
        if (state instanceof ShippedState) return "SHIPPED";
        return "UNKNOWN";
    }

    @Override
    public OrderState convertToEntityAttribute(String dbData) {
        if ("NEW".equals(dbData)) return new NewState();
        if ("PAID".equals(dbData)) return new PaidState();
        if ("SHIPPED".equals(dbData)) return new ShippedState();
        return new NewState(); // Default fallback
    }
}