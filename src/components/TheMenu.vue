<template>
  <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
  <div class="menu" style="margin:0px;">
    <div class="menu-user">
      <img src="" alt="">
      <p>User name</p>
    </div>
    <div class="menu-item">
      <a href="/dashboard">Dashboard</a>
    </div>
    <div class="menu-item">
      <a href="/campaign">Campaign</a>
    </div>
    <div class="menu-item">
      <a href="#">Account</a>
    </div>
    <div class="rest-menu">
    </div>
  </div>
</template>
<script>
</script>
<style scoped>
h3 {
  font-size: 24px;
}

h3 {
  font-family: "Segoe UI", Arial, sans-serif;
  font-weight: 400;
  margin: 10px 0;
}

.menu {
  height: 100%;
  overflow: auto;
  text-align: center;
}

.menu {
  width: 80%;
  display: block;
  padding: 0px;
  text-align: left;
  border: none;
  white-space: normal;
  float: none;
  outline: 0;
}

.menu-item {
  text-align: center;
  background-color: #468FAF;
  height: 40px;
  margin-bottom: 1px;
  padding-top: 15px;
}

.menu-item a {
  margin-top: 10px;
  color: white;
  font-size: 18px;
  text-decoration: none;
}

.menu-item a:hover {
  color: brown;
}

.menu {
  display: block;
  width: 100%;
}

.menu-user {
  text-align: center;
  background-color: #468FAF;
}

.menu-user img {
  height: 100px;
  width: 100px;
  border-radius: 50%;
  background-color: rgb(190, 188, 188);
  margin-top: 10px;
}

.menu-user p {
  margin-top: 7px;
  margin-bottom: 1px;
  padding-bottom: 10px;
  color: white;
  font-size: 18px;
}

.rest-menu {
  height: 440px;
  background-color: #468FAF;
}
</style>

