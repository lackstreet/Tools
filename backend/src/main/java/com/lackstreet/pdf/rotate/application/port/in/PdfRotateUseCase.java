package com.lackstreet.pdf.rotate.application.port.in;

import com.lackstreet.pdf.rotate.application.model.RotatePdfCommand;

public interface PdfRotateUseCase {
    byte[] rotate(RotatePdfCommand command);
}
