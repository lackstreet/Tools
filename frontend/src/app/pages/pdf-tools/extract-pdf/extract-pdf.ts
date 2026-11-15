import { Component, computed, inject, OnDestroy, signal } from '@angular/core';
import { ExtractPdfResourceService } from '../../../api';
import { Subscription } from 'rxjs';
import { PdfPreview } from '../services/pdf-preview';
import { PdfThumbnail } from '../models/pdf-preview';

@Component({
  imports: [],
  selector: 'app-extract-pdf',
  styleUrl: './extract-pdf.css',
  templateUrl: './extract-pdf.html',
})
export class ExtractPdf implements OnDestroy {
  protected readonly selectedFile = signal<File | null>(null);
  protected readonly pagesText = signal('');
  private readonly pdfExtraction = inject(ExtractPdfResourceService);

  protected readonly extracting = signal(false);
  protected readonly extractionError = signal<string | null>(null);

  protected readonly pageCount = signal<number | null>(null);
  protected readonly previewError = signal<string | null>(null);

  protected readonly thumbnails = signal<PdfThumbnail[]>([]);
  protected readonly selectedPageCount = computed(() => new Set(this.parsePages() ?? []).size);
  private readonly pdfPreview = inject(PdfPreview);
  private previewSubscription?: Subscription;

  protected onFileSelected(event: Event): void {
    this.previewSubscription?.unsubscribe();

    const input = event.target as HTMLInputElement;
    const file = input.files?.item(0) ?? null;

    this.selectedFile.set(file);
    this.pageCount.set(null);
    this.previewError.set(null);
    this.thumbnails.set([]);
    this.pagesText.set('');
    this.extractionError.set(null);

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

  protected isPageSelected(pageNumber: number): boolean {
    return this.parsePages()?.includes(pageNumber) ?? false;
  }

  protected togglePage(pageNumber: number): void {
    const pages = new Set(this.parsePages() ?? []);

    if (pages.has(pageNumber)) {
      pages.delete(pageNumber);
    } else {
      pages.add(pageNumber);
    }

    this.pagesText.set([...pages].sort((a, b) => a - b).join(','));
    this.extractionError.set(null);
  }

  protected onPagesChanged(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.pagesText.set(input.value);
  }

  private parsePages(): number[] | null {
    const parts = this.pagesText()
      .split(',')
      .map((part) => part.trim());
    if (parts.some((part) => !/^[0-9]+$/.test(part))) {
      return null;
    }

    const pages = parts.map((part) => Number(part));

    if (pages.some((page) => !Number.isSafeInteger(page) || page < 1)) {
      return null;
    }

    return pages;
  }

  protected extractPdf(): void {
    const file = this.selectedFile();
    const pages = this.parsePages();

    this.extractionError.set(null);

    if (!file || !pages) {
      this.extractionError.set('Seleziona un PDF e indica pagine valide.');
      return;
    }

    const totalPages = this.pageCount();

    if (totalPages === null) {
      this.extractionError.set('Attendi la lettura del PDF.');
      return;
    }

    if (pages.some((page) => page > totalPages)) {
      this.extractionError.set(
        `Il documento contiene ${totalPages} pagine, inserisci o seleziona un valore uguale o inferiore a ${totalPages}`,
      );
      return;
    }

    this.extracting.set(true);

    this.pdfExtraction.apiPdfExtractPost(file, pages).subscribe({
      next: (pdf: Blob) => {
        const url = URL.createObjectURL(pdf);
        const link = document.createElement('a');
        link.href = url;
        link.download = 'extracted.pdf';
        link.click();
        setTimeout(() => URL.revokeObjectURL(url), 1000);

        this.extracting.set(false);
      },
      error: () => {
        this.extractionError.set('Impossibile estrarre le pagine');
        this.extracting.set(false);
      },
    });
  }

  protected selectAllPages(): void {
    const count = this.pageCount();

    if (!count) {
      return;
    }

    const pages = Array.from({ length: count }, (_, index) => index + 1);
    this.pagesText.set(pages.join(','));
    this.extractionError.set(null);
  }

  protected clearSelection(): void {
    this.pagesText.set('');
    this.extractionError.set(null);
  }

  ngOnDestroy(): void {
    this.previewSubscription?.unsubscribe();
  }
}
