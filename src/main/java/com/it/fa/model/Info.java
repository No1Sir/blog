package com.it.fa.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Info implements Serializable {
    private Integer comment_count,attach_count,content_count;
}
