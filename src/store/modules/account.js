import accountRequest from "@/factories/modules/accountRequest";

const initialStateAccount = () => ({
  userList:[],
  loginForm: {
    email: '',
    password: '',
  },
  registerStatus: false,
  deleteMessage: '',
})

export default {
  namespaced: true,
  state: initialStateAccount,
  actions: {
    resetLoginDataAction(content) {
      content.commit('resetLoginDataMutation');
    },
    async registerAction(content, objRegister) {
      let status = accountRequest.register(objRegister);
      await status.then(result => {
        if(result === true){
          content.commit('setLoginFormMutation', objRegister);
        }else {
          content.commit('setRegisterStatusMutation', result);
        }
      });
    },
    async getUserListAction(content, id) {
      let userRes = await accountRequest.getUsersSearch(id);
      if(userRes.status === 200) {
        await content.commit('getUserListMutation',userRes.data);
      }
    },
    async deleteAccountsAction(content, accounts) {
      let res = await accountRequest.deleteAccount(accounts);
      if(res.status === 200) {
        content.commit('deleteAccountMutation', "Delete Success");
      }
    }
  },
  mutations: {
    setLoginFormMutation(state, objRegister) {
      state.loginForm.email = objRegister.email;
      state.loginForm.password = objRegister.password;
      state.registerStatus = true;
    },
    setRegisterStatusMutation(state, status) {
      state.registerStatus = status;
    },
    resetLoginDataMutation(state) {
      state.loginForm.email = '';
      state.loginForm.password = '';
      state.registerStatus = false;
    },
    getUserListMutation(state, users) {
      state.userList = users;
    },
    deleteAccountMutation(state, deleteMessage){
      state.deleteMessage = deleteMessage;
    },
  },
};

