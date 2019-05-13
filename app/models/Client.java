package models;

import javax.persistence.*;

public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long id;
}
