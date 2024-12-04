import {JSEncrypt} from "jsencrypt";
import PublicKey from "../assets/RSA/PublicKey.tsx";

const RsaEncryptUtil = {
    encrypt: (plaintext: string) => {
        const encrypt = new JSEncrypt();
        encrypt.setPublicKey(PublicKey.key);
        const encrypted = encrypt.encrypt(plaintext);
        return encrypted;
    }
}

export default RsaEncryptUtil;
