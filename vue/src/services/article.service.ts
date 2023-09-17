import { ApiError, ArticleApi, ArticleDetail, ArticlePreview, Category } from '@/api/giannitsa';
import ArticleUpload from './article-upload.model';

export default class ArticleService {
  constructor(private articleApi: ArticleApi) {}

  async createCategory(category: Category): Promise<Category | ApiError> {
    return await this.articleApi.createCategory(category).then(
      (success) => success.data,
      (reject) => reject.data as ApiError
    );
  }

  async categories(): Promise<Category[]> {
    return await this.articleApi.listCategories().then(
      (success) => success.data,
      (reject) => []
    );
  }

  async articles(category: string): Promise<ArticlePreview[] | ApiError> {
    return await this.articleApi.getRecentArticles(category).then(
      (ok) => ok.data,
      (reject) => reject.data as ApiError
    );
  }

  async uploadArticle(model: ArticleUpload): Promise<ArticleDetail | ApiError> {
    return await this.articleApi
      .uploadArticle(
        model.category.code,
        model.title,
        model.issueDate ?? this.getCurrentDateIsoFormatted(),
        model.author ?? '',
        model.description ?? '',
        model.content
      )
      .then(
        (ok) => ok.data,
        (reject) => reject.data as ApiError
      );
  }

  async loadArticle(category: string, article: string): Promise<ArticleDetail | ApiError> {
    return await this.articleApi.getArticle(article).then(
      (ok) => ok.data,
      (reject) => reject.data as ApiError
    );
  }

  private getCurrentDateIsoFormatted(): string {
    const date: string = new Date().toISOString();
    return date.substring(0, date.indexOf('T'));
  }

  async deleteCategory(category: string): Promise<boolean> {
    return await this.articleApi.deleteCategory(category).then(
      (ok) => true,
      (reject) => false
    );
  }
}
