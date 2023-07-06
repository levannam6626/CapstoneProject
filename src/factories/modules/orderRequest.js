import request from "..";

const ENTITY_PATH = "/api/v1/order";

class orderRequest extends request {
  async createOrder(userInfor) {
    try {
      const res = await this.requestFormData().post(`${ENTITY_PATH}/add`, userInfor);
      return res;
    } catch (err) {
      return { err: err };
    }
  }
  async loadBillByUser() {
    try {
      const res = await this.requestJsonAuth().get(`${ENTITY_PATH}/view/getByUser`);
      return res;
    } catch (err) {
      return { err: err };
    }
  }
  async loadAllBill() {
    try {
      const res = await this.requestJsonAuth().get(`${ENTITY_PATH}/view/all`);
      return res;
    } catch (err) {
      return { err: err };
    }
  }
  async changeDeliveryStatus(orderUpdateData) {
    try {
      const res = await this.requestFormData().post(`${ENTITY_PATH}/edit`, orderUpdateData);
      return res;
    } catch (err) {
      return { err: err };
    }
  }
}
export default new orderRequest();

