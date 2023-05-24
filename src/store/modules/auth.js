export default {
  namespaced: true,
  state: {
    token: localStorage.getItem('token')
  },
  actions: {
    deToken(content, data) {
      content.commit('deToken', data);
    },
    clearStore(content) {
      content.commit('clearStore');
    }
  },
  mutations: {
    deToken(state, data) {
      localStorage.setItem('token', data);
      state.token = localStorage.getItem('token')
    },
    clearStore(state) {
      localStorage.clear();
      state.token = null;
    },
  },
};

