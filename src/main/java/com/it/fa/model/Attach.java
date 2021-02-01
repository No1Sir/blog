package com.it.fa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attach {
    private String ftype,fname,fkey;
    private Integer authorId,created;
}
