import { Service } from '@angular/core';
import { Observable } from 'rxjs';
import { PdfPreviewState, PdfThumbnail } from '../models/pdf-preview';


import { GlobalWorkerOptions, getDocument } from 'pdfjs-dist';

GlobalWorkerOptions.workerSrc = '/assets/pdfjs/pdf.worker.mjs';

@Service()
export class PdfPreview {
  preview(file: File): Observable<PdfPreviewState> {
    return new Observable<PdfPreviewState>((subscriber) => {
      let task: ReturnType<typeof getDocument> | undefined;

      const load = async (): Promise<void> => {
        try {
          const data = new Uint8Array(await file.arrayBuffer());

          if (subscriber.closed) {
            return;
          }

          task = getDocument({ data });
          const pdf = await task.promise;

          if (subscriber.closed) {
            return;
          }

          subscriber.next({
            pageCount: pdf.numPages,
            thumbnails: [],
          });

          const thumbnails: PdfThumbnail[] = [];

          for (let pageNumber = 1; pageNumber <= pdf.numPages; pageNumber++) {
            if (subscriber.closed) {
              return;
            }

            const page = await pdf.getPage(pageNumber);

            if (subscriber.closed) {
              return;
            }

            const originalViewport = page.getViewport({ scale: 1 });
            const viewport = page.getViewport({
              scale: 220 / originalViewport.width,
            });

            const canvas = document.createElement('canvas');
            canvas.width = Math.ceil(viewport.width);
            canvas.height = Math.ceil(viewport.height);

            await page.render({ canvas, viewport }).promise;

            if (subscriber.closed) {
              return;
            }

            thumbnails.push({
              pageNumber,
              image: canvas.toDataURL('image/png'),
            });

            page.cleanup();

            subscriber.next({
              pageCount: pdf.numPages,
              thumbnails: [...thumbnails],
            });
          }

          subscriber.complete();
        } catch (error) {
          if (!subscriber.closed) {
            subscriber.error(error);
          }
        }
      };

      void load();

      return () => {
        if (task) {
          void task.destroy().catch((error) => {
            console.error('Errore nella pulizia di PDF.js', error);
          });
        }
      };
    });
  }
}
