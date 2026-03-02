export interface ApiListRequest {
  offset: number;
  count: number;
  filter: ApiListFilter
}

export interface ApiListFilter {
  id: string;
  httpMethod: string;
  endpoint: string;
  responseCode: string;
}

export interface ApiListResponse {
  totalCount: number;
  list: ApiListItem[];
}

export interface ApiListItem {
  id: string;
  httpMethod: string;
  endpoint: string;
  responseStatusCode: string;
  date: string;
}

export interface ApiDetailsResponse {
  id: string;
  httpMethod: string;
  endpoint: string;
  fullPath: string;
  curl: string;
  requestDate: string;
  requestBody: string;
  responseDate: string;
  responseStatusCode: string;
  exceptionStackTrace: string;
  jdbcApiModelList: JdbcApiModel[];
  logs: string;
  responseBody: string;
  thirdPartyApiModelList: ApiDetailsResponse[]
}

export interface JdbcApiModel {
  packageName: string;
  jdbcTemplateFunctionName: string;
  jdbcTemplateArgs: string;
  jdbcTemplateResult: string;
}
