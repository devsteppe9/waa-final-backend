package edu.miu.waa.model;

import lombok.Getter;

@Getter
public enum OfferStatusEnum {
    OPEN("OPEN"),
    ACCEPTED("ACCEPTED"),
    REJECTED("REJECTED"),
    CANCELLED("CANCELLED");

    private final String statusText;

    OfferStatusEnum(String statusText) {
        this.statusText = statusText;
    }

    public static OfferStatusEnum fromString(String text) throws IllegalArgumentException {
        for (OfferStatusEnum b : OfferStatusEnum.values()) {
            if (b.statusText.equalsIgnoreCase(text)) {
                return b;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
}
