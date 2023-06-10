import categoryRequest from "@/factories/modules/categoryRequest";

export default {
  namespaced: true,
  state: {
    category: {},
  },
  actions: {
    async getCategoryByIdAction(content, id) {
      let data = categoryRequest.loadCategories(id);
      await data.then((array) => {
        if (array.status === 200) {
          content.commit("getCategoryByIdMutation", array.data);
        }
      });
    },
  },
  mutations: {
    getCategoryByIdMutation(state, data) {
      state.category = data;
    },
  },
};

