package io.borys.webshop.product;

import jakarta.persistence.Embeddable;

@Embeddable
public record Price(Currency currency, double price) {
}
