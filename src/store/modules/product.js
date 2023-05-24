//import productRequest from "@/factories/modules/productRequest";

export default {
  namespaced: true,
  state: {
    products:[],
    categories:[{
      id: '1',
      categoryName: 'RASPBERRY',
    },
    {
      id: '2',
      categoryName: 'ARDUINO',
    },
    {
      id: '3',
      categoryName: 'LED',
    },
    {
      id: '4',
      categoryName: ' LCD SCREEN',
    }],
    categoryId: 'all',
  },
  actions: {
    loadProductListAction(content, id) {
      console.log(id);
      //productRequest.loadProductList(id);
    },
    changeCategory(content, id) {
      content.commit('changeCategory', id);
    }
  },
  mutations: {
    loadProductListAction(state, products) {
      state.products = products;
    },
    changeCategory(state, id) {
      console.log(state.categoryId);
      console.log(id);
      state.categoryId = id;
    }
  },
};
