import { ApiError, ArticleApi, ArticlePreview, Category } from '@/api/giannitsa';

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
}
