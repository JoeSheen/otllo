import { createRouter, createMemoryHistory } from "vue-router";

import LoginView from "../views/LoginView.vue";
import SignupView from "../views/SignupView.vue";
import HomeView from "../views/HomeView.vue";
import ProfileView from "../views/ProfileView.vue";

const routes = [
  { path: "/", name: "LoginView", component: LoginView },
  { path: "/signup", name: "SignupView", component: SignupView },
  { path: "/home", name: "HomeView", component: HomeView },
  { path: "/users/:id/profile", name: "ProfileView", component: ProfileView },
];

const router = createRouter({
  history: createMemoryHistory(),
  routes: routes,
});

export default router;
