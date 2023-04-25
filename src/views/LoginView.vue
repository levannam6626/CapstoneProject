<template>
  <div class="container">
    <div class="signin">
      <div class="signin-image">
        <figure>
          <img src="../assets/signin-image.jpg" alt="">
        </figure>
        <a href="register" class="signin-image-link">Create an account</a>
      </div>
      <div>
        <form @submit.prevent="login">
          <div>
            <h1> SIGN IN </h1>
          </div>
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
          <div class="submit">
            <button type="submit">Login</button>
          </div>
        </form>
        <div class="media-btn" style="margin-top: 25px; margin-left: 8px;">
          <input class="facebook-btn" style="margin-right: 25px; border-radius: 5px;" type="button" value="Facebook">
          <input class="google-btn" style="border-radius: 5px;" type="button" value="Google">
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
library.add(faEnvelope,faLock)
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
        confirmPassword: '',
      }
    };
  },
  methods: {
    ...mapActions('auth', ['deToken']),
    ...mapActions('login', ['loginAction']),
    resetMessages() {
      this.messages.email = '';
      this.messages.password = '';
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
        await store.state.login.userAccount.then(array => {
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
<style >

html, head, body {
  margin: 0px ;
  padding: 0px;
}
.container {
  background-color: #DBDBDB;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 667px;
}
.signin-image{
  margin: 70px 40px 0px 30px;
}
.signin-image figure{
  margin: 0px;
  margin-bottom: 30px;
}
.signin-image-link {
  color: red;
}
.signin {
  padding: 30px 35px 50px 5px;
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

.signin h1 {
  font-family: 'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
}

.signin .password {
  margin-top: 20px;
}
.signin .submit {
  margin-top: 25px;
}
.signin .email {
  position: relative;
}
.signin .email .email-icon{
  position: absolute;
  top: 12px;
}
.signin .password {
  position: relative;
}
.signin .password .password-icon{
  position: absolute;
  top: 13px;
}
.signin input[type="email"], .signin input[type="password"] {
	border: none;
  border-bottom: 2px groove #D1D1D4;
  background: none;
  height: 40px;
  padding-left: 25px;
}

.signin .submit button {
  width: 240px;
  height: 40px;
  background-color: #468FAF;
  border: none;
  border-radius: 5px;
  transition: 1 s;
  color: #fff;
  font-size: 15px;
  cursor: pointer;
}
.signin .submit button:hover {
  transform: scale(1.05);
}
.signin input {
  width: 220px;
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
}

.media-btn input {
  width: 105px;
  height: 40px;

}

.facebook-btn {
  background-color: #4795FF;
  color: #fff;
  border: none;
}

.google-btn {
  background-color: #EA4336;
  color: #fff;
  border: none;
}
.signin span {
  display: block;
  width: 240px;
}
</style>

