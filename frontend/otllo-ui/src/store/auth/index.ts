import { defineStore } from "pinia";
import { UserDetails } from "../../types/UserDetails.interface";
import { request } from "../../api/axios-api";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    user: null as UserDetails | null,
    token: null as string | null,
  }),
  getters: {},
  actions: {
    async authenticateUser(
      method: string,
      url: string,
      data: any
    ): Promise<void> {
      const { access_token, userDetails } = (await request(method, url, data))
        .data;
      this.user = userDetails;
      this.token = access_token;
    },

    isAuthenticated(): boolean {
      return !!this.token;
    },
  },
});
