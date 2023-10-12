package com.juaracoding.IRspringbootrestapi.TEST;

import com.juaracoding.IRspringbootrestapi.util.ExecuteSMTP;

import java.util.Arrays;

public class TestSMTP {

    public static void main(String[] args) {
//        public Boolean sendSMTPToken(String mailAddress, String subject, String [] strVerification,String pathFile)

        ExecuteSMTP executeSMTP = new ExecuteSMTP();
        String [] strArr = {"irvanrepaldo.work@gmail.com","irvanrepaldo94@gmail.com","rewolreman94@gmail.com"};
        String [] StrArr={
                "Verifikasi1","Verifikasi2","Verifikasi3"
        };
        executeSMTP.sendSMTPToken(Arrays.toString(strArr), "Test SMTP",StrArr,"\\data\\ver_regis.html");
    }
}
