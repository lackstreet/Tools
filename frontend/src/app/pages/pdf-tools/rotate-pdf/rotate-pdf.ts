import { Component, inject, OnDestroy, signal } from '@angular/core';
import { Subscription } from 'rxjs';
import { PdfPreview } from '../services/pdf-preview';
import { PdfThumbnail } from '../models/pdf-preview';
import { RotatePdfResourceService } from '../../../api';

@Component({
  imports: [],
  selector: 'app-rotate-pdf',
  styleUrl: './rotate-pdf.css',
  templateUrl: './rotate-pdf.html',
})
export class RotatePdf implements OnDestroy {
  private readonly pdfPreview = inject(PdfPreview);
  private previewSubscription?: Subscription;

  protected readonly selectedFile = signal<File | null>(null);
  protected readonly pageCount = signal<number | null>(null);
  protected readonly previewError = signal<string | null>(null);
  protected readonly thumbnails = signal<PdfThumbnail[]>([]);
  protected readonly selectedPages = signal<number[]>([]);
  protected readonly degrees = signal<90 | 180 | 270>(90);
  private readonly pdfRotate = inject(RotatePdfResourceService);

  protected readonly rotating = signal(false);
  protected readonly rotationError = signal<string | null>(null);

  protected onFileSelected(event: Event): void {
    this.previewSubscription?.unsubscribe();

    const input = event.target as HTMLInputElement;
    const file = input.files?.item(0) ?? null;

    this.selectedFile.set(file);
    this.pageCount.set(null);
    this.previewError.set(null);
    this.thumbnails.set([]);
    this.selectedPages.set([]);
    this.rotationError.set(null);

    if (!file) {
      return;
    }

    this.previewSubscription = this.pdfPreview.preview(file).subscribe({
      next: (state) => {
        this.pageCount.set(state.pageCount);
        this.thumbnails.set(state.thumbnails);
      },
      error: () => {
        this.previewError.set('Impossibile leggere il PDF.');
      },
    });
  }

  protected togglePage(pageNumber: number): void {
    this.selectedPages.update((pages) =>
      pages.includes(pageNumber)
        ? pages.filter((page) => page !== pageNumber)
        : [...pages, pageNumber],
    );
  }

  protected onDegreesChanged(event: Event): void {
    const select = event.target as HTMLSelectElement;
    const value = Number(select.value);

    if (value === 90 || value === 180 || value === 270) {
      this.degrees.set(value);
    }
  }

  protected rotatePdf(): void {
    const file = this.selectedFile();
    const pages = this.selectedPages();

    if (!file || pages.length === 0 || this.previewError() || this.rotating()) {
      return;
    }

    this.rotationError.set(null);
    this.rotating.set(true);

    this.pdfRotate.apiPdfRotatePost(file, pages, this.degrees()).subscribe({
      next: (pdf: Blob) => {
        const url = URL.createObjectURL(pdf);
        const link = document.createElement('a');

        link.href = url;
        link.download = 'rotated.pdf';
        link.click();

        setTimeout(() => URL.revokeObjectURL(url), 1000);
        this.rotating.set(false);
      },
      error: () => {
        this.rotationError.set('Impossibile ruotare le pagine.');
        this.rotating.set(false);
      },
    });
  }

  ngOnDestroy(): void {
    this.previewSubscription?.unsubscribe();
  }
}
