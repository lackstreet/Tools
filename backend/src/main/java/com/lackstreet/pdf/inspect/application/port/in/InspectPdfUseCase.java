package com.lackstreet.pdf.inspect.application.port.in;

import com.lackstreet.pdf.inspect.application.model.InspectPdfCommand;
import com.lackstreet.pdf.inspect.application.model.PdfInfo;

public interface InspectPdfUseCase {
    PdfInfo inspect(InspectPdfCommand command);
}
