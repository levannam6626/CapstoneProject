import { createStore } from "vuex";
import createPersistedState from "vuex-plugin-persistedstate";

import auth from "./modules/auth";
import account from "./modules/account";
import product from "./modules/product";
import category from "./modules/category";

export default createStore({
  modules: {
    auth,
    account,
    product,
    category,
  },
  plugins: [createPersistedState({
    key: 'vuex',              
    paths:['auth','product'],
  })],
});

