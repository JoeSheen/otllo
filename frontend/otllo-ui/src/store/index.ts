import { defineStore } from "pinia";

export const useAuthStore = defineStore("auth", {
  state: () => ({
    user: null as UserDetails | null,
    token: null as string | null,
  }),
  getters: {
    test(state) {
      return state.user;
    },
  },
  actions: {
    setUser(val: any) {
      this.user = val;
    },
  },
});

interface UserDetails {
  id: string;
  firstName: string;
  lastName: string;
  dateOfBirth: Date;
  gender: string;
  email: string;
  phoneNumber: string;
  username: string;
  profileImage: string;
  visible: boolean;
  status: string;
}
