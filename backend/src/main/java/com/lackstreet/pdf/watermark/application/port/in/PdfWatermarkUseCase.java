package com.lackstreet.pdf.watermark.application.port.in;

import com.lackstreet.pdf.watermark.application.model.WatermarkPdfCommand;

public interface PdfWatermarkUseCase {
    byte[] watermark(WatermarkPdfCommand command);
}
