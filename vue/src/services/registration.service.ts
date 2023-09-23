import { AuthenticationRequest, RegistrationApi } from '@/api/giannitsa';

export default class RegistrationService {
  constructor(private registrationApi: RegistrationApi) {}

  async register(request: AuthenticationRequest): Promise<boolean> {
    return await this.registrationApi.registerAccount(request).then(
      (ok) => {
        return true;
      },
      (err) => {
        return false;
      }
    );
  }

  async verify(token: string): Promise<boolean> {
    try {
      return await this.registrationApi
        .verifyAccount(token)
        .then(
          (ok) => {
            return true;
          },
          (err) => {
            return false;
          }
        )
        .catch((err) => false);
    } catch (ERROR) {
      return false;
    }
  }
}
