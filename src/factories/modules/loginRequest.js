import request from "..";

const ENTITY_PATH = "/api/auth/";

class loginRequest extends request {
  async login(obj) {
    try {
      const res = await this.requestLogin().post(`${ENTITY_PATH}signin`, { ...obj });
      return res;
    } catch (err) {
      if (err.response.status === 401) {
        localStorage.clear();
        this.$router.push("/login");
      }
      return { err: err };
    }
  }
}
export default new loginRequest();

