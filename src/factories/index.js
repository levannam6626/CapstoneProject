import axios from "axios";
import store from "../store";

const url = "http://localhost:8082";

export default class index {
  constructor() {}
  requestLogin() {
    var header = {
      "Content-Type": "application/json",
    };
    if (store.state.auth.token) {
      header.Authorization = `Bearer ${store.state.auth.token}`;
    }
    const instance = axios.create({
      baseURL: url,
      headers: header,
    });
    return instance;
  }
  requestRegister() {
    var header = {
      "Content-Type": "multipart/form-data",
    };
    const instance = axios.create({
      baseURL: url,
      headers: header,
    });
    return instance;
  }
  requestProduct() {
    var header = {
      "Content-Type": "application/json",
    };
    if (store.state.auth.token) {
      header.Authorization = `Bearer ${store.state.auth.token}`;
    }
    const instance = axios.create({
      baseURL: url,
      headers: header,
    });
    return instance;
  }
  requestFormData() {
    var header = {
      "Content-Type": "multipart/form-data",
    };
    if (store.state.auth.token) {
      header.Authorization = `Bearer ${store.state.auth.token}`;
    }
    const instance = axios.create({
      baseURL: url,
      headers: header,
    });
    return instance;
  }
  requestCategory() {
    var header = {
      "Content-Type": "multipart/form-data",
    };
    const instance = axios.create({
      baseURL: url,
      headers: header,
    });
    return instance;
  }
}

