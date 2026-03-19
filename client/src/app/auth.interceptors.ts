import { Injectable } from '@angular/core';
import {
  HttpEvent, HttpHandler, HttpInterceptor, HttpRequest
} from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable()
export class AuthInterceptor implements HttpInterceptor {

  private getToken(): string | null {
    return localStorage.getItem('token'); // or from a proper AuthService
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    // Allow unauthenticated endpoints to pass through without token
    const url = req.url?.toLowerCase();
    if (url.includes('login') || url.includes('add-user')) {
      return next.handle(req);
    }

    const token = this.getToken();

    let headers = req.headers
      .set('Accept', 'application/json')
      .set('Content-Type', 'application/json');

    if (token) {
      headers = headers.set('Authorization', `Bearer ${token}`);
    }

    const authReq = req.clone({ headers });
    return next.handle(authReq);
  }
}