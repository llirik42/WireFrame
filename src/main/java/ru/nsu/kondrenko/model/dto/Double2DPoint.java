package ru.nsu.kondrenko.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class Double2DPoint implements Serializable {
    private double x;

    private double y;
}
