import { Category } from '@/api/giannitsa';

export default interface ArticleUpload {
  category: Category;
  title: string;
  description?: string;
  issueDate?: string;
  author?: string;
  content: File;
}
