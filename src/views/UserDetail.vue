<template>
  <form @submit.prevent="saveUser">
    <h2>Information</h2>
    <table align="center">
      <tr>
        <th>Email</th>
        <td><input type="email" v-model="userAccount.email" required readonly></td>
      </tr>
      <tr>
        <th>First Name</th>
        <td><input type="text" v-model="userAccount.firstname" maxlength="20" required :readonly="!edit"></td>
      </tr>
      <tr>
        <th>Last Name</th>
        <td><input type="text" v-model="userAccount.lastname" maxlength="20" required :readonly="!edit"></td>
      </tr>
      <tr>
        <th>Address</th>
        <td><input type="text" v-model="userAccount.address" maxlength="150" required :readonly="!edit"></td>
      </tr>
      <tr>
        <th>Phone</th>
        <td><input type="text" v-model="userAccount.phone" minlength="10" maxlength="13" required :readonly="!edit"></td>
      </tr>
      <tr>
        <th>Gender</th>
        <td>
          <select name="gender" id="gender" v-model="userAccount.gender" :disabled="!edit">
            <option value="Male">MALE</option>
            <option value="Female">FEMALE</option>
          </select>
        </td>
      </tr>
    </table>
    <span style="display:flex;color: red; font-size: 10px; justify-content: center; margin-top: 5px;">{{ message }}</span>
    <div class="action">
      <a @click="showEdit()" v-show="!edit">Edit</a>
      <button type="submit" id="submit" v-show="edit">Submit</button>
      <a class="logout-btn" @click="this.$emit('cancelProfile', true)">Cancel</a>
    </div>
  </form>
</template>
<script>
import store from '@/store';
import { mapActions } from 'vuex';

export default {
  data() {
    return {
      reg: {
        regName: /^[A-Za-z][A-Za-z0-9_ -]{0,20}$/,
        regAddress: /^[A-Za-z0-9 ,.-]{1,150}$/,
        regPhone: /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/,
      },
      edit: false,
      message: '',
    }
  },
  emit: {
    cancelProfile: null
  },
  computed: {
    userAccount() {
      return store.state.auth.userAccount;
    }
  },
  methods: {
    ...mapActions('auth',['deToken','logoutAction','editUserAction']),
    resetMessages() {
      this.message = '';
    },
    validated() {
      this.resetMessages();
      if (!this.reg.regName.test(this.userAccount.firstname)) {
        this.message = "Firstname must not contain special characters";
      }
      else if (!this.reg.regName.test(this.userAccount.lastname)) {
        this.message = "Lastname must not contain special characters";
      }
      else if (!this.reg.regAddress.test(this.userAccount.address)) {
        this.message = "Address must not contain special characters except [.-,]";
      }
      else if (!this.reg.regPhone.test(this.userAccount.phone)) {
        this.message = "Please enter a valid phone number";
      }
      else {
        return true;
      }
      return false;
    },
    async saveUser() {
      if (this.validated()) {
        const objUser = {
          id: this.userAccount.id,
          email: this.userAccount.email,
          firstname: this.userAccount.firstname,
          lastname: this.userAccount.lastname,
          address: this.userAccount.address,
          phone: this.userAccount.phone,
          gender: this.userAccount.gender,
          role: this.userAccount.role,
        }
        this.$el.querySelector('#submit').style.cursor = 'progress';
        await this.editUserAction(objUser);
        this.$el.querySelector('#submit').style.cursor = 'pointer';
        alert('edit success!')
        this.edit = false;
      }
    },
    showEdit() {
      this.edit = !this.edit
    },
    logout() {
      this.logoutAction();
      this.deToken('logout');
      this.$router.push('/login');
    },
  }
}
</script>
<style scoped>
h2 {
  color: #fff;
  text-align: center;
  cursor: context-menu;
}
table {
  border-collapse: collapse;
  width: 90%;
  border-radius: 10px;
  background-color: #343a3f;
  color: #fff;
}
td, th {
  border: solid 1px black;
  text-align: left;
  box-sizing: border-box;
  padding-left: 6px;
}
td {
  width: 62%;
}
input, select {
  width: 98%;
  color: #fff;
  border: none;
  background-color: #343a3f;
}
th, input[readonly] {
  cursor: context-menu;
}
input:focus {
  outline: none;
}
.action {
  display: flex;
  height: 25px;
  margin: 10px 0px;
  justify-content: center;
  gap: 20px;
}
.action a{
  padding: 4px 10px;
  background-color: #fff;
}
button, .action a {
  box-sizing: border-box;
  border-radius: 5px;
  font-size: 13px;
  border: solid 1px #fff;
  width: 70px;
  text-align: center;
  cursor: pointer;
}
button:hover, a:hover {
  background-color: red;
  color: #fff;
}
</style>

