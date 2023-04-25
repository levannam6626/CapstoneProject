<template>
  <div class="container">
    <div class="signup">
      <form @submit.prevent="register">
        <div>
          <h1> SIGN UP </h1>
        </div>
        <div class="email">
          <font-awesome-icon class="email-icon" icon="fa-solid fa-envelope" />
          <input type="email" placeholder="Your Email" v-model="email">
        </div>
        <span class="message">{{ this.messages.email }}</span>
        <div class="name">
          <font-awesome-icon class="name-icon" icon="fa-solid fa-user" />
          <input type="text" placeholder="Your Name" v-model="name">
        </div>
        <span class="message">{{ this.messages.name }}</span>
        <div class="address">
          <font-awesome-icon class="address-icon" icon="fa-solid fa-location-dot" />
          <input type="text" placeholder="Your Address" v-model="address">
        </div>
        <span class="message">{{ this.messages.address }}</span>
        <div class="phone">
          <font-awesome-icon  class="phone-icon" icon="fa-solid fa-phone" />
          <input type="text" placeholder="Your Phone" v-model="phone">
        </div>
        <span class="message">{{ this.messages.phone }}</span>
        <div class="password" >
          <font-awesome-icon class="password-icon" icon="fa-solid fa-lock" />
          <input type="password" placeholder="Password" v-model="password">
        </div>
        <span class="message">{{ this.messages.password }}</span>
        <div class="confirm-password" >
          <font-awesome-icon class="confirm-password-icon" icon="fa-solid fa-lock" />
          <input type="password" placeholder="Repeat your password" v-model="confirmPassword">
        </div>
        <span class="message">{{ this.messages.confirmPassword }}</span>
        <div class="submit">
          <button type="submit">Register</button>
        </div>
      </form>
      <div class="signup-image">
        <figure>
          <img src="../assets/signup-image.png" alt="">
        </figure>
        <a href="login" class="signup-image-link">I am already member</a>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions } from 'vuex';
import store from '@/store';
import { library } from '@fortawesome/fontawesome-svg-core'
import { faEnvelope, faUser, faLock, faPhone, faLocationDot } from '@fortawesome/free-solid-svg-icons'
library.add(faEnvelope, faUser, faLock, faPhone, faLocationDot)

export default {
  data() {
    return {
      email: '',
      name: '',
      address: '',
      phone: '',
      password: '',
      confirmPassword: '',
      regEmail: /^\w+@[a-zA-Z]{3,}\.com$/,
      regName: /^[A-Za-z][A-Za-z0-9_ ]{2,50}$/,
      regAddress: /^[A-Za-z0-9 ,.-]{2,200}$/,
      regPhone: /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/,
      regPassword: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[#$@!%&*?])[A-Za-z\d#$@!%&*?]{8,30}$/,
      isLogin: false,
      deTokenData: '',
      messages: {
        email: '',
        name: '',
        address: '',
        phone: '',
        password: '',
        confirmPassword: '',
      },
    };
  },
  methods: {
    ...mapActions('register', ['registerAction']),
    resetMessages() {
      this.messages.email = '';
      this.messages.name = '';
      this.messages.address = '';
      this.messages.phone = '';
      this.messages.password = '';
      this.messages.confirmPassword = '';
    },
    validated() {
      this.resetMessages();
      if (!this.regEmail.test(this.email)) {
        this.messages.email = "Please enter a valid email address";
      }
      else if (!this.regName.test(this.name)) {
        this.messages.name = "Name length must be between 2 and 50 and contain no special characters";
      }
      else if (!this.regAddress.test(this.address)) {
        this.messages.address = "Address length must be between 2 and 200";
      }
      else if (!this.regPhone.test(this.phone)) {
        this.messages.phone = "Please enter a valid phone number";
      }
      else if (!this.regPassword.test(this.password)) {
        this.messages.password = "Password must contain one digit from 1 to 9, one lowercase letter, one uppercase letter, one special character, no space, and it must be 8-16 characters long.";
      }
      else if (this.confirmPassword !== this.password) {
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
          email: this.email,
          name: this.name,
          address: this.address,
          phone: this.phone,
          password: this.password,
        };
        await this.registerAction(objRegister);
        await store.state.register.userLogin.then(array => {
          if(array.status === 200){
            this.isLogin = true;
            this.deTokenData = array.data;
          }
        });
        if (this.isLogin === true) {
          this.deToken(this.deTokenData);
          this.$router.push("/");
          alert("Login success !!!");
        }
        else {
          alert("Login failed !!!");
        }
      }
    },
  },
};
</script>
<style scoped>
.container {
  background-color: #DBDBDB;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 667px;
}
span {
  width: 248px;
}
.signup {
  padding: 30px 50px 50px 50px;
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
}

.signup h1 {
  font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
}

.name, .address, .phone, .password, .confirm-password, .submit {
  margin-top: 16px;
}

input[type="text"], input[type="email"], input[type="password"] {
  height: 33px;
}
.submit button {
  width: 248px;
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
  width: 235px;
  padding-left: 22px;
  font-size: 15px;
}
input:active,
input:focus,
input:hover {
  outline: none;
	border-bottom-color: #468FAF;
}
.signup-image{
  margin: 90px 20px 0px 60px;
}
.signup-image figure{
  margin: 0px;
  margin-bottom: 30px;
}
.signup-image-link {
  color: red;
}
.message {
  color: red;
  font-size: 10px;
}
.email, .name, .address, .phone, .password, .confirm-password {
  position: relative;
}
.email-icon, .name-icon, .address-icon, .phone-icon, .password-icon, .confirm-password-icon {
  position: absolute;
  top: 9px;
}
</style>

