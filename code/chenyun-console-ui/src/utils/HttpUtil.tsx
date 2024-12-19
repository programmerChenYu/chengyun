import axios from "axios";

// 设置请求的基址
const HttpUtil = axios.create({
    baseURL: 'http://127.0.0.1:8000',
    timeout: 10000,
})

// 拦截器
HttpUtil.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('cyToken');
        if (token) {
            config.headers['cyToken'] = 'Bearer ' + token;
        }
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
        console.log(error)
        return Promise.reject(error);
    }
)

export default HttpUtil;
