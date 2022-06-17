package com.example.demo.dto;

import lombok.Data;

@Data
public class FooDto {
    private Long id;
    private String name;

    public FooDto(Long id, String name){
        this.id = id;
        this.name = name;
    }
}
