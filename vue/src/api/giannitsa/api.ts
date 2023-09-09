/* tslint:disable */
/* eslint-disable */
/**
 * Giannitsa Web App BFF API
 * A simple API to allow server-client communications
 *
 * The version of the OpenAPI document: 1.0.0
 * Contact: papp.szabolcs.bazil@gmail.com
 *
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */

import type { Configuration } from './configuration';
import type { AxiosPromise, AxiosInstance, AxiosRequestConfig } from 'axios';
import globalAxios from 'axios';
// Some imports not used depending on template conditions
// @ts-ignore
import {
  DUMMY_BASE_URL,
  assertParamExists,
  setApiKeyToObject,
  setBasicAuthToObject,
  setBearerAuthToObject,
  setOAuthToObject,
  setSearchParams,
  serializeDataIfNeeded,
  toPathString,
  createRequestFunction,
} from './common';
import type { RequestArgs } from './base';
// @ts-ignore
import { BASE_PATH, COLLECTION_FORMATS, BaseAPI, RequiredError } from './base';

/**
 *
 * @export
 * @interface ArticleDetail
 */
export interface ArticleDetail {
  /**
   *
   * @type {number}
   * @memberof ArticleDetail
   */
  id: number;
  /**
   * URL-safe unique identifier.
   * @type {string}
   * @memberof ArticleDetail
   */
  code: string;
  /**
   *
   * @type {string}
   * @memberof ArticleDetail
   */
  title: string;
  /**
   *
   * @type {Array<Paragraph>}
   * @memberof ArticleDetail
   */
  paragraphs: Array<Paragraph>;
}
/**
 *
 * @export
 * @interface ArticlePreview
 */
export interface ArticlePreview {
  /**
   * URL-safe unique identifier.
   * @type {string}
   * @memberof ArticlePreview
   */
  code?: string;
  /**
   *
   * @type {string}
   * @memberof ArticlePreview
   */
  title: string;
  /**
   *
   * @type {string}
   * @memberof ArticlePreview
   */
  description: string;
  /**
   * The unique identifier of the image to be as thumbnail for the article.
   * @type {string}
   * @memberof ArticlePreview
   */
  thumbnail?: string;
}
/**
 *
 * @export
 * @interface AuthenticationRequest
 */
export interface AuthenticationRequest {
  /**
   *
   * @type {string}
   * @memberof AuthenticationRequest
   */
  username: string;
  /**
   *
   * @type {string}
   * @memberof AuthenticationRequest
   */
  password: string;
}
/**
 *
 * @export
 * @interface AuthenticationResponse
 */
export interface AuthenticationResponse {
  /**
   *
   * @type {string}
   * @memberof AuthenticationResponse
   */
  token?: string;
}
/**
 *
 * @export
 * @interface Category
 */
export interface Category {
  /**
   * URL-safe unique identifier.
   * @type {string}
   * @memberof Category
   */
  code: string;
  /**
   *
   * @type {string}
   * @memberof Category
   */
  title?: string;
  /**
   *
   * @type {string}
   * @memberof Category
   */
  description?: string;
}
/**
 *
 * @export
 * @interface Paragraph
 */
export interface Paragraph {
  /**
   *
   * @type {string}
   * @memberof Paragraph
   */
  title?: string;
  /**
   *
   * @type {string}
   * @memberof Paragraph
   */
  text?: string;
  /**
   *
   * @type {string}
   * @memberof Paragraph
   */
  image?: string;
}

/**
 * ArticleApi - axios parameter creator
 * @export
 */
export const ArticleApiAxiosParamCreator = function (configuration?: Configuration) {
  return {
    /**
     * Creates a new, empty category. The category\'s URL-safe unique code is generated server side, and must be unique.
     * @summary Create a new category
     * @param {string} [body]
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    createCategory: async (
      body?: string,
      options: AxiosRequestConfig = {}
    ): Promise<RequestArgs> => {
      const localVarPath = `/categories`;
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }

      const localVarRequestOptions = { method: 'POST', ...baseOptions, ...options };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      localVarHeaderParameter['Content-Type'] = 'application/json';

      setSearchParams(localVarUrlObj, localVarQueryParameter);
      let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };
      localVarRequestOptions.data = serializeDataIfNeeded(
        body,
        localVarRequestOptions,
        configuration
      );

      return {
        url: toPathString(localVarUrlObj),
        options: localVarRequestOptions,
      };
    },
    /**
     * Deletes a category, moving its contents to the archived category. The archived category may not be deleted.
     * @summary Delete a category
     * @param {string} category
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    deleteCategory: async (
      category: string,
      options: AxiosRequestConfig = {}
    ): Promise<RequestArgs> => {
      // verify required parameter 'category' is not null or undefined
      assertParamExists('deleteCategory', 'category', category);
      const localVarPath = `/categories/{category}`.replace(
        `{${'category'}}`,
        encodeURIComponent(String(category))
      );
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }

      const localVarRequestOptions = { method: 'DELETE', ...baseOptions, ...options };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      setSearchParams(localVarUrlObj, localVarQueryParameter);
      let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };

      return {
        url: toPathString(localVarUrlObj),
        options: localVarRequestOptions,
      };
    },
    /**
     * ...
     * @summary Load the contents of an article
     * @param {string} article
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    getArticle: async (article: string, options: AxiosRequestConfig = {}): Promise<RequestArgs> => {
      // verify required parameter 'article' is not null or undefined
      assertParamExists('getArticle', 'article', article);
      const localVarPath = `/articles/{article}`.replace(
        `{${'article'}}`,
        encodeURIComponent(String(article))
      );
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }

      const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      setSearchParams(localVarUrlObj, localVarQueryParameter);
      let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };

      return {
        url: toPathString(localVarUrlObj),
        options: localVarRequestOptions,
      };
    },
    /**
     * ...
     * @summary Return the Nth page of a categories articles
     * @param {string} category
     * @param {number} page
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    getArticles: async (
      category: string,
      page: number,
      options: AxiosRequestConfig = {}
    ): Promise<RequestArgs> => {
      // verify required parameter 'category' is not null or undefined
      assertParamExists('getArticles', 'category', category);
      // verify required parameter 'page' is not null or undefined
      assertParamExists('getArticles', 'page', page);
      const localVarPath = `/categories/{category}/{page}`
        .replace(`{${'category'}}`, encodeURIComponent(String(category)))
        .replace(`{${'page'}}`, encodeURIComponent(String(page)));
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }

      const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      setSearchParams(localVarUrlObj, localVarQueryParameter);
      let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };

      return {
        url: toPathString(localVarUrlObj),
        options: localVarRequestOptions,
      };
    },
    /**
     * ...
     * @summary Return the first page of articles in a category
     * @param {string} category
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    getRecentArticles: async (
      category: string,
      options: AxiosRequestConfig = {}
    ): Promise<RequestArgs> => {
      // verify required parameter 'category' is not null or undefined
      assertParamExists('getRecentArticles', 'category', category);
      const localVarPath = `/categories/{category}`.replace(
        `{${'category'}}`,
        encodeURIComponent(String(category))
      );
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }

      const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      setSearchParams(localVarUrlObj, localVarQueryParameter);
      let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };

      return {
        url: toPathString(localVarUrlObj),
        options: localVarRequestOptions,
      };
    },
    /**
     * Returns a list of categories available with the provided authorisation (regular and anonymous users may only receive a subset of categories available to administrators).
     * @summary List all categories
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    listCategories: async (options: AxiosRequestConfig = {}): Promise<RequestArgs> => {
      const localVarPath = `/categories`;
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }

      const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      setSearchParams(localVarUrlObj, localVarQueryParameter);
      let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };

      return {
        url: toPathString(localVarUrlObj),
        options: localVarRequestOptions,
      };
    },
    /**
     * ...
     * @summary Creates a new article in this category
     * @param {string} category
     * @param {string} title The desired title of the article
     * @param {string} issued The date for which this article is issued, may be null.
     * @param {string} author The name of the article\\\&#39;s author. If null, the uploader is considered the author.
     * @param {string} description Succinct description for the article.
     * @param {File} documentFile
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    uploadArticle: async (
      category: string,
      title: string,
      issued: string,
      author: string,
      description: string,
      documentFile: File,
      options: AxiosRequestConfig = {}
    ): Promise<RequestArgs> => {
      // verify required parameter 'category' is not null or undefined
      assertParamExists('uploadArticle', 'category', category);
      // verify required parameter 'title' is not null or undefined
      assertParamExists('uploadArticle', 'title', title);
      // verify required parameter 'issued' is not null or undefined
      assertParamExists('uploadArticle', 'issued', issued);
      // verify required parameter 'author' is not null or undefined
      assertParamExists('uploadArticle', 'author', author);
      // verify required parameter 'description' is not null or undefined
      assertParamExists('uploadArticle', 'description', description);
      // verify required parameter 'documentFile' is not null or undefined
      assertParamExists('uploadArticle', 'documentFile', documentFile);
      const localVarPath = `/categories/{category}`.replace(
        `{${'category'}}`,
        encodeURIComponent(String(category))
      );
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }

      const localVarRequestOptions = { method: 'POST', ...baseOptions, ...options };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;
      const localVarFormParams = new ((configuration && configuration.formDataCtor) || FormData)();

      if (title !== undefined) {
        localVarFormParams.append('title', title as any);
      }

      if (issued !== undefined) {
        localVarFormParams.append('issued', issued as any);
      }

      if (author !== undefined) {
        localVarFormParams.append('author', author as any);
      }

      if (description !== undefined) {
        localVarFormParams.append('description', description as any);
      }

      if (documentFile !== undefined) {
        localVarFormParams.append('documentFile', documentFile as any);
      }

      localVarHeaderParameter['Content-Type'] = 'multipart/form-data';

      setSearchParams(localVarUrlObj, localVarQueryParameter);
      let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };
      localVarRequestOptions.data = localVarFormParams;

      return {
        url: toPathString(localVarUrlObj),
        options: localVarRequestOptions,
      };
    },
  };
};

/**
 * ArticleApi - functional programming interface
 * @export
 */
export const ArticleApiFp = function (configuration?: Configuration) {
  const localVarAxiosParamCreator = ArticleApiAxiosParamCreator(configuration);
  return {
    /**
     * Creates a new, empty category. The category\'s URL-safe unique code is generated server side, and must be unique.
     * @summary Create a new category
     * @param {string} [body]
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async createCategory(
      body?: string,
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<Category>> {
      const localVarAxiosArgs = await localVarAxiosParamCreator.createCategory(body, options);
      return createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration);
    },
    /**
     * Deletes a category, moving its contents to the archived category. The archived category may not be deleted.
     * @summary Delete a category
     * @param {string} category
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async deleteCategory(
      category: string,
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
      const localVarAxiosArgs = await localVarAxiosParamCreator.deleteCategory(category, options);
      return createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration);
    },
    /**
     * ...
     * @summary Load the contents of an article
     * @param {string} article
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async getArticle(
      article: string,
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<ArticleDetail>> {
      const localVarAxiosArgs = await localVarAxiosParamCreator.getArticle(article, options);
      return createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration);
    },
    /**
     * ...
     * @summary Return the Nth page of a categories articles
     * @param {string} category
     * @param {number} page
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async getArticles(
      category: string,
      page: number,
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<Array<ArticlePreview>>> {
      const localVarAxiosArgs = await localVarAxiosParamCreator.getArticles(
        category,
        page,
        options
      );
      return createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration);
    },
    /**
     * ...
     * @summary Return the first page of articles in a category
     * @param {string} category
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async getRecentArticles(
      category: string,
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<Array<ArticlePreview>>> {
      const localVarAxiosArgs = await localVarAxiosParamCreator.getRecentArticles(
        category,
        options
      );
      return createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration);
    },
    /**
     * Returns a list of categories available with the provided authorisation (regular and anonymous users may only receive a subset of categories available to administrators).
     * @summary List all categories
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async listCategories(
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<Array<Category>>> {
      const localVarAxiosArgs = await localVarAxiosParamCreator.listCategories(options);
      return createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration);
    },
    /**
     * ...
     * @summary Creates a new article in this category
     * @param {string} category
     * @param {string} title The desired title of the article
     * @param {string} issued The date for which this article is issued, may be null.
     * @param {string} author The name of the article\\\&#39;s author. If null, the uploader is considered the author.
     * @param {string} description Succinct description for the article.
     * @param {File} documentFile
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async uploadArticle(
      category: string,
      title: string,
      issued: string,
      author: string,
      description: string,
      documentFile: File,
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
      const localVarAxiosArgs = await localVarAxiosParamCreator.uploadArticle(
        category,
        title,
        issued,
        author,
        description,
        documentFile,
        options
      );
      return createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration);
    },
  };
};

/**
 * ArticleApi - factory interface
 * @export
 */
export const ArticleApiFactory = function (
  configuration?: Configuration,
  basePath?: string,
  axios?: AxiosInstance
) {
  const localVarFp = ArticleApiFp(configuration);
  return {
    /**
     * Creates a new, empty category. The category\'s URL-safe unique code is generated server side, and must be unique.
     * @summary Create a new category
     * @param {string} [body]
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    createCategory(body?: string, options?: any): AxiosPromise<Category> {
      return localVarFp.createCategory(body, options).then((request) => request(axios, basePath));
    },
    /**
     * Deletes a category, moving its contents to the archived category. The archived category may not be deleted.
     * @summary Delete a category
     * @param {string} category
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    deleteCategory(category: string, options?: any): AxiosPromise<void> {
      return localVarFp
        .deleteCategory(category, options)
        .then((request) => request(axios, basePath));
    },
    /**
     * ...
     * @summary Load the contents of an article
     * @param {string} article
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    getArticle(article: string, options?: any): AxiosPromise<ArticleDetail> {
      return localVarFp.getArticle(article, options).then((request) => request(axios, basePath));
    },
    /**
     * ...
     * @summary Return the Nth page of a categories articles
     * @param {string} category
     * @param {number} page
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    getArticles(
      category: string,
      page: number,
      options?: any
    ): AxiosPromise<Array<ArticlePreview>> {
      return localVarFp
        .getArticles(category, page, options)
        .then((request) => request(axios, basePath));
    },
    /**
     * ...
     * @summary Return the first page of articles in a category
     * @param {string} category
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    getRecentArticles(category: string, options?: any): AxiosPromise<Array<ArticlePreview>> {
      return localVarFp
        .getRecentArticles(category, options)
        .then((request) => request(axios, basePath));
    },
    /**
     * Returns a list of categories available with the provided authorisation (regular and anonymous users may only receive a subset of categories available to administrators).
     * @summary List all categories
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    listCategories(options?: any): AxiosPromise<Array<Category>> {
      return localVarFp.listCategories(options).then((request) => request(axios, basePath));
    },
    /**
     * ...
     * @summary Creates a new article in this category
     * @param {string} category
     * @param {string} title The desired title of the article
     * @param {string} issued The date for which this article is issued, may be null.
     * @param {string} author The name of the article\\\&#39;s author. If null, the uploader is considered the author.
     * @param {string} description Succinct description for the article.
     * @param {File} documentFile
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    uploadArticle(
      category: string,
      title: string,
      issued: string,
      author: string,
      description: string,
      documentFile: File,
      options?: any
    ): AxiosPromise<void> {
      return localVarFp
        .uploadArticle(category, title, issued, author, description, documentFile, options)
        .then((request) => request(axios, basePath));
    },
  };
};

/**
 * ArticleApi - object-oriented interface
 * @export
 * @class ArticleApi
 * @extends {BaseAPI}
 */
export class ArticleApi extends BaseAPI {
  /**
   * Creates a new, empty category. The category\'s URL-safe unique code is generated server side, and must be unique.
   * @summary Create a new category
   * @param {string} [body]
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof ArticleApi
   */
  public createCategory(body?: string, options?: AxiosRequestConfig) {
    return ArticleApiFp(this.configuration)
      .createCategory(body, options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   * Deletes a category, moving its contents to the archived category. The archived category may not be deleted.
   * @summary Delete a category
   * @param {string} category
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof ArticleApi
   */
  public deleteCategory(category: string, options?: AxiosRequestConfig) {
    return ArticleApiFp(this.configuration)
      .deleteCategory(category, options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   * ...
   * @summary Load the contents of an article
   * @param {string} article
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof ArticleApi
   */
  public getArticle(article: string, options?: AxiosRequestConfig) {
    return ArticleApiFp(this.configuration)
      .getArticle(article, options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   * ...
   * @summary Return the Nth page of a categories articles
   * @param {string} category
   * @param {number} page
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof ArticleApi
   */
  public getArticles(category: string, page: number, options?: AxiosRequestConfig) {
    return ArticleApiFp(this.configuration)
      .getArticles(category, page, options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   * ...
   * @summary Return the first page of articles in a category
   * @param {string} category
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof ArticleApi
   */
  public getRecentArticles(category: string, options?: AxiosRequestConfig) {
    return ArticleApiFp(this.configuration)
      .getRecentArticles(category, options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   * Returns a list of categories available with the provided authorisation (regular and anonymous users may only receive a subset of categories available to administrators).
   * @summary List all categories
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof ArticleApi
   */
  public listCategories(options?: AxiosRequestConfig) {
    return ArticleApiFp(this.configuration)
      .listCategories(options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   * ...
   * @summary Creates a new article in this category
   * @param {string} category
   * @param {string} title The desired title of the article
   * @param {string} issued The date for which this article is issued, may be null.
   * @param {string} author The name of the article\\\&#39;s author. If null, the uploader is considered the author.
   * @param {string} description Succinct description for the article.
   * @param {File} documentFile
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof ArticleApi
   */
  public uploadArticle(
    category: string,
    title: string,
    issued: string,
    author: string,
    description: string,
    documentFile: File,
    options?: AxiosRequestConfig
  ) {
    return ArticleApiFp(this.configuration)
      .uploadArticle(category, title, issued, author, description, documentFile, options)
      .then((request) => request(this.axios, this.basePath));
  }
}

/**
 * AuthApi - axios parameter creator
 * @export
 */
export const AuthApiAxiosParamCreator = function (configuration?: Configuration) {
  return {
    /**
     * Pings the server with the authentication token included as a bearer token to check for its validity.
     * @summary Checks for authentication.
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    isAuthenticated: async (options: AxiosRequestConfig = {}): Promise<RequestArgs> => {
      const localVarPath = `/auth`;
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }

      const localVarRequestOptions = { method: 'GET', ...baseOptions, ...options };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      setSearchParams(localVarUrlObj, localVarQueryParameter);
      let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };

      return {
        url: toPathString(localVarUrlObj),
        options: localVarRequestOptions,
      };
    },
    /**
     * Submits an username-password authentication request for login purposes.
     * @summary Attempts authentication.
     * @param {AuthenticationRequest} authenticationRequest
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    login: async (
      authenticationRequest: AuthenticationRequest,
      options: AxiosRequestConfig = {}
    ): Promise<RequestArgs> => {
      // verify required parameter 'authenticationRequest' is not null or undefined
      assertParamExists('login', 'authenticationRequest', authenticationRequest);
      const localVarPath = `/auth/login`;
      // use dummy base URL string because the URL constructor only accepts absolute URLs.
      const localVarUrlObj = new URL(localVarPath, DUMMY_BASE_URL);
      let baseOptions;
      if (configuration) {
        baseOptions = configuration.baseOptions;
      }

      const localVarRequestOptions = { method: 'POST', ...baseOptions, ...options };
      const localVarHeaderParameter = {} as any;
      const localVarQueryParameter = {} as any;

      localVarHeaderParameter['Content-Type'] = 'application/json';

      setSearchParams(localVarUrlObj, localVarQueryParameter);
      let headersFromBaseOptions = baseOptions && baseOptions.headers ? baseOptions.headers : {};
      localVarRequestOptions.headers = {
        ...localVarHeaderParameter,
        ...headersFromBaseOptions,
        ...options.headers,
      };
      localVarRequestOptions.data = serializeDataIfNeeded(
        authenticationRequest,
        localVarRequestOptions,
        configuration
      );

      return {
        url: toPathString(localVarUrlObj),
        options: localVarRequestOptions,
      };
    },
  };
};

/**
 * AuthApi - functional programming interface
 * @export
 */
export const AuthApiFp = function (configuration?: Configuration) {
  const localVarAxiosParamCreator = AuthApiAxiosParamCreator(configuration);
  return {
    /**
     * Pings the server with the authentication token included as a bearer token to check for its validity.
     * @summary Checks for authentication.
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async isAuthenticated(
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<void>> {
      const localVarAxiosArgs = await localVarAxiosParamCreator.isAuthenticated(options);
      return createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration);
    },
    /**
     * Submits an username-password authentication request for login purposes.
     * @summary Attempts authentication.
     * @param {AuthenticationRequest} authenticationRequest
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    async login(
      authenticationRequest: AuthenticationRequest,
      options?: AxiosRequestConfig
    ): Promise<(axios?: AxiosInstance, basePath?: string) => AxiosPromise<AuthenticationResponse>> {
      const localVarAxiosArgs = await localVarAxiosParamCreator.login(
        authenticationRequest,
        options
      );
      return createRequestFunction(localVarAxiosArgs, globalAxios, BASE_PATH, configuration);
    },
  };
};

/**
 * AuthApi - factory interface
 * @export
 */
export const AuthApiFactory = function (
  configuration?: Configuration,
  basePath?: string,
  axios?: AxiosInstance
) {
  const localVarFp = AuthApiFp(configuration);
  return {
    /**
     * Pings the server with the authentication token included as a bearer token to check for its validity.
     * @summary Checks for authentication.
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    isAuthenticated(options?: any): AxiosPromise<void> {
      return localVarFp.isAuthenticated(options).then((request) => request(axios, basePath));
    },
    /**
     * Submits an username-password authentication request for login purposes.
     * @summary Attempts authentication.
     * @param {AuthenticationRequest} authenticationRequest
     * @param {*} [options] Override http request option.
     * @throws {RequiredError}
     */
    login(
      authenticationRequest: AuthenticationRequest,
      options?: any
    ): AxiosPromise<AuthenticationResponse> {
      return localVarFp
        .login(authenticationRequest, options)
        .then((request) => request(axios, basePath));
    },
  };
};

/**
 * AuthApi - object-oriented interface
 * @export
 * @class AuthApi
 * @extends {BaseAPI}
 */
export class AuthApi extends BaseAPI {
  /**
   * Pings the server with the authentication token included as a bearer token to check for its validity.
   * @summary Checks for authentication.
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof AuthApi
   */
  public isAuthenticated(options?: AxiosRequestConfig) {
    return AuthApiFp(this.configuration)
      .isAuthenticated(options)
      .then((request) => request(this.axios, this.basePath));
  }

  /**
   * Submits an username-password authentication request for login purposes.
   * @summary Attempts authentication.
   * @param {AuthenticationRequest} authenticationRequest
   * @param {*} [options] Override http request option.
   * @throws {RequiredError}
   * @memberof AuthApi
   */
  public login(authenticationRequest: AuthenticationRequest, options?: AxiosRequestConfig) {
    return AuthApiFp(this.configuration)
      .login(authenticationRequest, options)
      .then((request) => request(this.axios, this.basePath));
  }
}
