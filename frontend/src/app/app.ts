import { Component, inject, signal } from '@angular/core';
import { HealthCheck } from './services/health-check';
import { RouterOutlet } from '@angular/router';

@Component({
  imports: [RouterOutlet],
  selector: 'app-root',
  styleUrl: './app.css',
  templateUrl: './app.html',
})
export class App {
  protected readonly title = signal('PDF Tools');
  private readonly healthCheck = inject(HealthCheck);
  protected readonly loading = signal(false);


  protected readonly status = signal('Stato non verificato');
  protected readonly selectedFile = signal<File | null>(null);

  protected onFileSelected(event: Event): void {
    const input = event.target as HTMLInputElement;
    this.selectedFile.set(input.files?.item(0) ?? null);
  }


  protected checkBackend(): void {
    this.loading.set(true);
    this.status.set('Verifica in corso...');

    this.healthCheck.check().subscribe({
      next: message => {
        this.status.set(message);
        this.loading.set(false);
      },
      error: () => {
        this.status.set('Impossibile contattare il backend');
        this.loading.set(false);
      }
    });
  }

}
