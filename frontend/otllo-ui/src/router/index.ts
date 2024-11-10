import { createRouter, createMemoryHistory } from "vue-router";

import LoginView from "../views/LoginView.vue";
import SignupView from "../views/SignupView.vue";

const router = createRouter({
  history: createMemoryHistory(),
  routes: [
    { path: "/", component: LoginView },
    { path: "/signup", component: SignupView },
  ],
});

export default router;
