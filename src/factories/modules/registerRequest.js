import request from "..";

const ENTITY_PATH = "/api/auth/";

class loginRequest extends request {
  async register(objRegister) {
    try {
      const res = await this.requestRegister().post(`${ENTITY_PATH}register`, { ...objRegister });
      return res;
    } catch (err) {
      if (err.response.status === 401) {
        localStorage.clear();
        this.$router.push("/register");
      }
      return { err: err };
    }
  }
}
export default new loginRequest();

