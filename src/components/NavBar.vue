<template>
  <nav>
    <div class="nav-left">
      <a href="/"><font-awesome-icon icon="fa-solid fa-house" /></a>
      <ul class="menu" id="menu">
        <li class="menu-item">
          <a @click="this.$router.push('/add-product')" v-if="this.account.role === 'SELLER'">CREATE PRODUCT</a>
          <a @click="this.$router.push('/introduce')" v-else>INTRODUCE</a>
        </li>
        <li class="menu-item">
          <a @click="showProductCategory()" style="min-width: 220px">PRODUCT CATEGORY</a>
          <TheMenu class="sub-menu" id="sub-menu" :showh3="false"/>
        </li>
        <li class="menu-item">
          <a href="/bills" v-if="this.loggedIn === true">BILLS</a>
        </li>
        <li class="menu-item" v-if="this.account.role !== 'SELLER'">
          <a href="/#footer-contact">CONTACT</a>
        </li>
      </ul>
    </div>
    <div class="user">
      <div class="search">
        <input type="search" placeholder="Search by name ..." v-on:keyup.enter="this.$emit('searchProduct',this.productName)" v-model="this.productName">
        <font-awesome-icon class="search-icon" icon="fa-solid fa-magnifying-glass" />
      </div>
      <a @click="this.$router.push('/cart-list')" class="cart" v-if="this.loggedIn === true && this.account.role === 'CUSTOMER'">
        <font-awesome-icon class="cart-icon" icon="fa-solid fa-cart-shopping"/>
        <span class="cart-count">{{ this.cartCount }}</span>
      </a>
      <button class="account login" @click="login()" v-if="this.loggedIn === false">
        <span style="font-weight: bold;" >SIGN-IN</span>
      </button>
      <button class="account detail" @click="showDetail($event)" v-else>
        <span id="name">{{ this.account.firstname }}</span>
      </button>
      <UserDetail class="user-detail" v-show="showUserDetail"/>
    </div>
  </nav>
  <div class="button-toggle" @click="changeShowMenu()">
    <button ><font-awesome-icon icon="fa-solid fa-bars" /></button>
    <font-awesome-icon icon="fa-solid fa-chevron-up" id="icon" />
  </div>
</template>
<script>
import UserDetail from '../views/UserDetail.vue';
import TheMenu from '@/components/TheMenu.vue';

import { library } from '@fortawesome/fontawesome-svg-core';
import { faMagnifyingGlass, faBars, faHouse, faChevronUp } from '@fortawesome/free-solid-svg-icons';
library.add(faMagnifyingGlass, faBars, faHouse, faChevronUp);

import store from '@/store';
import { mapActions } from 'vuex';

export default {
  data() {
    return {
      loggedIn: store.state.auth.loginStatus,
      account: store.state.auth.userAccount,
      productName: '',
      showUserDetail: false,
    }
  },
  emits :{
    searchProduct: null
  },
  computed: {
    cart() {
      return store.state.cart.cartList
    },
    cartCount() {
      if(this.cart.cartItems !== undefined) {
        return this.cart.cartItems.length;
      }else {
        return 0;
      }
    }
  },
  components: {
    TheMenu,
    UserDetail,
  },
  mounted() {
    this.getCart();
  },
  methods: {
    ...mapActions('cart',['getCartAction']),
    async getCart() {
      await this.getCartAction();
    },
    changeShowMenu() {
      let icon = document.getElementById('icon');
      let menu = document.getElementById('menu');
      if(menu.className === 'menu') {
        menu.className += ' responsive';
      }else {
        menu.className = 'menu';
      }
      if(icon.style.transform === 'rotate(90deg)') {
        icon.style.transform = 'rotate(180deg)'
      }else {
        icon.style.transform = 'rotate(90deg)'
      }
    },
    showProductCategory() {
      let subMenu = document.getElementById('sub-menu');
      if(subMenu.className === 'menu sub-menu') {
        subMenu.className += ' category';
      }else {
        subMenu.className = 'menu sub-menu';
      }
    },
    login() {
      this.$router.push("/login");
    },
    showDetail(event) {
      if(event.target.className === 'account detail' || event.target.id === 'name') {
        this.showUserDetail = !this.showUserDetail;
      }
    }
  },
};
</script>
<style scoped>
nav {
  width: 100%;
  min-width: 285px;
  background-color: red;
  display: flex;
  box-sizing: border-box;
  padding: 0px 25px;
  gap: 10px;
  justify-content: space-between;
}
.nav-left {
  display: flex;
  width: 60%;
  margin: 0px;
  padding: 0px;
  align-self: center;
  gap: 5px;
  min-width: 600px;
}
.menu {
  display: flex;
  margin: 0px;
  padding: 0px;
  align-items: center;
}
.menu li {
  list-style: none;
}
.menu li a {
  text-decoration-line: none;
}
.menu-item {
  position: relative;
  height: 100%;
}
.menu-item > a {
  box-sizing: border-box;
  cursor: pointer;
  width: auto;
  padding: 0px 10px;
  color: white;
  font-size: 18px;
  height: 100%;
  display: flex;
  align-items: center;
}
.menu-item > a:hover {
  background-color: #252525;
}
.category {
  display: inline-block !important;
}
.sub-menu {
  position: absolute;
  background-color: white;
  left: 0px;
  top: 60px;
  display: none;
  box-sizing: border-box;
  padding: 0;
  padding-top: 10px;
  border-radius: 5px;
  border: solid rgb(54, 54, 54) 1px;
  box-shadow: 0px 5px 5px 0px;
  height: auto;
  z-index: 2;
}
.sub-menu a:hover {
  color: red;
  background-color: #90AA00;
}
.sub-menu-item {
  position: relative;

}
.sub-menu-item > a {
  box-sizing: border-box;
  text-align: left;
  padding: 10px 0px 10px 10px;
  color: black;
  display: block;
  text-transform: uppercase;
}
.sub-menu .sub-menu-item ul{
  position: absolute;
  display: none;
  background-color: white;
  border-radius: 5px;
  border: solid rgb(54, 54, 54) 1px;
  box-shadow: 0px 5px 5px 0px;
  text-align: center;
  width: 150px;
  padding: 0;
  padding-top: 5px;
  left: 199px;
}
.sub-menu .sub-menu-item:hover ul{
  display: inline-block;
}
.nav-left > a {
  background-color: #252525;
  text-align: center;
  font-size: 31px;
  padding: 12px 5px;
  width: 40%;
  min-width: 35px;
  max-width: 70px;
  color: #551A8B;
}
.nav-left button {
  height: 37px;
  margin-top: 10px;
  display: none;
  margin-left: 20px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  box-shadow: grey 2px 2px;
}
.nav-left button:hover {
  background-color: grey;
}
.search {
  width: 100%;
  box-sizing: border-box;
  padding: 10px 0;
  position: relative;
  min-width: 60px;
}
.search-icon {
  position: absolute;
  left: 4px;
  top: 15px;
  padding: 6px 3px;
  border: solid 1px white;
  border-radius: 5px 0px 0px 5px;
  background-color: white;
}
.search input[type="search"] {
  font-size: 16px;
  border: 0px;
  border-radius: 10px;
  height: 40px;
  padding-left: 28px;
  width: 100%;
}
.search input[type="search"]:focus {
  outline: none;
}
.user {
  width: 100%;
  max-width: 550px;
  display: flex;
  gap: 10px;
  position: relative;
  justify-content: space-between;
}
.cart {
  color: #fff;
  align-self: center;
  position: relative;
  cursor: pointer;
}
.cart-icon {
  font-size: 36px;
}
.cart-count {
  position: absolute;
  width: 22px;
  text-align: center;
  left: 30%;
  color: red;
  top: 2px;
  font-weight: bold;
}
.account {
  position: relative;
  border-radius: 10px;
  background-color: #fff;
  min-width: 80px;
  max-width: 90px;
  margin: 10px 0px;
  color: red;
  font-size: 15px;
  cursor: pointer;
  font-weight: bold;
}
.login:hover {
  background-color: red;
  color: #fff;  
}

.account span{
  display: block;
  width: 64px ;
  overflow: hidden;
}
.user-detail {
  position: absolute;
  right: 70px;
  top: 47px;
  width: 250px;
  background-color: rgb(15, 15, 15);
  border-radius: 10px;
  z-index: 2;
}
.button-toggle {
  display: none;
  min-width: 285px;
  cursor: pointer;
}
.button-toggle button, .button-toggle #icon {
  color: #fff;
  font-size: 25px;
  cursor: pointer;
}
.button-toggle button {
  background-color: red;
  border: 0;
  padding: 0;
}
.button-toggle #icon{
  transform: rotate(180deg);
}
@media screen and ( max-width: 920px) {
  nav {
    position: relative;
    gap: 0 !important;
  }
  .responsive .menu-item a{
    height: 40px;
    background-color: #252525;
    color: #fff;
  }
  .responsive {
    display: block !important;
    position: absolute;
    z-index: 1;
    left: 0;
    top: 60px;
    width: 100%;
    min-width: 300px;
    box-sizing: border-box;
    margin-top: 40px;
    border-bottom: solid 2px #252525;
  }
  .responsive a {
    padding: 0 25px;
  }
  .responsive a:hover {
    background-color: #DDDDDD !important;
    color: black !important;
  }
  .category {
    right: 0px;
    top: 40px;
  }
  .nav-left {
    min-width: 50px;
    width: 40%;
  }
  .nav-left .menu {
    display: none;
  }
  nav + .button-toggle {
    border-top: 2px solid #fff;
    display: block;
    width: 100%;
    background-color: red;
    height: 40px;
    display: flex;
    justify-content: space-between;
    box-sizing: border-box;
    padding: 0 25px;
    align-items: center;
  }
  .search {
    padding: 10px 0;
  }
}
@media screen and (max-width: 365px) {
  .user-detail {
    right: 10px;
    top: 48px;
  }
}
@media screen and (max-width: 320px) {
  nav {
    padding: 0 10px;
  }
  .button-toggle {
    padding: 0px 10px !important;
  }
}
</style>

