<template>
  <nav>
    <div class="nav-left">
      <a style="background-color: #252525; font-size: 31px; padding: 12px 27px;" href="/"><font-awesome-icon icon="fa-solid fa-house" /></a>
      <ul class="menu">
        <li class="menu-item">
          <a href="addProduct" v-if="this.account.role === 'SELLER'">CREATE PRODUCT</a>
          <a href="introduce" v-else>INTRODUCE</a>
        </li>
        <li class="menu-item">
          <a @click="changeShowMenu()" style="min-width: 500px;">PRODUCT CATEGORY</a>
          <ul class="sub-menu">
            <li class="sub-menu-item" v-for="(category,index) in this.categories" :key="index">
              <a :href="category.categoryName">{{ category.categoryName }}</a>
              <!-- <ul>
                <li><a href="">Test1.1.1</a></li>
                <li><a href="">Test1.1.1</a></li>
                <li><a href="">Test1.1.1</a></li>
              </ul> -->
            </li>
          </ul>
        </li>
        <li class="menu-item">
          <a href="#contact">CONTACT</a>
        </li>
      </ul>
      <button><font-awesome-icon icon="fa-solid fa-bars" /></button>
    </div>
    <div class="search">
      <input type="search" placeholder="Search...">
      <font-awesome-icon class="search-icon" icon="fa-solid fa-magnifying-glass" />
    </div>
    <div class="user">
      <div class="cart" v-if="this.loggedIn === true">
        <font-awesome-icon class="cart-icon" icon="fa-solid fa-cart-shopping" @click = "cartList()"/>
        <span class="cart-count">0</span>
      </div>
      <button class="account" @click="login()">
        <span style="font-weight: bold;" v-if="this.loggedIn === false">SIGN-IN</span>
        <span v-else>{{ this.account.firstname }}</span>
        <button @click="logout()" v-if="this.loggedIn === true">Log-out</button>
      </button>
    </div>
    
  </nav>
</template>
<script>
import { library } from '@fortawesome/fontawesome-svg-core';
import { faMagnifyingGlass, faBars, faHouse } from '@fortawesome/free-solid-svg-icons';
library.add(faMagnifyingGlass, faBars, faHouse);

import { mapActions } from 'vuex';
import store from '@/store';

export default {
  data() {
    return {
      showMenu: true,
      loggedIn: store.state.account.loginStatus,
      account: store.state.account.userAccount,
      categories: store.state.product.categories,
    }
  },
  methods: {
    ...mapActions('auth',['clearStore']),
    ...mapActions('account',['logoutAction']),
    logout() {
      this.clearStore();
      this.logoutAction();
      this.$router.push('/homePage');
    },
    changeShowMenu() {
      this.showMenu = !this.showMenu;
      this.$emit('showMenuUpdated', this.showMenu);
    },
    login() {
      this.$router.push("login");
    },
    cartList() {

    },
  },
  mounted() {
    
  }
};
</script>
<style scoped>
nav {
  width: 100%;
  background-color: red;
  display: flex;
  box-sizing: border-box;
  padding: 0px 40px;
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
  min-width: 550px;
}
.menu {
  display: flex;
  margin: 0px;
  padding: 0px;
  align-items: center;
}
.menu > li {
  margin: 0px 2px;
}
.menu li {
  list-style: none;
}
.menu li a {
  text-decoration-line: none;
}
.menu > li > a {
  color: white;
  font-size: 18px;
}
.menu-item {
  position: relative;
}
.menu-item > a {
  box-sizing: border-box;
  cursor: pointer;
  width: auto;
  padding: 20px 10px;
}
.menu-item > a:hover {
  background-color: #252525;
}
.menu-item:hover > .sub-menu {
  display: inline-block;
}
.sub-menu {
  position: absolute;
  background-color: white;
  width: 200px;
  left: 0px;
  top: 40px;
  display: none;
  box-sizing: border-box;
  padding: 0;
  text-align: center;
  padding-top: 10px;
  border-radius: 5px;
  border: solid rgb(54, 54, 54) 1px;
  box-shadow: 0px 5px 5px 0px;
}
.sub-menu a:hover {
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
.nav-left button {
  height: 37px;
  margin-top: 10px;
  display: none;
  margin-left: 30px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  box-shadow: grey 2px 2px;
}
.nav-left button:hover {
  background-color: grey;
}
.search {
  width: 20%;
  min-width: 150px;
  box-sizing: border-box;
  padding: 10px;
  position: relative;
}
.search-icon {
  position: absolute;
  left: 10px;
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
  padding-left: 25px;
  width: 100%;
}
.search input[type="search"]:focus {
  outline: none;
}
.user {
  display: flex;
  gap: 10px;
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
  left: 45%;
  color: red;
  top: 2px;
  font-weight: bold;
}
.account {
  position: relative;
  border-radius: 10px;
  background-color: #fff;
  width: 80px;
  margin: 10px 0px;
  color: red;
  font-size: 15px;
  cursor: pointer;
  font-weight: bold;
}
.account:hover {
  background-color: red;
  color: #fff;
}
.account button {
  position: absolute;
  display: none;
  cursor: pointer;
  left: 0px;
  top: 35px;
  font-size: 15px;
  width: 80px;
  border-radius: 10px;
  height: 40px;
  color: red;
}
.account button:hover {
  background-color: red;
  color: #fff;
}
.account:hover button{
  display: block;
}
.account span{
  width: 64px ;
}
@media screen and ( max-width: 880px) {
  .nav-left {
    min-width: 135px;
  }
  .nav-left .menu {
    display: none;
  }
  .nav-left > button {
    display: block;
  }
}

</style>
