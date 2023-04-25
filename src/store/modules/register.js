import registerRequest from "@/factories/modules/registerRequest";

export default {
  namespaced: true,
  state: {
    userLogin: {
        email: '',
        password: '',
    }
  },
  actions: {
    registerAction(content, objRegister) {
      let status = registerRequest.register(objRegister);
      if(status){
        content.commit('registerAction', objRegister);
      }
    },
  },
  mutations: {
    registerAction(state, login) {
      state.userLogin.email = login.email;
      state.userLogin.password = login.password;
    },
  },
};

