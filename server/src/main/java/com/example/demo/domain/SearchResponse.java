package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by hector on 26/05/2017.
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResponse {
    private boolean found;
}
