package com.lackstreet.pdf.rotate.application.model;

public enum RotationAngle {
    DEGREE_90(90), DEGREE_180(180), DEGREE_270(270);
    private final int degrees;

    RotationAngle(int degrees) {
        this.degrees = degrees;
    }

    public int degrees() {
        return degrees;
    }

    public static RotationAngle fromDegrees(int degrees) {
        return switch (degrees) {
            case 90 -> DEGREE_90;
            case 180 -> DEGREE_180;
            case 270, -90 -> DEGREE_270;
            default -> throw new IllegalArgumentException("Invalid degrees: " + degrees);
        };
    }
}
