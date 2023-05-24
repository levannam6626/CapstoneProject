import { createStore } from "vuex";
import auth from "./modules/auth";
import account from "./modules/account";
import product from "./modules/product";

export default createStore({
  modules: {
    auth,
    account,
    product,
  }
});

