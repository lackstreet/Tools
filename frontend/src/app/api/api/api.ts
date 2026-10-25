export * from './healthCheck.service';
import { HealthCheckService } from './healthCheck.service';
export * from './inspectPdfResource.service';
import { InspectPdfResourceService } from './inspectPdfResource.service';
export * from './mergePdfResource.service';
import { MergePdfResourceService } from './mergePdfResource.service';
export const APIS = [HealthCheckService, InspectPdfResourceService, MergePdfResourceService];
