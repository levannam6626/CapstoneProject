import { createStore } from "vuex";
import auth from "./modules/auth";
import account from "./modules/account";

export default createStore({
  modules: {
    auth,
    account,
  }
});

