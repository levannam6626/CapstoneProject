import request from "..";

const ENTITY_PATH = "/api/v1/order";

class orderRequest extends request {
  async createOrder() {
    try {
      const res = await this.requestJsonAuth().post(`${ENTITY_PATH}/create`);
      return res;
    } catch (err) {
      return { err: err };
    }
  }
  async loadBillByUser() {
    try {
      const res = await this.requestJsonAuth().get(`${ENTITY_PATH}/get`);
      return res;
    } catch (err) {
      return { err: err };
    }
  }
  async loadAllBill() {
    try {
      const res = await this.requestJsonAuth().get(`${ENTITY_PATH}/all`);
      return res;
    } catch (err) {
      return { err: err };
    }
  }
}
export default new orderRequest();

