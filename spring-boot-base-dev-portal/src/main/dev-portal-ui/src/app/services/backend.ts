import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {ApiDetailsResponse, ApiListRequest, ApiListResponse} from "./models/ApiModels";
import {environment} from "../../environments/environment";

@Injectable()
export class Backend {

  // Dynamically construct base URL from current window location
  private static readonly BASE_URL = Backend.getBaseUrl();

  public static GET_API_LIST: string = Backend.BASE_URL + "/dev-portal/api/list";
  public static GET_API_DETAILS: string = Backend.BASE_URL + "/dev-portal/api/id";
  public static CLEAR_API_LIST: string = Backend.BASE_URL + "/dev-portal/api/clear";

  constructor(private http: HttpClient) {}

  /**
   * Determines the backend base URL based on environment.
   *
   * Logic:
   * 1. Check for ?backendUrl query parameter override
   * 2. If development environment: use http://localhost:8080
   * 3. If production environment: extract protocol, host, and context path from current URL
   *
   * Examples (production):
   * - http://localhost:8080/prompt-lib/dev-portal/ -> http://localhost:8080/prompt-lib
   * - https://example.com/my-app/dev-portal/ -> https://example.com/my-app
   * - http://localhost:8082/dev-portal/ -> http://localhost:8082
   *
   * @returns The backend base URL
   */
  private static getBaseUrl(): string {
    // 1. Check for query parameter override
    const urlParams = new URLSearchParams(window.location.search);
    const backendUrlOverride = urlParams.get('backendUrl');

    if (backendUrlOverride) {
      console.log('[DevPortal] Using backend URL from query parameter:', backendUrlOverride);
      return backendUrlOverride;
    }

    // 2. Use localhost:8080 for development environment
    if (!environment.production) {
      const devBaseUrl = 'http://localhost:8080/api';
      console.log('[DevPortal] Using development backend base URL:', devBaseUrl);
      return devBaseUrl;
    }

    // 3. For production: Extract components from current URL
    const protocol = window.location.protocol; // e.g., "http:" or "https:"
    const host = window.location.host;         // e.g., "localhost:8080" or "example.com"
    const pathname = window.location.pathname; // e.g., "/prompt-lib/dev-portal/index.html"

    // 4. Extract context path (everything before /dev-portal/)
    let contextPath = '';
    const devPortalIndex = pathname.indexOf('/dev-portal');

    if (devPortalIndex > 0) {
      // Extract the part before /dev-portal/
      contextPath = pathname.substring(0, devPortalIndex);
    }

    // 5. Build base URL
    const baseUrl = `${protocol}//${host}${contextPath}`;

    console.log('[DevPortal] Auto-detected backend base URL:', baseUrl);
    console.log('[DevPortal] - Protocol:', protocol);
    console.log('[DevPortal] - Host:', host);
    console.log('[DevPortal] - Context Path:', contextPath || '(none)');

    return baseUrl;
  }

  /**
   * Get the current backend base URL (useful for debugging or display)
   */
  public static getBackendBaseUrl(): string {
    return Backend.BASE_URL;
  }

  public getApiList(apiListRequest: ApiListRequest) {
    return this.http.post<ApiListResponse>(Backend.GET_API_LIST, apiListRequest).toPromise();
  }

  public getApiDetails(id: string) {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');
    return this.http.post<ApiDetailsResponse>(Backend.GET_API_DETAILS, `{"id": "${id}"}`, {headers: headers}).toPromise();
  }

  public clearApiList() {
    return this.http.post<{success: boolean, message: string}>(Backend.CLEAR_API_LIST, {}).toPromise();
  }
}
