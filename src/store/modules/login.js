import loginRequest from "@/factories/modules/loginRequest";

export default {
  namespaced: true,
  state: {
    userAccount: []
  },
  actions: {
    loginAction(content, data) {
      let account = loginRequest.login(data);
      content.commit('loginAction', account);
    },
  },
  mutations: {
    loginAction(state, account) {
      state.userAccount = account;
    },
  },
};

