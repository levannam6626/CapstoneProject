import { createStore } from "vuex";
import auth from "./modules/auth";
import login from "./modules/login";
import register from "./modules/register";

export default createStore({
  modules: {
    auth,
    login,
    register,
  }
});

