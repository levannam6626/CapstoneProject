import request from "..";

const ENTITY_PATH = "/api/v1/payment";

class orderRequest extends request {
  async paymentByBankTransfer(totalCost) {
    try {
      const res = await this.requestFormData().get(`${ENTITY_PATH}/create-payment`,{params:{TotalCost: totalCost} });
      return res;
    } catch (err) {
      return { err: err };
    }
  }
}
export default new orderRequest();

