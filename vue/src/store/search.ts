import { ArticlePreview } from '@/api/vulpress';
import { articleService } from '@/services';
import { defineStore } from 'pinia';
import { Ref, ref } from 'vue';
import { Router, useRouter } from 'vue-router';

export const useSearchStore = defineStore('search', () => {
  const router: Router = useRouter();

  const queryStr: Ref<string | undefined> = ref<string | undefined>();
  const searchResult: Ref<ArticlePreview[]> = ref<ArticlePreview[]>([]);

  async function search(q: string): Promise<void> {
    queryStr.value = q;
    searchResult.value = await articleService.findArticles(q);
    router.push({ name: 'search' });
  }
  return {
    // refs:
    queryStr,
    searchResult,
    // methods:
    search,
  };
});
