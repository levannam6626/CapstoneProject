import orderRequest from "@/factories/modules/orderRequest";
export default {
  namespaced: true,
  state: {
    productOrders: [],
    message: {
      payment: null,
    },
  },
  actions: {
    async paymentAction(content, userInfor) {
      let res = await orderRequest.createOrder(userInfor);
      if(res.status === 200) {
        content.commit('paymentMutation', res.data);
      }
    },
    async loadBillByUserAction(content) {
      let res = await orderRequest.loadBillByUser();
      if(res.status === 200) {
        content.commit('setBillMutation', res.data);
      }
    },
    async loadAllBillAction(content) {
      let res = await orderRequest.loadAllBill();
      if(res.status === 200) {
        content.commit('setBillMutation', res.data);
      }
    },
    async changeDeliveryStatusAction(content, orderUpdateData) {
      await orderRequest.changeDeliveryStatus(orderUpdateData);
      content.dispatch('loadAllBillAction');
    }
  },
  mutations: {
    paymentMutation(state, data) {
      state.message.payment = data;
    },
    setBillMutation(state, data) {
      state.productOrders = data;
    }
  },
};
