package net.slepikas.banking.db.dao;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;

@Data
@Entity
@Table(name = "customers", schema = "sample")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @NotBlank
    @Length(max = 254)
    @Column(name = "email")
    private String email;

    @NotBlank
    @Length(max = 100)
    @Column(name = "pass")
    private String pass;

}
