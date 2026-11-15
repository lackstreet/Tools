package com.lackstreet.pdf.extract.adapter.in.rest;

import com.lackstreet.pdf.extract.application.model.ExtractPdfCommand;
import com.lackstreet.pdf.extract.application.port.in.ExtractPdfUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

import java.util.List;

@Path("/api/pdf")
public class ExtractPdfResource {

    private final ExtractPdfUseCase extractPdfUseCase;

    @Inject
    public ExtractPdfResource(ExtractPdfUseCase extractPdfUseCase) {
        this.extractPdfUseCase = extractPdfUseCase;
    }

    @POST
    @Path("/extract")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/pdf")
    public Response extract(
            @RestForm("file") FileUpload file,
            @RestForm("pages")List<Integer> pages) {
        if (file == null
                || file.size() == 0
                || pages == null
                || pages.isEmpty()) {
            throw new BadRequestException("Files and pages are required");
        }
        ExtractPdfCommand command = new ExtractPdfCommand(file.uploadedFile(), pages);
        byte[] extractedPdf = extractPdfUseCase.extract(command);

        return Response.ok(extractedPdf, "application/pdf")
                .header(
                        "Content-Disposition",
                        "attachment; filename=\"extracted.pdf\""
                )
                .build();
    }
}
