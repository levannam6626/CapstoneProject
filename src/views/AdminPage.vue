<template>
  <div class="container">
    <aside v-show="showAside">
      <div class="account">
        <figure>
          <img src="../assets/signin-image.jpg" alt="">
        </figure>
        <h1>ADMIN</h1>
      </div>
      <div class="menu">
        <ul>
          <li><a href="">Dashboard</a></li>
          <li></li>
        </ul>
      </div>
    </aside>
    <div class="content">
      <div class="header">
        <div class="header-btn">
          <button @click="showAside=!showAside"><font-awesome-icon icon="fa-solid fa-bars" /></button>
          <button @click="logout()">Log-out <font-awesome-icon icon="fa-solid fa-right-from-bracket" /></button>
        </div>
        <div class="detail">
          <div  class="detail-item">Users</div>
          <div  class="detail-item">Saler</div>
          <div  class="detail-item"></div>
          <div  class="detail-item"></div>
        </div>
      </div>
      <div class="table-user" >
        <table align="center">
          <tr style="background-color: #468faf">
            <th style="width: 18%">Email</th>
            <th style="width: 18%">First Name</th>
            <th style="width: 18%">Last Name</th>
            <th style="width: 20%">Address</th>
            <th style="width: 12%">Phone</th>
            <th style="width: 15%">Role</th>
          </tr>
          <tr v-for="(user, index) in this.users" :key="index" >
            <td>{{ user.email }}</td>
            <td>{{ user.firstname }}</td>
            <td>{{ user.lastname }}</td>
            <td>{{ user.address }}</td>
            <td>{{ user.phone }}</td>
            <td>{{ user.role }}</td>
          </tr>
        </table>
      </div>
    </div>
  </div>
</template>
<script>
import { library } from '@fortawesome/fontawesome-svg-core';
import { faBars, faRightFromBracket } from '@fortawesome/free-solid-svg-icons';
import { mapActions } from 'vuex';
library.add(faBars, faRightFromBracket);
export default ({
  data() {
    return {
      showAside: true,
      users: [],
    }
  },
  methods: {
    ...mapActions('auth',['clearStore']),
    ...mapActions('account',['logoutAction']),
    logout() {
      this.clearStore();
      this.logoutAction();
      this.$router.push('/');
    }
  }
})
</script>
<style scoped>
.container {
  display: flex;
  width: 100%;
  height: 100%;
}
aside {
  width: 20%;
  height: 100%;
  background-color: rgb(231, 227, 227);
}
.account {
  height: 170px;
  display: grid;
  box-sizing: border-box;
  padding: 10px;
  background-color: #0075BA;
  justify-content: center;
}
.account figure {
  padding: 0px;
  margin: 0px;
  width: 100px;
  height: 100px;
  overflow: hidden;
  border-radius: 50%;
}
.account figure {
  height: 100px;
  width: 100px;
}
.account h1 {
  text-align: center;
  width: 100px;
  color: #fff;
}
.content {
  background-color: #fff;
  width: 100%;
  height: 100%;
}
.header {
  background-color: #0075BA;
  height: 170px;
  display: grid;
  box-sizing: border-box;
  padding-right: 20px;
}
.header-btn {
  display: flex;
  justify-content: space-between;
  height: 50px;
  justify-items: center;
}
.header-btn button {
  font-size: 18px;
  background-color: #0075BA;
  color: #fff;
  border: none;
  cursor: pointer;
}
.detail {
  background-color: #fff;
  height: 120px;
  border-radius: 3px 3px 0px 0px;
  display: flex;
  box-sizing: border-box;
  padding: 30px 15px 0px;
  gap: 20px;
}
.detail-item {
  background-color: #00ba4d;
  width: 40%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  color: #FFF;
}
.table-user {
  margin-top: 20px;
  width: 80%;
  display: flex;
  justify-content: center;
}
.table-user table {
  width: 100%;
}
</style>

