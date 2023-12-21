package main.org.example.model;

import lombok.Data;

@Data
public class Currency {
    private int id;
    private int numCode;
    private String charCode;
    private int scale;
    private String name;
    private double rate;
}
