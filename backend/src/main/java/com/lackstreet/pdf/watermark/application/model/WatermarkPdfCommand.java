package com.lackstreet.pdf.watermark.application.model;

import java.nio.file.Path;
import java.util.Objects;

public record WatermarkPdfCommand (
    Path document,
    String text,
    float opacity,
    WatermarkLayout layout
) {
    public WatermarkPdfCommand {
        Objects.requireNonNull(document, "Document must not be null");
        Objects.requireNonNull(text, "Text must not be null");
        Objects.requireNonNull(layout, "layout must not be null");

        if (text.isBlank()) {
            throw new IllegalArgumentException("Watermark text must not be blank");
        }

        if (!Float.isFinite(opacity) || opacity <= 0 || opacity > 1) {
            throw new IllegalArgumentException(
                    "Opacity must be greater than 0 and at most 1"
            );
        }
    }
}
