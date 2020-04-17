package com.covid.korona.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Model {
    private String infectedCount;
    private String newInfectedCount;
    private String recoveredCount;
    private String deadCount;
    private String activeInfectedCount;
}
