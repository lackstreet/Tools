import { Routes } from '@angular/router';
import { Home } from './pages/home/home';
import { MergePdf } from './pages/merge-pdf/merge-pdf';
import { InspectPdf } from './pages/inspect-pdf/inspect-pdf';
import { PdfTools } from './pages/pdf-tools/pdf-tools';

export const routes: Routes = [
  { path: '', component: Home },
  { path: 'pdf', component: PdfTools },
  { path: 'pdf/merge', component: MergePdf },
  { path: 'pdf/inspect', component: InspectPdf },
];
