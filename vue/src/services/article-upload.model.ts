import { Category } from '@/api/vulpress';

export default interface ArticleUpload {
  category: Category;
  title: string;
  description?: string;
  issueDate?: string;
  author?: string;
  content: File;
}
