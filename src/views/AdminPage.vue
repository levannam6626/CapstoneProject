<template>
  <div class="container">
    <aside v-show="showAside">
      <div class="account">
        <figure>
          <img src="../assets/signin-image.jpg" alt="">
        </figure>
        <h1>ADMIN</h1>
      </div>
      <ul class="menu">
        <li><a href="/admin">Dashboard</a></li>
        <li></li>
      </ul>
    </aside>
    <div class="content">
      <div class="header">
        <div class="header-btn">
          <button @click="showAside=!showAside"><font-awesome-icon icon="fa-solid fa-bars" /></button>
          <button @click="logout()"><font-awesome-icon icon="fa-solid fa-right-from-bracket" /></button>
        </div>
        <div class="detail">
          <div class="detail-item" style=" background-color: #C688EB;"><span>{{ this.userCount }}</span>user accounts</div>
          <div class="detail-item" style=" background-color: #ffc4a4;"><span>{{ this.customerCount }}</span>customer</div>
          <div class="detail-item" style=" background-color: #fba2d0;"><span>{{ this.sellerCount }}</span>seller</div>
        </div>
      </div>
      <nav class="action">
        <span for="">User Detail</span>
        <div class="action-search">
          <font-awesome-icon class="search-icon" icon="fa-solid fa-magnifying-glass" />
          <input type="search" placeholder="Search by email" v-model="emailSearch">
        </div>
        <div class="action-btn">
          <button class="btn-add" @click="showAddUserForm = !showAddUserForm">Add</button>
          <button class="btn-del" @click="deleteAccounts()">Delete</button>
        </div>
      </nav>
      <div class="table-user" >
        <table align="center" >
          <thead>
            <tr style="background-color: rgb(5, 5, 194)">
              <th style="width: 22%">Email</th>
              <th style="width: 15%">First Name</th>
              <th style="width: 15%">Last Name</th>
              <th style="width: 22%">Address</th>
              <th style="width: 10%">Phone</th>
              <th style="width: 10%">Role</th>
              <th style="width: 5%">Action</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(user, index) in this.displayedUsers" :key="index" >
              <td style="box-sizing: border-box; padding-left: 5px; text-align: left;">{{ user.email }}</td>
              <td>{{ user.firstname }}</td>
              <td>{{ user.lastname }}</td>
              <td >{{ user.address }}</td>
              <td>{{ user.phone }}</td>
              <td style="box-sizing: border-box; padding-left: 5px; text-align: left;">{{ user.role }}</td>
              <td><input type="checkbox" name="action" :value="user" v-model="selectedOptions"></td>
            </tr>
          </tbody>
        </table>
        <div class="pagination-table">
          <button @click="currentPage--" :disabled="currentPage === 1">Prev</button>
          <span>{{ currentPage }}</span>
          <button @click="currentPage++" :disabled="currentPage === this.totalPages">Next</button>
        </div>
      </div>
      <div class="register-form" v-show="this.showAddUserForm" @click="closeRegisterForm($event)">
        <RegisterForm :logedinRole = "'ADMIN'" class="adduser" @closeForm="closeFormInParent"/>
      </div>
      
    </div>
  </div>
</template>
<script>
import store from '@/store';
import { mapActions } from 'vuex';
import RegisterForm from '@/components/RegisterForm.vue';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faBars, faRightFromBracket, faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
library.add(faBars, faRightFromBracket, faMagnifyingGlass);

export default ({
  data() {
    return {
      showAside: true,
      users: [],
      currentPage: 1,
      usersPerPage: 6,
      userCount: 0,
      customerCount: 0,
      sellerCount: 0,
      selectedOptions: [],
      deleteAccountAction: false,
      emailSearch: "",
      showAddUserForm: false
    }
  },
  computed: {
    userFilter() {
      return this.users.filter(user => {
        return user.email.toLowerCase().includes(this.emailSearch.toLowerCase());
      });
    },
    displayedUsers() {
      const startIndex = (this.currentPage - 1) * this.usersPerPage;
      const endIndex = startIndex + this.usersPerPage;
      return this.userFilter.slice(startIndex, endIndex);
    },
    totalPages() {
      return Math.max(Math.ceil(this.userFilter.length / this.usersPerPage), 1);
    }
  },
  components: {
    RegisterForm,
  },
  watch: {
    deleteAccountAction(deleteAccountAction) {
      if (deleteAccountAction === true) {
        this.loadUserAccount('all');
        this.deleteAccountAction = false;
      }
    }
  },
  methods: {
    ...mapActions('auth',['deToken','logoutAction']),
    ...mapActions('account',['getUserListAction','deleteAccountsAction']),
    logout() {
      this.deToken('logout');
      this.logoutAction();
      this.$router.push('/login');
    },
    async loadUserAccount(email) {
      await this.getUserListAction(email);
      this.users = store.state.account.userList;
      this.userCount = this.users.length;
      this.sellerCount = 0;
      this.customerCount = 0;
      this.users.forEach(user => {
        if(user.role === "SELLER") {
          this.sellerCount = this.sellerCount + 1;
        }else if(user.role === "CUSTOMER") {
          this.customerCount = this.customerCount + 1;
        }
      });
    },
    async deleteAccounts() {
      if(this.selectedOptions.length > 0){
        if(confirm("Do you really want to delete?")){
          await this.deleteAccountsAction(this.selectedOptions);
          this.deleteAccountAction = true;
        }
      }
    },
    closeRegisterForm(event) {
      if (event.target.className === "register-form") {
        this.showAddUserForm = false;
      }
    },
    closeFormInParent(status) {
      if (status) {
        this.showAddUserForm = false;
        this.loadUserAccount('all');
      }
    }
  },
  mounted() {
    this.loadUserAccount('all');
  },
})
</script>
<style scoped>
.love {
  color: red;
  font-size: 30px;
  text-align: center;
}
.container {
  display: flex;
  width: 100%;
  height: 100%;
}
aside {
  width: 18%;
  height: 100%;
  background-color: #6c7ee1;
  box-sizing: border-box;
  margin: 0px;
}
.account {
  height: 179px;
  box-sizing: border-box;
  display: grid;
  padding: 10px;
  background-color: #6c7ee1;
  justify-content: center;
  border-bottom: solid 2px rgb(231, 227, 227);
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
.menu {
  box-sizing: border-box;
  margin: 0;
  padding: 0;
  margin-top: 2px;
  display: grid;
}
.menu li {
  list-style-type: none;
  width: 100%;
  border-bottom: solid 1px rgb(231, 227, 227);
}
.menu li a {
  display: inline-block;
  text-decoration: none;
  font-size: 20px;
  width: 100%;
  box-sizing: border-box;
  background-color: #6c7ee1;
  height: 45px;
  display: flex;
  align-items: center;
  padding-left: 30px;
  margin-bottom: 2px;
  color: #fff;
}
.content {
  background-color: #fff;
  width: 100%;
  height: 100%;
}
.header {
  background-color: #6c7ee1;
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
  background-color: #6c7ee1;
  color: #fff;
  border: none;
  cursor: pointer;
}
.header-btn button:hover {
  color: red;
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
  width: 40%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 20px;
  color: black;
}
.detail-item span {
  font-size: 30px;
  margin-right: 10px;
}
.action {
  display: flex;
  justify-content: space-between;
  box-sizing: border-box;
  padding: 0px 20px;
  margin: 20px 35px 10px 15px;
  background-color: #EAE7D6;
  height: 60px;
  align-items: center;
  font-size: 20px;
  gap: 10px;
}
.action-search {
  display: flex;
  position: relative;
  width: 50%;
}
.action-search input {
  height: 38px;
  border: 0px;
  border-radius: 8px;
  font-size: 16px;
  box-sizing: border-box;
  padding-left: 22px;
  width: 100%;
}
.action-search input:focus {
  outline: none;
}
.search-icon {
  position: absolute;
  left: 5px;
  top: 11px;
  font-size: 15px;
}
.action-btn {
  display: flex;
  gap: 10px;
}
.action-btn button{
  font-size: 15px;
  box-sizing: border-box;
  padding: 10px;
  border-radius: 8px;
  width: 50%;
  border: solid 0.5px;
  font-weight: bold;
  color: black;
  cursor: pointer;
}
.btn-add {
  background-color: #FEA837;
}
.btn-add:hover {
  background-color: #DE741C;
  color: #fff;
}
.btn-del {
  background-color: #00ba4d;
}
.btn-del:hover {
  background-color: #046e30;
  color: #fff;
}
.table-user {
  margin-top: 15px;
  width: 100%;
  box-sizing: border-box;
  padding: 0px 35px 0px 15px;
  overflow-x: auto;
}
.table-user table {
  width: 100%;
}
.table-user th {
  height: 35px;
}
table {
  margin-top: 30px;
  border-collapse: collapse;
  width: 100%;
}
table,
td,
th {
  border: solid 1px black;
}
th {
  height: 55px;
  color: white;
}
tr {
  height: 40px;
}
tr,
th,
td {
  margin: 0px;
  padding: 0px;
}
td {
  text-align: center;
}
.pagination-table {
  display: flex;
  justify-content: center;
  gap: 15px;
  margin-top: 20px;
}
.register-form {
  width: 100%;
  height: 100%;
  position: absolute;
  top: 0px;
  left: 0px;
  background-color: rgba(0,0,0,0.5);
  z-index: 0;
  display: flex;
  justify-content: center;
}
.adduser {
  position: absolute;
  top: 8%;
  width: 65%;
  z-index: 1;
  box-sizing: border-box;
  display: flex;
  justify-content: center;
}
@media screen and (max-width: 780px) {
  aside {
    display: none;
  }
  .content {
    margin: 0px;
  }
}
@media screen and (max-width: 580px) {
  .table-user {
    padding: 0px;
  }
}
</style>

