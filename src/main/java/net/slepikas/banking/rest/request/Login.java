package net.slepikas.banking.rest.request;

import lombok.Data;

@Data
public class Login {

    private String email;

    private String pass;

}
