import { createRouter, createWebHistory } from "vue-router";
import LoginView from "../views/LoginView.vue";
import HomePageView from "../views/HomePageView.vue";
import RegisterView from "../views/RegisterView.vue";

const routes = [
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView,
  },
  {
    path: '/',
    name: 'homePageView',
    component: HomePageView,
  },
]
const router = createRouter({
  history: createWebHistory(),
  routes,
});
// router.beforeEach((to, from, next) => {
//   const publicPages = ['/login'];
//   const authRequired = !publicPages.includes(to.path);
//   const loggedIn = localStorage.getItem('token');

//   if (authRequired && !loggedIn) {
//     return next('/login');
//     //return next('/register');
//   }
//   if (to.name === "login" && loggedIn) {
//     return next('/')
//   }
//   next();
// })
export default router

