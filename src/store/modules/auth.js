import accountRequest from "@/factories/modules/accountRequest";

import jwtDecode from 'jwt-decode';

const initialStateAuth = () => ({
  token: null,
  userAccount: {},
  loginData:[],
  loginStatus: false,
})

export default {
  namespaced: true,
  state: initialStateAuth,
  actions: {
    async loginAction(content, data) {
      let dataRes = accountRequest.login(data);
      await dataRes.then(array => {
        if(array.status === 200){
          content.commit('loginMutation', array.data);
        }
      });
    },
    async getUser(content, id) {
      let userRes = await accountRequest.getUserByID(id);
      await content.commit('getUserMutation',userRes.data);
    },
    async editUserAction(content, objUser) {
      let res = await accountRequest.editUser(objUser);
      if(res.status === 200) {
        content.commit('getUserMutation', objUser);
      }
    },
    logoutAction(content)
    {
      content.commit('loginMutation','logout');
      content.commit('getUserMutation','logout');
    },
    async checkAccessToken({content, state}) {
      console.log(state.loginData.refreshToken)
      if (state.loginData.accessToken && jwtDecode(state.loginData.accessToken).exp < Date.now() / 1000) {
        let res = await accountRequest.refreshToken(state.loginData.refreshToken);
        console.log(res)
        content.commit('loginMutation', res.data)
      }
    },
    deToken(content, data) {
      content.commit('deToken', data);
    },
    clearStore(content) {
      content.commit('clearStore');
    }
  },
  mutations: {
    loginMutation(state, loginData) {
      if(loginData === 'logout') {
        const initial = initialStateAuth();
        Object.keys(initial).forEach(key => { state[key] = initial[key] });
      }else {
        state.loginData = loginData;
        state.loginStatus = true;
      }
    },
    getUserMutation(state, user) {
      if(user === 'logout') {
        const initial = initialStateAuth();
        Object.keys(initial).forEach(key => { state[key] = initial[key] });
      }else {
        state.userAccount = user;
      }
    },
    deToken(state, data) {
      if(data === 'logout') {
        state.token = null;
      }else {
        state.token = data;
      }
    },
    clearStore(state) {
      state.token = null;
    },
  },
};

