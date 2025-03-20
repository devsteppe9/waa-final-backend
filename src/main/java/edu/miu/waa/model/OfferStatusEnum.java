package edu.miu.waa.model;

public enum OfferStatusEnum {
    PENDING("pending"),
    APPROVED("approved"),
    REJECTED("rejected");

    private final String statusText;

    OfferStatusEnum(String statusText) {
        this.statusText = statusText;
    }

    public String getStatusText() {
        return statusText;
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
