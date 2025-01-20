package br.com.hcbtechsolution.booking.domain.entities;

import jakarta.validation.constraints.NotBlank;

public class User {

    private String id;

    @NotBlank(message = "The field 'name' cannot be null or empty or blank (only whitespace is not allowed).")
    private String name;

    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
