package com.lackstreet.pdf.watermark.adapter.in.rest;

import com.lackstreet.pdf.watermark.application.model.WatermarkLayout;
import com.lackstreet.pdf.watermark.application.model.WatermarkPdfCommand;
import com.lackstreet.pdf.watermark.application.port.in.PdfWatermarkUseCase;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestForm;
import org.jboss.resteasy.reactive.multipart.FileUpload;

@Path("/api/pdf")
public class PdfWatermarkResource {

    private final PdfWatermarkUseCase pdfWatermarkUseCase;

    @Inject
    public PdfWatermarkResource(PdfWatermarkUseCase pdfWatermarkUseCase) {
        this.pdfWatermarkUseCase = pdfWatermarkUseCase;
    }

    @POST
    @Path("/watermark")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces("application/pdf")
    public Response watermark(
            @RestForm("file") FileUpload file,
            @RestForm("text") String text,
            @RestForm("opacity") Float opacity,
            @RestForm("layout") WatermarkLayout layout) {

        if (file == null || file.size() == 0 || text == null || opacity == null || layout == null) {
            throw new BadRequestException("File, text, opacity, and layout are required");
        }

        var command = new WatermarkPdfCommand(
                file.uploadedFile(),
                text,
                opacity,
                layout
        );

        return Response.ok(pdfWatermarkUseCase.watermark(command))
                .header("Content-Disposition", "attachment; filename=watermarked.pdf")
                .build();
    }
}