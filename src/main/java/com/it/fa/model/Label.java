package com.it.fa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Label {
    public Label(String name){
        this.name = name;
    }
    private Integer lid,parent,count;
    private String name,type;
}
