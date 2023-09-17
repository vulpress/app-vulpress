import { AppBarModel, UiAction, ViewApi } from '@/api/giannitsa';

export default class ViewService {
  constructor(private viewApi: ViewApi) {}

  async appBarModel(): Promise<AppBarModel> {
    return (await this.viewApi.getAppBar()).data;
  }

  async actions(viewName: string, pageName?: string): Promise<UiAction[]> {
    return (await this.viewApi.getActions(viewName, pageName)).data;
  }
}
