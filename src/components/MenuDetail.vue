<template>
  <nav>
    <div class="logo">
      Logo
    </div>
    <ul class="menu">
      <li class="menu-item">
        <a href="">Homepage</a>
      </li>
      <li class="menu-item">
        <a>Test1</a>
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
        <a>Test2</a>
        <ul class="sub-menu">
          <li><a href="">Test2.1</a></li>
          <li><a href="">Test2.1</a></li>
          <li><a href="">Test2.1</a></li>
          <li><a href="">Test2.1</a></li>
          <li><a href="">Test2.1</a></li>
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
      <li class="menu-item">
        <a href="">Test4</a>
        <ul class="sub-menu">
          <li><a href="">Test4.1</a></li>
          <li><a href="">Test4.1</a></li>
          <li><a href="">Test4.1</a></li>
          <li><a href="">Test4.1</a></li>
          <li><a href="">Test4.1</a></li>
        </ul>
      </li>
      <li class="menu-item">
        <a href="">Test5</a>
        <ul class="sub-menu">
          <li><a href="">Test5.1</a></li>
          <li><a href="">Test5.1</a></li>
          <li><a href="">Test5.1</a></li>
          <li><a href="">Test5.1</a></li>
          <li><a href="">Test5.1</a></li>
        </ul>
      </li>
      <li class="menu-item">
        <a href="">Test6</a>
        <ul class="sub-menu">
          <li><a href="">Test6.1</a></li>
          <li><a href="">Test6.1</a></li>
          <li><a href="">Test6.1</a></li>
          <li><a href="">Test6.1</a></li>
          <li><a href="">Test6.1</a></li>
        </ul>
      </li>
    </ul>
    <div class="search">
      <input type="search" placeholder="Search...">
    </div>
  </nav>
</template>
<script>
export default {};
</script>
<style scoped>
nav {
  background-color: red;
  display: flex;
  justify-content: space-between;
}
.logo {
  width: 15%;
  font-size: 20px;
  color: white;
  padding: 10px;
  box-sizing: border-box;
}
.menu {
  width: 60%;
  display: flex;
  justify-content: space-between;
}
.menu li {
  list-style: none;
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
  top: 18px;
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
.search {
  width: 25%;
  padding: 10px;
  box-sizing: border-box;
}
.search input[type="search"] {
  font-size: 15px;
  border: 0px;
  border-radius: 5px;
  height: 30px;
  padding-left: 5px;
  margin-left: 30%;
  width: 70%;
}
</style>
