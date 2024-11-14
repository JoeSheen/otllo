import { createRouter, createMemoryHistory } from "vue-router";

import LoginView from "../views/LoginView.vue";
import SignupView from "../views/SignupView.vue";
import HomeView from "../views/HomeView.vue";

const routes = [
  { path: "/", name: "LoginView", component: LoginView },
  { path: "/signup", name: "SignupView", component: SignupView },
  { path: "/home", name: "HomeView", component: HomeView },
];

const router = createRouter({
  history: createMemoryHistory(),
  routes: routes,
});

export default router;
