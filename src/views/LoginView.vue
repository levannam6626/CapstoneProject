<template>
  <div class="container">
    <div class="content">
      <h1> SIGN IN </h1>
      <div class="signin">
        <div class="signin-image">
          <figure>
            <img src="../assets/signin-image.jpg" alt="">
          </figure>
          <a href="register" class="signin-image-link">Create an account</a>
        </div>
        <div class="form-login">
          <form @submit.prevent="login">
            <div class="email">
              <font-awesome-icon class="email-icon" icon="fa-solid fa-envelope" />
              <input type="email" placeholder="Email" v-model="email">
            </div>
            <span class="message">{{ this.messages.email }}</span>
            <div class="password" >
              <font-awesome-icon class="password-icon" icon="fa-solid fa-lock" />
              <input type="password" placeholder="Password" v-model="password">
            </div>
            <span class="message">{{ this.messages.password }}</span>
            <span class="message">{{ this.messages.login }}</span>
            <div class="submit">
              <button type="submit">Login</button>
            </div>
          </form>
          <div class="media-btn">
            <button class="facebook-btn" ><font-awesome-icon class="icon" icon="fa-brands fa-facebook" /></button>
            <button class="google-btn" ><font-awesome-icon class="icon" icon="fa-brands fa-google" /></button>
          </div>
          <a href="register">Create an account</a>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapActions } from 'vuex';
import store from '@/store';
import { library } from '@fortawesome/fontawesome-svg-core'
import { faEnvelope, faLock } from '@fortawesome/free-solid-svg-icons'
import { faFacebook, faGoogle } from '@fortawesome/free-brands-svg-icons'
library.add(faEnvelope, faLock, faFacebook, faGoogle)

export default {
  data() {
    return {
      email: '',
      password: '',
      regEmail: /^\w+@[a-zA-Z]{3,}\.com$/,
      regPassword: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[#$@!%&*?])[A-Za-z\d#$@!%&*?]{8,30}$/,
      isLogin : false,
      deTokenData: "",
      messages: {
        email: '',
        password: '',
        login: ''
      }
    };
  },
  mounted: function () {
    this.loadFormData();
  },
  methods: {
    ...mapActions('auth', ['deToken','loginAction','getUser']),

    loadFormData() {
      this.email = store.state.account.loginForm.email;
      this.password = store.state.account.loginForm.password;
    },
    resetMessages() {
      this.messages.email = '';
      this.messages.password = '';
      this.messages.login = '';
    },
    validated() {
      this.resetMessages();
      if (!this.regEmail.test(this.email)) {
        this.messages.email = "Please enter a valid email address";
      }
      else if (!this.regPassword.test(this.password)) {
        this.messages.password = "Password must contain one digit from 1 to 9, one lowercase letter, one uppercase letter, one special character, no space, and it must be 8-16 characters long.";
      }
      else {
        return true;
      }
      return false;
    },
    async login() {
      if (this.validated()) {
        const objLogin = {
          email: this.email,
          password: this.password,
        };
        await this.loginAction(objLogin);
        if (store.state.auth.loginStatus === true) {
          await this.deToken(store.state.auth.loginData.accessToken);
          await this.getUser(store.state.auth.loginData.id);
          if (store.state.auth.userAccount.role === "ADMIN") {
            this.$router.push("admin");
          }
          else {
            this.$router.push("/");
          }
        }
        else {
          this.messages.login = 'Incorrect email or password !!!';
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
  height: 100%;
}
.content {
  display: grid;
  box-sizing: border-box;
  padding-bottom: 50px;
  background-color: #fff;
  text-align: center;
  box-shadow: rgba(0, 0, 0, 0.07) 0px 1px 2px, 
  rgba(0, 0, 0, 0.07) 0px 2px 4px,
  rgba(0, 0, 0, 0.07) 0px 4px 8px,
  rgba(0, 0, 0, 0.07) 0px 8px 16px,
  rgba(0, 0, 0, 0.07) 0px 16px 32px,
  rgba(0, 0, 0, 0.07) 0px 32px 64px;
  border-radius: 6px;
  width: 65%;
  max-width: 600px;
  min-width: 270px;
}
.content h1 {
  font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
  width: 100%;
}
.signin {
  display: flex;
  justify-content: center;
  width: 100%;
}
.signin-image{
  margin: 20px 0px 0px 30px;
  box-sizing: border-box;
  width: 50%;
}
.signin-image figure{
  margin: 0px;
  margin-bottom: 30px;
  width: 90%;
}
.signin-image figure img{
  width: 100%;
}
a {
  color: red;
}
.form-login {
  display: grid;
  gap: 20px;
  box-sizing: border-box;
  padding: 0px 20px;
}
.email, .password  {
  position: relative;
  width: 100%;
}
.password {
  margin-top: 20px;
}
.submit {
  margin-top: 25px;
  width: 100%;
}
.email-icon{
  position: absolute;
  top: 12px;
}
.password .password-icon{
  position: absolute;
  top: 13px;
}
input[type="email"], input[type="password"] {
	border: none;
  border-bottom: 2px groove #D1D1D4;
  background: none;
  height: 40px;
  padding-left: 25px;
  width: 85%;
}

.submit button {
  width: 85%;
  height: 40px;
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
.signin input {
  font-size: 15px;
}
.signin input[type="email"]:active, .signin input[type="password"]:active,
.signin input[type="email"]:focus, .signin input[type="password"]:focus,
.signin input[type="email"]:hover, .signin input[type="password"]:hover {
	outline: none;
	border-bottom-color: #468FAF;
}
.signin .message {
  color: red;
  font-size: 10px;
}
.media-btn {
  display: flex;
  width: 100%;
  gap: 20px;
}

.media-btn button {
  width: 45%;
  height: 40px;
  font-size: 22px;
}

.facebook-btn, .google-btn {
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
.facebook-btn {
  background-color: #4795FF;
}

.google-btn {
  background-color: #EA4336;
}
.signin span {
  display: block;
  width: 240px;
}
.form-login > a {
    display: none;
  }
@media screen and (max-width: 680px) {
  .signin-image {
    display: none;
  }
  .form-login {
    width: 95%;
  }
  .form-login > a {
    display: block;
  }
}
@media screen and (max-width: 430px) {
  .form-login {
    width: 100%;
  }
  .form-login > a {
    display: block;
  }
}
</style>

