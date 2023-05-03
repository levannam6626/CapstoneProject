<template>
  <nav>
    <div class="logo">
      Logo
    </div>
    <div class="center">
      <ul class="menu">
        <li class="menu-item">
          <a href="/">Homepage</a>
        </li>
        <li class="menu-item">
          <a>Introduce</a>
        </li>
        <li class="menu-item" @click="changeShowMenu()">
          <a>Product Category</a>
          <ul class="sub-menu">
            <li class="sub-menu-item">
              <a href="">Test1.1</a>
              <ul>
                <li><a href="">Test1.1.1</a></li>
                <li><a href="">Test1.1.1</a></li>
                <li><a href="">Test1.1.1</a></li>
              </ul>
            </li>
            <li class="sub-menu-item"><a href="">Test1.1</a></li>
            <li class="sub-menu-item"><a href="">Test1.1</a></li>
            <li class="sub-menu-item"><a href="">Test1.1</a></li>
            <li class="sub-menu-item"><a href="">Test1.1</a></li>
          </ul>
        </li>
        <li class="menu-item">
          <a href="#">Test3</a>
          <ul class="sub-menu">
            <li><a href="">Test3.1</a></li>
            <li><a href="">Test3.1</a></li>
            <li><a href="">Test3.1</a></li>
            <li><a href="">Test3.1</a></li>
            <li><a href="">Test3.1</a></li>
          </ul>
        </li>
      </ul>
      <button><font-awesome-icon icon="fa-solid fa-bars" /></button>
    </div>
    <div class="search">
      <input type="search" placeholder="Search...">
      <font-awesome-icon class="search-icon" icon="fa-solid fa-magnifying-glass" />
    </div>
  </nav>
</template>
<script>
import { library } from '@fortawesome/fontawesome-svg-core'
import { faMagnifyingGlass, faBars } from '@fortawesome/free-solid-svg-icons'
library.add(faMagnifyingGlass, faBars)

export default {
  data() {
    return {
      showMenu: true
    }
  },
  methods: {
    changeShowMenu() {
      this.showMenu = !this.showMenu;
      this.$emit('showMenuUpdated', this.showMenu);
    }
  },
};
</script>
<style scoped>
nav {
  width: 100%;
  background-color: red;
  display: flex;
  justify-content: space-between;
  box-sizing: border-box;
  padding: 0px 30px 0px 40px;
}
.logo {
  width: 15%;
  min-width: 150px;
  font-size: 20px;
  color: white;
  padding: 16px 30px;
  box-sizing: border-box;
  background-color: rgb(26, 23, 23);
}
.center {
  display: flex;
  width: 60%;
  height: 100%;
  margin: 0px;
  padding: 0px;
}
.menu {
  display: flex;
  height: 100%;
  margin: 0px;
  padding: 0px;
  width: 100%;
}
.menu>li {
  height: 100%;
  box-sizing: border-box;
  padding: 16px 15px;
  margin: 0px 2px;
}
.menu li {
  list-style: none;
  box-sizing: border-box;
}
.menu li:hover {
  background-color: rgb(26, 23, 23);
}
.menu li {
  cursor:pointer;
}
.menu li a {
  text-decoration-line: none;
  color: white;
  font-size: 18px;
}
.menu .menu-item {
  position: relative;
}
.menu .menu-item:hover>.sub-menu {
  display: inline-block;
}
.menu .menu-item .sub-menu {
  position: absolute;
  background-color: white;
  width: 150px;
  left: 0px;
  top: 60px;
  display: none;
  box-sizing: border-box;
  padding: 0;
  text-align: center;
  padding-top: 10px;
  border-radius: 5px;
  border: solid rgb(54, 54, 54) 1px;
  box-shadow: 0px 5px 5px 0px;
}
.sub-menu li {
  height: 25px;
}
.sub-menu li>a {
  color: black;
}
.sub-menu li:hover {
  background-color: #90AA00;
}
.sub-menu .sub-menu-item {
  position: relative;
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
  padding-top: 10px;
  left: 150px;
}
.sub-menu .sub-menu-item:hover ul{
  display: inline-block;
}
.center button {
  height: 37px;
  margin-top: 10px;
  display: none;
  margin-left: 30px;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  box-shadow: grey 2px 2px;
}
.center button:hover {
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
@media screen and ( max-width: 750px) {
  .center .menu {
    display: none;
  }
  .center > button {
    display: block;
  }
}
</style>
