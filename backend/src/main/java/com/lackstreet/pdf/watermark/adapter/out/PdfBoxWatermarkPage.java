package com.lackstreet.pdf.watermark.adapter.out;

import com.lackstreet.pdf.watermark.application.model.WatermarkLayout;
import com.lackstreet.pdf.watermark.application.port.out.PdfWatermarkPage;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;
import org.apache.pdfbox.util.Matrix;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Path;

public class PdfBoxWatermarkPage implements PdfWatermarkPage {

    private void drawText(
            PDPageContentStream stream,
            PDFont font,
            String text,
            float fontSize,
            Matrix placement) throws IOException {

        float textWidth = font.getStringWidth(text) / 1000f * fontSize;
        float textCenterY = (
                font.getFontDescriptor().getAscent()
                        + font.getFontDescriptor().getDescent()
        ) / 2000f * fontSize;

        stream.beginText();
        stream.setFont(font, fontSize);
        stream.setNonStrokingColor(0.6f);
        stream.setTextMatrix(placement);
        stream.newLineAtOffset(-textWidth / 2, -textCenterY);
        stream.showText(text);
        stream.endText();
    }

    private void drawRepeated(
            PDPageContentStream stream,
            PDFont font,
            String text,
            float fontSize,
            PDPage page) throws IOException {

        var box = page.getCropBox();
        double angle = Math.toRadians(page.getRotation() + 45);

        float width = font.getStringWidth(text) / 1000f * fontSize;
        float height = (
                font.getFontDescriptor().getAscent()
                        - font.getFontDescriptor().getDescent()
        ) / 1000f * fontSize;

        float spacing = Math.max(
                120f,
                (float) Math.hypot(width, height) + 40f
        );

        int row = 0;

        for (float y = box.getLowerLeftY();
             y <= box.getUpperRightY() + spacing;
             y += spacing, row++) {

            float offset = row % 2 == 0 ? 0 : spacing / 2;

            for (float x = box.getLowerLeftX() - spacing + offset;
                 x <= box.getUpperRightX() + spacing;
                 x += spacing) {

                drawText(
                        stream,
                        font,
                        text,
                        fontSize,
                        Matrix.getRotateInstance(angle, x, y)
                );
            }
        }
    }

    @Override
    public byte[] watermark(Path document, String text, float opacity, WatermarkLayout layout) {
        try (PDDocument pdf = Loader.loadPDF(document.toFile());
             ByteArrayOutputStream output = new ByteArrayOutputStream()) {

            var font = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
            float unitWidth = font.getStringWidth(text) / 1000f;
            float unitHeight = (
                    font.getFontDescriptor().getAscent()
                            - font.getFontDescriptor().getDescent()
            ) / 1000f;

            var graphicsState = new PDExtendedGraphicsState();
            graphicsState.setNonStrokingAlphaConstant(opacity);

            for (var page : pdf.getPages()) {

                var box = page.getCropBox();

                float availableSize = Math.min(box.getWidth(), box.getHeight()) * 0.8f;

                float fontSize = Math.min(
                        48f,
                        availableSize / (float) Math.hypot(unitWidth, unitHeight)
                );


                float centerX = box.getLowerLeftX() + box.getWidth() / 2;
                float centerY = box.getLowerLeftY() + box.getHeight() / 2;

                double angle = Math.toRadians(
                        page.getRotation()
                                + (layout == WatermarkLayout.DIAGONAL ? 45 : 0)
                );

                try (var stream = new PDPageContentStream(
                        pdf,
                        page,
                        PDPageContentStream.AppendMode.APPEND,
                        true,
                        true)) {

                    stream.saveGraphicsState();
                    stream.setGraphicsStateParameters(graphicsState);

                    switch (layout) {
                        case CENTER, DIAGONAL -> drawText(
                                stream,
                                font,
                                text,
                                fontSize,
                                Matrix.getRotateInstance(angle, centerX, centerY)
                        );

                        case REPEATED_DIAGONAL -> drawRepeated(
                                stream, font, text, Math.min(20f, fontSize), page
                        );
                    }

                    stream.restoreGraphicsState();

                }
            }

            pdf.save(output);
            return output.toByteArray();

        } catch (IOException e) {
            throw new UncheckedIOException(
                    "Impossible to apply watermark", e
            );
        }
    }

}

