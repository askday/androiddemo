import Axios from "axios";

function HttpInit() {
  Axios.defaults.baseURL = 'http://localhost:3005/'
  Axios.defaults.timeout = 1000
  Axios.interceptors.request.use(function (config) {
    return config;
  }, function (error) {
    return Promise.reject(error);
  });
  Axios.interceptors.response.use(function (response) {
    return response.data;
  }, function (error) {
    return Promise.reject(error);
  })
}

class HttpUtil extends Axios { }

export { HttpInit, HttpUtil };