package mmovie.mmovie.common;

import java.util.Base64;

public class Convert {

    /**
     * 인코더
     * */
    public String enCoder(String str){
        return Base64.getEncoder().encodeToString(str.getBytes());
    }

    /**
     * 디코더
     * */
    public String deCoder(String str){
        return new String(Base64.getDecoder().decode(str));
    }

}
