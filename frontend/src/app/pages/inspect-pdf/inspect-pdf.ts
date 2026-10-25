import { Component, inject, signal } from '@angular/core';
import { HealthCheck } from '../../services/health-check';
import { InspectPdfResourceService, PdfInfo } from '../../api';
import { RouterOutlet } from '@angular/router';

@Component({
  imports: [RouterOutlet],
  selector: 'app-inspect-pdf',
  styleUrl: './inspect-pdf.css',
  templateUrl: './inspect-pdf.html',
})
export class InspectPdf {
  protected readonly title = signal('PDF Tools');
  private readonly healthCheck = inject(HealthCheck);
  protected readonly loading = signal(false);

  private readonly pdfInspection = inject(InspectPdfResourceService);
  protected readonly pdfInfo = signal<PdfInfo | null>(null);

  protected readonly status = signal('Stato non verificato');
  protected readonly selectedFile = signal<File | null>(null);

  protected readonly inspecting = signal(false);
  protected readonly inspectionError = signal<string | null>(null);

  protected onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.selectedFile.set(input.files?.item(0) ?? null);
    this.pdfInfo.set(null);
    this.inspectionError.set(null);
  }

  protected checkBackend(): void {
    this.loading.set(true);
    this.status.set('Verifica in corso...');

    this.healthCheck.check().subscribe({
      next: (message) => {
        this.status.set(message);
        this.loading.set(false);
      },
      error: () => {
        this.status.set('Impossibile contattare il backend');
        this.loading.set(false);
      },
    });
  }

  protected inspectPdf(): void {
    const file = this.selectedFile();

    if (!file) {
      return;
    }

    this.pdfInfo.set(null);
    this.inspectionError.set(null);
    this.inspecting.set(true);

    this.pdfInspection.apiPdfInspectPost(file).subscribe({
      next: (info) => {
        this.pdfInfo.set(info);
        this.inspecting.set(false);
      },
      error: (error) => {
        console.error('Errore durante l’analisi del PDF', error);
        this.inspecting.set(false);
        this.inspectionError.set("Impossibile analizzare il PDF. Riprova");
      },
    });
  }
}
