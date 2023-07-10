import cartRequest from "@/factories/modules/cartRequest";
import store from "..";
export default {
  namespaced: true,
  state: {
    cartList: [],
  },
  actions: {
    async addProductToCartAction(content, objOrder) {
      await cartRequest.addProductToCart(objOrder);
      content.dispatch('getCartAction');
    },
		async getCartAction(content) {
			let res = cartRequest.getCart();
      await res.then((array) => {
        if(array.status === 200) {
          content.commit('getCartMutation', array.data);
        }else if(array.err.response.status === 403) {
          store.dispatch('auth/logoutAction')
        }
      })
		},
    async changeSelectedAction(content, cartItem) {
      await cartRequest.changeSelected(cartItem);
    },
    async changeSelectedAllAction(content, cartItems) {
      await cartRequest.changeSelectedAllAction(cartItems);
    },
    async delCartItemAction(content, cartItem) {
      await cartRequest.delCartItem(cartItem);
      await content.dispatch('getCartAction');
    }
  },
  mutations: {
		getCartMutation(state, cart) {
			state.cartList = cart;
		}
  },
};
