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

import TheMenu from "@/components/TheMenu.vue";
import BannerImg from "@/components/BannerImg.vue";

import BillView from "@/views/BillView.vue";
import CartList from "@/views/CartList.vue";
import SuccessAlert from "@/components/SuccessAlert.vue";

import store from "@/store";
import UserDetail from "@/views/UserDetail.vue";

const routes = [
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/register',
    name: 'register',
    component: RegisterView
  },
  {
    path: '/',
    name: 'homePage',
    component: HomePageView,
    children: [
      {
        path: '/:categoryName',
        alias: ['/','/search-product/:productName'],
        name: 'productList',
        components: {
          default: ProductList,
          banner: BannerImg,
          theMenu: TheMenu
        }
      },
      {
        path: '/:categoryName/:productName',
        name: 'productDetail',
        component: ProductDetail
      },
      {
        path: '/user-infor',
        name: 'userInfor',
        component: UserDetail
      },
      {
        path: '/introduce',
        name: 'introduce',
        component: IntroduceView
      },
      {
        path: '/add-product',
        name: 'addProduct',
        component: AddProduct
      },
      {
        path: '/edit-product/:productId',
        name: 'editProduct',
        component: EditProduct
      },
      {
        path: '/cart-list',
        name: 'cartList',
        component: CartList
      },
      {
        path: '/bills',
        name: 'bill',
        component: BillView
      }
    ],
  },
  {
    path: '/admin',
    name: 'admin',
    component: AdminPage
  },
  {
    path: '/success',
    name: 'success',
    component: SuccessAlert
  }
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
    return
  }else if (authRequired && !loggedIn) {
    return next('/login');
  }if(to.path === '/admin') {
    if(loginRole === 'ADMIN') {
      next();
      return
    }else {
      next('/');
      return
    }
  }else{
    next();
    return
  }
})
export default router

