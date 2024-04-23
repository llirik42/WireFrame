package ru.nsu.kondrenko.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Setter
@Getter
public class IntPoint {
    private int x;
    private int y;

    public double distance(IntPoint p) {
        return Math.sqrt(Math.pow(p.x - x, 2) + Math.pow(p.y - y, 2));
    }
}
