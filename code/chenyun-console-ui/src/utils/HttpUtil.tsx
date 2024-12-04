import axios from "axios";

// 设置请求的基址
const HttpUtil = axios.create({
    baseURL: 'http://127.0.0.1:8000',
    timeout: 10000,
})

// 拦截器
HttpUtil.interceptors.request.use(
    (config) => {
        return config
    },
    (error) => {
        return Promise.reject(error);
    }
)

HttpUtil.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        return Promise.reject(error);
    }
)

export default HttpUtil;
