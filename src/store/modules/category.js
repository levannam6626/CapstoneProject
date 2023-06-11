import categoryRequest from "@/factories/modules/categoryRequest";

export default {
  namespaced: true,
  state: {
    categories:[],
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
    async addCategoryAction(content, objCategory) {
      await categoryRequest.createCategory(objCategory);
      await content.dispatch('loadCategoriesAction','all');
    },
    async loadCategoriesAction(content, id) {
      let data = categoryRequest.loadCategories(id);
      await data.then(array => {
        if(array.status === 200){
          content.commit('loadCategoriesMutation', array.data);
        }
      });
    },
  },
  mutations: {
    getCategoryByIdMutation(state, data) {
      state.category = data;
    },
    loadCategoriesMutation(state, data) {
      state.categories = data;
    },
  },
};

