import accountRequest from "@/factories/modules/accountRequest";

export default {
  namespaced: true,
  state: {
    userAccount: {},
    loginForm: {
      email: '',
      password: '',
    },
    loginData:[],
    loginStatus: false,
    registerStatus: false,
  },
  actions: {
    async loginAction(content, data) {
      let dataRes = accountRequest.login(data);
      await dataRes.then(array => {
        if(array.status === 200){
          content.commit('loginMutation', array.data);
        }
      });
    },
    logoutAction(content) {
      content.commit('logoutMutation');
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
    async getUser(content,id) {
      let userRes = await accountRequest.getUserByID(id);
      await content.commit('getUser',userRes.data);
    }
  },
  mutations: {
    loginMutation(state, loginData) {
      state.loginData = loginData;
      state.loginStatus = true;
    },
    logoutMutation(state) {
      state.userAccount = {};
      state.loginData = [];
      state.loginStatus = false;
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
    getUser(state, user) {
      state.userAccount = user;
    }
  },
};

