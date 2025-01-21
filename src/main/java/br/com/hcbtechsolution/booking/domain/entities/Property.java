package br.com.hcbtechsolution.booking.domain.entities;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class Property {

    private String id;

    @NotBlank(message = "The field 'name' cannot be null or empty or blank (only whitespace is not allowed).")
    private String name;

    private String description;

    @Min(value = 1, message = "The field 'maxGuests' should be greater than 0")
    private int maxGuests;

    private int pricePerNight;

    public Property(String id, String name, String description, int maxGuests, int pricePerNight) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.maxGuests = maxGuests;
        this.pricePerNight = pricePerNight;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public int getPricePerNight() {
        return pricePerNight;
    }

    public void setPricePerNight(int pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public void validateGuestCount(int numberOfGuests) {
        if (numberOfGuests > maxGuests) {
            throw new IllegalArgumentException(
                    "Maximum number of guests exceeded. Max Guests allowed: " + maxGuests);
        }
    }

    public double calculateTotalPrice(int totalNights) {
        double totalPrice = totalNights * pricePerNight;

        if (totalNights >= 7)
            totalPrice *= 0.9;

        return totalPrice;
    }
}
