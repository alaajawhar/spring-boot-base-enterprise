import {Component, NgModule, OnInit, ViewChild} from '@angular/core';
import {Backend} from "../../../services/backend";
import {ApiDetailsResponse} from "../../../services/models/ApiModels";
import {ActivatedRoute} from "@angular/router";

@Component({
  selector: 'app-api-details',
  templateUrl: './api-details.component.html',
  styleUrls: ['./api-details.component.css']
})
export class ApiDetailsComponent implements OnInit {

  apiDetails?: ApiDetailsResponse
  curlButtonText: string = "Copy Curl"
  isFetching: boolean = true

  constructor(private route: ActivatedRoute, private backend: Backend) { }

  async ngOnInit(): Promise<void> {
    this.isFetching = true;
    this.apiDetails = await this.backend.getApiDetails(this.route.snapshot.paramMap.get("id")!);
    this.isFetching = false;
  }

  onCopyCurl() {
    this.copyMessage(this.apiDetails?.curl!)
    this.curlButtonText = "Copied"

    setTimeout(() => {
      this.curlButtonText = "Copy Curl"
    }, 600);
  }

  copyMessage(val: string){
    const selBox = document.createElement('textarea');
    selBox.style.position = 'fixed';
    selBox.style.left = '0';
    selBox.style.top = '0';
    selBox.style.opacity = '0';
    selBox.value = val;
    document.body.appendChild(selBox);
    selBox.focus();
    selBox.select();
    document.execCommand('copy');
    document.body.removeChild(selBox);
  }

  stringify(json: string) {
    if (typeof json === 'string') {
      return JSON.parse(json)
    }
    return json
  }

  auto_grow(element: any) {
    // Reset the height to auto to ensure it resizes properly
    element.style.height = 'auto';

    // Set the height to the scrollHeight if it's greater than the current height
    if (element.scrollHeight > element.clientHeight) {
      element.style.height = element.scrollHeight + "px";
    }
  }
}
