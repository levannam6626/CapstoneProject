<template>
  <div class="signup">
    <form @submit.prevent="register">
      <div>
        <h1> SIGN UP </h1>
      </div>
      <div class="email">
        <font-awesome-icon class="email-icon" icon="fa-solid fa-envelope" />
        <input type="email" placeholder="Your Email" v-model="this.account.email" maxlength="50" required>
      </div>
      <span class="message">{{ this.messages.email }}</span>
      <div class="first-name">
        <font-awesome-icon class="first-name-icon" icon="fa-solid fa-user" />
        <input type="text" placeholder="First Name" v-model="this.account.firstName" maxlength="20" required>
      </div>
      <span class="message">{{ this.messages.firstName }}</span>
      <div class="last-name">
        <font-awesome-icon class="last-name-icon" icon="fa-solid fa-user" />
        <input type="text" placeholder="Last Name" v-model="this.account.lastName" maxlength="20" required>
      </div>
      <span class="message">{{ this.messages.lastName }}</span>
      <div class="address">
        <font-awesome-icon class="address-icon" icon="fa-solid fa-location-dot" />
        <input type="text" placeholder="Your Address" v-model="this.account.address" maxlength="150" required>
      </div>
      <span class="message">{{ this.messages.address }}</span>
      <div class="phone">
        <font-awesome-icon class="phone-icon" icon="fa-solid fa-phone" />
        <input type="text" placeholder="Your Phone" v-model="this.account.phone" minlength="10" maxlength="13" required>
      </div>
      <span class="message">{{ this.messages.phone }}</span>
      <div class="gender" >
        <select name="gender" id="gender" v-model="this.account.gender">
          <option value="Male">MALE</option>
          <option value="Female">FEMALE</option>
        </select>
      </div>
      <div class="role" v-if="this.logedinRole === 'ADMIN'">
        <select name="role" id="role" v-model="this.account.role">
          <option value="CUSTOMER">CUSTOMER</option>
          <option value="SELLER">SELLER</option>
        </select>
      </div>
      <div class="password">
        <font-awesome-icon class="password-icon" icon="fa-solid fa-lock" />
        <input type="password" placeholder="Password" v-model="this.account.password" minlength="8" maxlength="30" required>
      </div>
      <span class="message">{{ this.messages.password }}</span>
      <div class="confirm-password">
        <font-awesome-icon class="confirm-password-icon" icon="fa-solid fa-lock" />
        <input type="password" placeholder="Repeat your password" v-model="this.account.confirmPassword">
      </div>
      <span class="message">{{ this.messages.confirmPassword }}</span>
      <div class="submit">
        <button type="submit" id="submit">Register</button>
      </div>
      <a href="login" class="image-link" v-if="this.logedinRole !== 'ADMIN'">I am already member</a>
    </form>
    <div class="signup-image">
      <figure>
        <img src="../assets/signup-image.png" alt="">
      </figure>
      <a href="login" class="signup-image-link" v-if="this.logedinRole !== 'ADMIN'">I am already member</a>
    </div>
    <button v-if="this.logedinRole === 'ADMIN'" @click="$emit('closeForm',true)">X</button>
    <div class="alert" v-show="this.alertSuccess" v-if="this.logedinRole !== 'ADMIN'">
      <SuccessAlert :message="'User account has been created successfully'" @closeAlert="closeAlert"/>
    </div>
  </div>
</template>
<script>
import { mapActions } from 'vuex';
import store from '@/store';
import { library } from '@fortawesome/fontawesome-svg-core';
import { faEnvelope, faUser, faLock, faPhone, faLocationDot } from '@fortawesome/free-solid-svg-icons';
library.add(faEnvelope, faUser, faLock, faPhone, faLocationDot);

import SuccessAlert from '@/components/SuccessAlert.vue';

export default {
  props: {
    logedinRole: {
      type: String,
      default: "CUSTOMER"
    }
  },
  data() {
    return {
      account: {
        email: '',
        firstName: '',
        lastName: '',
        address: '',
        role: 'CUSTOMER',
        gender: 'Male',
        phone: '',
        password: '',
        confirmPassword: '',
      },
      reg: {
        regEmail: /^\w+@[a-zA-Z]{3,}\.com$/,
        regName: /^[A-Za-z][A-Za-z0-9_ -]{0,20}$/,
        regAddress: /^[A-Za-z0-9 ,.-]{1,150}$/,
        regPhone: /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/,
        regPassword: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[#$@!%&*?])[A-Za-z\d#$@!%&*?]{8,30}$/,
      },
      isRegister: false,
      deTokenData: '',
      messages: {
        email: '',
        firstName: '',
        lastName: '',
        address: '',
        phone: '',
        password: '',
        confirmPassword: '',
      },
      alertSuccess: false,
      alertFailed: true
    };
  },
  components: {
    SuccessAlert
  },
  mounted: function () {
    this.resetLoginData();
  },
  methods: {
    ...mapActions('account', ['registerAction','resetLoginDataAction']),
    resetLoginData() {
      this.resetLoginDataAction();
    },
    resetRegisterData() {
      this.account.email = '';
      this.account.firstName = '';
      this.account.lastName = '';
      this.account.address = '';
      this.account.role = 'CUSTOMER';
      this.account.gender = 'Male';
      this.account.phone = '';
      this.account.password = '';
      this.account.confirmPassword = '';

    },
    resetMessages() {
      this.messages.email = '';
      this.messages.firstName = '';
      this.messages.lastName = '';
      this.messages.address = '';
      this.messages.phone = '';
      this.messages.password = '';
      this.messages.confirmPassword = '';
    },
    validated() {
      this.resetMessages();
      if (!this.reg.regEmail.test(this.account.email)) {
        this.messages.email = "Please enter a valid email address";
      }
      else if (!this.reg.regName.test(this.account.firstName)) {
        this.messages.firstName = "Firstname must not contain special characters";
      }
      else if (!this.reg.regName.test(this.account.lastName)) {
        this.messages.lastName = "Lastname must not contain special characters";
      }
      else if (!this.reg.regAddress.test(this.account.address)) {
        this.messages.address = "Address must not contain special characters except [.-,]";
      }
      else if (!this.reg.regPhone.test(this.account.phone)) {
        this.messages.phone = "Please enter a valid phone number";
      }
      else if (!this.reg.regPassword.test(this.account.password)) {
        this.messages.password = "Password must contain one digit from 1 to 9, one lowercase letter, one uppercase letter, one special character, no space, and it must be 8-30 characters long.";
      }
      else if (this.account.confirmPassword !== this.account.password) {
        this.messages.confirmPassword = "Confirm password and password does not match";
      }
      else {
        return true;
      }
      return false;
    },
    async register() {
      if (this.validated()) {
        const objRegister = {
          email: this.account.email,
          firstname: this.account.firstName,
          lastname: this.account.lastName,
          address: this.account.address,
          phone: this.account.phone,
          password: this.account.password,
          confirmPassword: this.account.confirmPassword,
          gender: this.account.gender,
          role: this.account.role,
        };
        this.$el.querySelector('#submit').style.cursor = 'progress';
        await this.registerAction(objRegister);
        this.$el.querySelector('#submit').style.cursor = 'pointer';
        this.isRegister = store.state.account.registerStatus;
        if(this.isRegister === true) {
          if(this.logedinRole === "ADMIN")
          {
            this.resetLoginData();
            this.resetRegisterData();
            this.$emit('closeForm', true);
            this.$emit('alertSuccess', true);
          }else{
            this.alertSuccess = true;
            this.$router.push("login");
          }
        }
        else {this.messages.email = 'Email has been registered';
        }
      }
    },
    closeAlert() {
      this.alertSuccess = false;
      this.alertFailed = false;
    }
  },
};
</script>
<style scoped>
span {
  width: 248px;
}

.signup {
  background-color: #fff;
  text-align: center;
  box-shadow: rgba(0, 0, 0, 0.07) 0px 1px 2px,
    rgba(0, 0, 0, 0.07) 0px 2px 4px,
    rgba(0, 0, 0, 0.07) 0px 4px 8px,
    rgba(0, 0, 0, 0.07) 0px 8px 16px,
    rgba(0, 0, 0, 0.07) 0px 16px 32px,
    rgba(0, 0, 0, 0.07) 0px 32px 64px;
  border-radius: 6px;
  display: flex;
  width: 60%;
  max-width: 750px;
  min-width: 250px;
}

.signup h1 {
  font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
  width: 100%;
}
.signup form {
  width: 45%;
  box-sizing: border-box;
  padding: 0px 0px 40px 40px;
  min-width: 250px;
}
.first-name,
.last-name,
.address,
.phone,
.password,
.confirm-password,
.submit {
  margin-top: 10px;
}

input[type="text"],
input[type="email"],
input[type="password"] {
  height: 33px;
  box-sizing: border-box;
}
.submit button {
  width: 90%;
  height: 33px;
  background-color: #468FAF;
  border: none;
  border-radius: 5px;
  transition: 1 s;
  color: #fff;
  font-size: 15px;
  cursor: pointer;
}
.submit button:hover {
  transform: scale(1.05);
}
input {
  border: none;
  border-bottom: 2px groove #D1D1D4;
  background: none;
  width: 100%;
  padding-left: 22px;
  font-size: 15px;
}
input:active,
input:focus,
input:hover {
  outline: none;
  border-bottom-color: #468FAF;
}
.signup-image {
 width: 50%;
 box-sizing: border-box;
 padding-top: 120px;
 display: grid;
}
.signup-image figure {
  box-sizing: border-box;
  width: 90%;
  margin: 0px;
  padding-left: 30px;
}
.signup-image figure img {
  width: 100%;
}
.image-link, .signup-image-link {
  color: red;
}
.signup-image-link {
  height: 20px;
}
.image-link {
  display: none;
}
.message {
  color: red;
  font-size: 10px;
}
.email,
.first-name,
.last-name,
.address,
.phone,
.password,
.confirm-password {
  position: relative;
}
.email-icon,
.first-name-icon,
.last-name-icon,
.address-icon,
.phone-icon,
.password-icon,
.confirm-password-icon {
  position: absolute;
  top: 9px;
}
select {
  width: 100%;
  height: 28px;
  margin-top: 10px;
  text-align: left;
  box-sizing: border-box;
  padding-left: 20px;
  border: 0px;
  border-bottom: 2px groove #D1D1D4;
}
select:hover, select:active, select:focus {
  border-bottom-color: #468FAF;
}
select:focus {
  outline: 0px;
}
.signup>button {
  height: 30px;
  border: 0px;
  background-color: #fff;
  color: red;
  font-size: 17px;
  margin: 2px;
}
.signup>button:hover {
  font-weight: bold;
}
.alert {
  position: absolute;
  z-index: 5;
  width: 100%;
  height: 100%;
  background-color:  rgba(0, 0, 0, 0.07);
}
@media screen and (max-width: 760px) {
  .signup-image {
    display: none;
  }
  .signup {
    width: 40%;
  }
  .signup form {
    width: 100%;
    padding: 0px 30px 50px 30px;
  }
  .image-link {
    display: block;
  }
}
@media screen and (max-width: 860px) {
  .signup-image {
    padding-right: 10px;
  }
}
</style>
  
  