import accountRequest from "@/factories/modules/accountRequest";

export default {
  namespaced: true,
  state: {
    userAccount: [],
    loginForm: {
      email: '',
      password: '',
  },
    registerStatus: false,
  },
  actions: {
    loginAction(content, data) {
      let account = accountRequest.login(data);
      content.commit('loginAction', account);
    },
    resetLoginDataAction(content) {
      content.commit('resetLoginDataAction');
    },
    registerAction(content, objRegister) {
      let status = accountRequest.register(objRegister);
      if(status){
        content.commit('registerAction', objRegister);
      }
    },
  },
  mutations: {
    loginAction(state, account) {
      state.userAccount = account;
    },
    resetLoginDataAction(state) {
      state.loginForm.email = '';
      state.loginForm.password = '';
    },
    registerAction(state, objRegister) {
      state.loginForm.email = objRegister.email;
      state.loginForm.password = objRegister.password;
      state.registerStatus = true;
    },
  },
};

