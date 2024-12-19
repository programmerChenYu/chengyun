const CheckTokenUtil = {
    check: (msg: string|undefined) => {
        if (String(msg).substring(0,8) === 'token 无效') {
            return false;
        } else if (String(msg).substring(0,9) === 'token 已过期') {
            return false;
        } else if (String(msg).substring(0,11) === 'token 已被顶下线') {
            return false;
        } else if (String(msg).substring(0,11) === 'token 已被踢下线') {
            return false;
        } else {
            return true;
        }
    }
}

export default CheckTokenUtil;
