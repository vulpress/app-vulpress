import { AuthApi, AuthenticationRequest } from "@/api/giannitsa";

export default class AuthService {
  constructor(private authApi: AuthApi) {}

  async isAuthenticated(): Promise<boolean> {
    const jwtToken = localStorage.getItem("jwtToken");
    if (!jwtToken) {
      return false;
    }

    return this.authApi
      .isAuthenticated()
      .then((s) => true)
      .catch((s) => false);
  }

  async login(credentials: AuthenticationRequest): Promise<boolean> {
    return await this.authApi
      .login(credentials)
      .then((success) => {
        localStorage.setItem("jwtToken", success.data.token ?? "");
        return true;
      })
      .catch((error) => false);
  }

  logout(): void {
    localStorage.removeItem("jwtToken");
  }
}
