package com.lackstreet.pdf.watermark.application.port.out;

import com.lackstreet.pdf.watermark.application.model.WatermarkLayout;

import java.nio.file.Path;

public interface PdfWatermarkPage {
    byte[] watermark(
            Path document,
            String text,
            float opacity,
            WatermarkLayout layout
    );
}
