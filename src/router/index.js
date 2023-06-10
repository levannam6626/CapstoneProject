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
import TestView from "@/views/TestView.vue";
import store from "@/store";

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
    name: 'homePage',
    component: HomePageView,
    children: [
      {
        path: '/:categoryName',
        alias: ['/','/:categoryName/:productName'],
        name: 'productList',
        component: ProductList,
        children: [{
          path: '/:categoryName/:productName',
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
        components: {
          default: ProductList,
          productAction: AddProduct,
        }
      },
      {
        path: '/editProduct/:productId',
        name: 'editProduct',
        components: {
          default: ProductList,
          productAction: EditProduct,
        }
      },
    ],
  },
  {
    path: '/admin',
    name: 'admin',
    component: AdminPage,
  },
  {
    path: '/test',
    name: 'test',
    component: TestView,
  },
]
const router = createRouter({
  history: createWebHistory(),
  routes,
});
router.beforeEach((to, from, next) => {
  const publicPages = ['/register','/login','/','/introduce','/#contact'];
  const authRequired = !publicPages.includes(to.path);
  const loggedIn = store.state.auth.token;
  const loginRole = store.state.auth.userAccount.role;

  if(to.params.categoryName !== undefined) {
    next();
  }else if (authRequired && !loggedIn) {
    return next('/login');
  }if(to.path === '/admin') {
    if(loginRole === 'ADMIN') {
      next();
    }else {
      next('/');
    }
  }else{
    next();
  }
})
export default router

