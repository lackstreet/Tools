package com.lackstreet.pdf.inspect.adapter.in.rest;

import com.lackstreet.pdf.inspect.application.model.InspectPdfCommand;
import com.lackstreet.pdf.inspect.application.model.PdfInfo;
import com.lackstreet.pdf.inspect.application.port.in.InspectPdfUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

@Path("/api/pdf")
public class InspectPdfResource {

    private final InspectPdfUseCase inspectPdfUseCase;

    @Inject
    public InspectPdfResource(InspectPdfUseCase inspectPdfUseCase) {
        this.inspectPdfUseCase = inspectPdfUseCase;
    }

    @POST
    @Path("/inspect")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public PdfInfo inspect(@RestForm("file") FileUpload file) {
        if (file == null) {
            throw new BadRequestException("File cannot be null.");
        }
        if (file.size() <= 0) {
            throw new BadRequestException("File size must be greater than zero.");
        }

        InspectPdfCommand inspectPdfCommand = new InspectPdfCommand(
                file.fileName(),
                file.size(),
                file.uploadedFile()
        );

        return inspectPdfUseCase.inspect(inspectPdfCommand);
    }
}
