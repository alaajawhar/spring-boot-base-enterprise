import {Component, OnInit} from '@angular/core';
import {Backend} from "../../services/backend";
import {ApiListFilter, ApiListRequest, ApiListResponse} from "../../services/models/ApiModels";
import {Router} from "@angular/router";

@Component({
  selector: 'app-api-list',
  templateUrl: './api-list.component.html',
  styleUrls: ['./api-list.component.css'],
})
export class ApiListComponent implements OnInit {

  apiListFilter: ApiListFilter = {
    endpoint: undefined!, httpMethod: undefined!, id: undefined!, responseCode: undefined!
  }
  apiListResponse?: ApiListResponse
  selectedPageNumber: number = 1;
  pageNumberArr: number[] = []
  isFetching: boolean = false;
  errorMessage: string | null = null;
  skeletonRows = Array(10); // Number of skeleton rows to show

  constructor(private backend: Backend, private router: Router) {
  }

  async ngOnInit(): Promise<void> {
    this.isFetching = true;
    this.errorMessage = null;
    try {
      let apiListRequest: ApiListRequest = {
        filter: undefined!, count: 10, offset: 0
      }
      this.apiListResponse = await this.backend.getApiList(apiListRequest);
      this.setPageNumberArr()
    } catch (error) {
      this.errorMessage = 'Failed to load API list. Please try again.';
      console.error('Error fetching API list:', error);
    } finally {
      this.isFetching = false;
    }
  }

  async onPageChange(pageNumber: number) {
    this.isFetching = true;
    this.errorMessage = null;
    try {
      this.selectedPageNumber = pageNumber
      let apiListRequest: ApiListRequest = {
        filter: undefined!, count: 10, offset: (this.selectedPageNumber - 1) * 10
      }
      this.apiListResponse = await this.backend.getApiList(apiListRequest);
    } catch (error) {
      this.errorMessage = 'Failed to load page. Please try again.';
      console.error('Error fetching page:', error);
    } finally {
      this.isFetching = false;
    }
  }

  async onPrevious() {
    this.isFetching = true;
    this.errorMessage = null;
    try {
      this.selectedPageNumber = this.selectedPageNumber - 1
      let apiListRequest: ApiListRequest = {
        filter: undefined!, count: 10, offset: (this.selectedPageNumber - 1) * 10
      }
      this.apiListResponse = await this.backend.getApiList(apiListRequest);
    } catch (error) {
      this.errorMessage = 'Failed to load previous page. Please try again.';
      console.error('Error fetching previous page:', error);
    } finally {
      this.isFetching = false;
    }
  }

  async onNext() {
    this.isFetching = true;
    this.errorMessage = null;
    try {
      this.selectedPageNumber = this.selectedPageNumber + 1
      let apiListRequest: ApiListRequest = {
        filter: undefined!, count: 10, offset: (this.selectedPageNumber - 1) * 10
      }
      this.apiListResponse = await this.backend.getApiList(apiListRequest);
    } catch (error) {
      this.errorMessage = 'Failed to load next page. Please try again.';
      console.error('Error fetching next page:', error);
    } finally {
      this.isFetching = false;
    }
  }

  async onFilter() {
    this.isFetching = true;
    this.errorMessage = null;
    try {
      let apiListRequest: ApiListRequest = {
        filter: this.apiListFilter,
        count: 10, offset: 0
      }
      this.apiListResponse = await this.backend.getApiList(apiListRequest);
      this.setPageNumberArr()
    } catch (error) {
      this.errorMessage = 'Failed to filter API list. Please try again.';
      console.error('Error filtering API list:', error);
    } finally {
      this.isFetching = false;
    }
  }

  setPageNumberArr() {
    this.pageNumberArr = []
    for (let i = 1; i <= this.apiListResponse?.totalCount! / 10; i++) {
      this.pageNumberArr.push(i)
    }

    if (this.apiListResponse?.totalCount! % 10 > 0) {
      this.pageNumberArr.push(this.pageNumberArr.length + 1)
    }
  }

  async onClear() {
    this.isFetching = true;
    this.errorMessage = null;
    try {
      await this.backend.clearApiList();
      // Reload the list after clearing
      let apiListRequest: ApiListRequest = {
        filter: undefined!, count: 10, offset: 0
      }
      this.apiListResponse = await this.backend.getApiList(apiListRequest);
      this.selectedPageNumber = 1;
      this.setPageNumberArr();
    } catch (error) {
      this.errorMessage = 'Failed to clear API list. Please try again.';
      console.error('Error clearing API list:', error);
    } finally {
      this.isFetching = false;
    }
  }

}
