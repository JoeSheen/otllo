import { createRouter, createMemoryHistory } from "vue-router";
import LoginView from "../views/LoginView.vue";

const router = createRouter({
  history: createMemoryHistory(),
  routes: [{ path: "/", name: "LoginView", component: LoginView }],
});

export default router;
