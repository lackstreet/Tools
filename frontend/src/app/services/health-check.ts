import { inject, Service } from '@angular/core';
import { Observable } from 'rxjs';
import {HttpClient} from '@angular/common/http';

@Service()
export class HealthCheck {
  private readonly http = inject(HttpClient);

  check() :Observable<string> {
    return this.http.get('/api/healthcheck',{
      responseType: 'text'
      }
    );
  }
}
