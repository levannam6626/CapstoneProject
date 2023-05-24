import { createRouter, createWebHistory } from "vue-router";
import AdminPage from "@/views/AdminPage.vue";
import LoginView from "@/views/LoginView.vue";
import HomePageView from "@/views/HomePageView.vue";
import RegisterView from "@/views/RegisterView.vue";
import ProductList from "@/views/ProductList.vue";
import IntroduceView from "@/views/IntroduceView.vue";
import ProductDetail from "@/views/ProductDetail.vue";
import AddProduct from "@/views/AddProduct.vue";
import EditProduct from "@/views/EditProduct.vue";

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
    alias: ['/homePage'],
    name: 'homePage',
    component: HomePageView,
    children: [
      {
        path: '/',
        alias: ['/homePage'],
        name: '',
        component: ProductList,
        children: [{
          path: 'productDetail',
          name: 'productDetail',
          component: ProductDetail,
        }],
      },
      {
        path: '/productList',
        name: 'productList',
        component: ProductList,
        children: [{
          path: '/productDetail',
          name: 'productDetail',
          component: ProductDetail,
        }],
      },
      {
        path: '/introduce',
        name: 'introduce',
        component: IntroduceView,
      },
      {
        path: '/addProduct',
        name: 'addProduct',
        component: AddProduct,
      },
      {
        path: '/editProduct/:productId',
        name: 'editProduct',
        component: EditProduct
      },
    ],
  },
  {
    path: '/admin',
    name: 'admin',
    component: AdminPage,
  },
]
const router = createRouter({
  history: createWebHistory(),
  routes,
});
// router.beforeEach((to, from, next) => {
//   const publicPages = ['/register','/login','/'];
//   const authRequired = !publicPages.includes(to.path);
//   const loggedIn = localStorage.getItem('token');

//   if (authRequired && !loggedIn) {
//     return next('/login');
//     //return next('/register');
//   }
//   if (to.name === "login" && loggedIn) {
//     return next('/admin')
//   }
//   next();
// })
export default router

