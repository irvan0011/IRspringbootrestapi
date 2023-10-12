package com.juaracoding.IRspringbootrestapi.service;

import com.juaracoding.IRspringbootrestapi.configuration.OtherConfiguration;
import com.juaracoding.IRspringbootrestapi.core.security.JwtUtility;
import com.juaracoding.IRspringbootrestapi.dto.UsrDTO;
import com.juaracoding.IRspringbootrestapi.handler.RequestCapture;
import com.juaracoding.IRspringbootrestapi.handler.ResponseHandler;
import com.juaracoding.IRspringbootrestapi.model.Menu;
import com.juaracoding.IRspringbootrestapi.model.Usr;
import com.juaracoding.IRspringbootrestapi.repo.LogRequestRepo;
import com.juaracoding.IRspringbootrestapi.repo.UsrRepo;
import com.juaracoding.IRspringbootrestapi.util.ExecuteSMTP;
import com.juaracoding.IRspringbootrestapi.util.LogTable;
import com.juaracoding.IRspringbootrestapi.util.LoggingFile;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional
public class UsrService implements UserDetailsService{

    private UsrRepo usrRepo;
    private JwtUtility jwtUtility;
    private String [] strExceptionArr = new String[2];
    @Autowired
    private LogRequestRepo logRequestRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UsrService(UsrRepo usrRepo, JwtUtility jwtUtility) {
        strExceptionArr[0] = "UsrService";
        this.usrRepo = usrRepo;
        this.jwtUtility = jwtUtility;
    }

    public ResponseEntity<Object> registrationUser(Usr usr, HttpServletRequest request){//RANGE 001-005
        try{
            if(usr==null)
            {
                return new ResponseHandler().generateResponse(
                        "Data tidak Valid",//message
                        HttpStatus.BAD_REQUEST,//httpstatus
                        null,//object
                        "FV-Auth001",
                        request
                );
            }
            Random random = new Random();
            Integer intToken = random.nextInt(100000,999999);
            String strToken = String.valueOf(intToken);
            Usr usrNext = null;//untuk penampungan jika proses update
            /*
                pengecekan untuk memastikan user registrasi pertama kali atau sudah pernah dan melakukan registrasi lagi
                tetapi belum selesai melakukan otentikasi verifikasi email
             */
            Optional<Usr> optionalUsr = usrRepo.findByUserNameAndIsActive(usr.getUserName(),false);
            if(optionalUsr.isEmpty())
            {
                /*
                    Jika user baru maka informasi nya akan langsung di save
                 */
                usr.setPassword(bCryptPasswordEncoder.encode(usr.getPassword()+OtherConfiguration.getFlagPwdTrap()));//encrypt password sebelum ke database
                usr.setToken(bCryptPasswordEncoder.encode(strToken));
                usrRepo.save(usr);
            } else {
                /*
                    Jika user sudah pernah registrasi tetapi gagal maka informasi sebelumnya akan ditiban
                    Proses update
                 */
                usrNext = optionalUsr.get();
                usrNext.setPassword(usr.getPassword());
                usrNext.setAlamat(usr.getAlamat());
                usrNext.setEmail(usr.getEmail());
                usrNext.setToken(bCryptPasswordEncoder.encode(strToken));
                usrNext.setNoHp(usr.getNoHp());
                usrNext.setTanggalLahir(usr.getTanggalLahir());
                usrNext.setModifiedBy(usrNext.getIdUser());
                usrNext.setModifiedDate(new Date());
            }
            String[] strVerify = new String[3];
            strVerify[0] = "Verifikasi Email";
            strVerify[1] = usr.getNamaLengkap();
            strVerify[2] = strToken;

            new ExecuteSMTP().sendSMTPToken(usr.getEmail(),"TOKEN Verifikasi Email",strVerify,"\\data\\ver_regis.html");
        }
        catch (Exception e)
        {
            strExceptionArr[1] = "registrationUser(Usr usr, HttpServletRequest request) --- LINE 69 \n"+ RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data Gagal Disimpan",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE-Auth001",//errorCode Fail Error modul-code 001 sequence 001 range 001 - 010
                    request
            );
        }

        return new ResponseHandler().generateResponse(
                "Registrasi Berhasil Diproses",//message
                HttpStatus.CREATED,//httpstatus created
                null,
                null,
                request
        );
    }

    public ResponseEntity<Object> regisVerification(String strUsername,String token, HttpServletRequest request){//RANGE 006-010
        try{
            if(strUsername==null)
            {
                return new ResponseHandler().generateResponse(
                        "Data tidak Valid",//message
                        HttpStatus.BAD_REQUEST,//httpstatus
                        null,//object
                        "FV-Auth006",
                        request
                );
            }
            Usr usrNext = null;//untuk penampungan jika proses update
            /*
                Mengambil informasi token dari user
             */
            Optional<Usr> optionalUsr = usrRepo.findByUserName(strUsername);
            if(optionalUsr.isEmpty())
            {
                /*
                    Validasi kiriman dari client salah
                 */
                return new ResponseHandler().generateResponse(
                        "Data tidak Valid",//message
                        HttpStatus.BAD_REQUEST,//httpstatus
                        null,//object
                        "FV-Auth007",
                        request
                );
            } else {
                usrNext = optionalUsr.get();
                if(bCryptPasswordEncoder.matches(token,usrNext.getToken())){
                    /*
                        Jika token valid maka kolom isActive user otomatis akan menjadi true
                     */
                    usrNext.setModifiedBy(usrNext.getIdUser());
                    usrNext.setModifiedDate(new Date());
                    usrNext.setActive(true);
                }else {
                    return new ResponseHandler().generateResponse(
                            "Token Tidak Valid",//message
                            HttpStatus.BAD_REQUEST,//httpstatus
                            null,//object
                            "FV-Auth008",
                            request
                    );
                }
            }
        }
        catch (Exception e)
        {
            strExceptionArr[1] = "regisVerification(String strUsername,String token, HttpServletRequest request) --- LINE 69 \n"+ RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data Gagal Disimpan",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE-Auth006",//errorCode Fail Error modul-code 001 sequence 006 range 006 - 010
                    request
            );
        }

        return new ResponseHandler().generateResponse(
                "Registrasi Berhasil",//message
                HttpStatus.CREATED,//httpstatus created
                null,
                null,
                request
        );
    }
    public ResponseEntity<Object> requestNewToken(String strUsername, HttpServletRequest request){//RANGE 011-015
        try{

            Optional<Usr> optionalUsr = usrRepo.findByUserName(strUsername);
            Usr usrNext = null;
            if(optionalUsr.isEmpty())
            {
                /*
                    Validasi kiriman dari client salah
                 */
                return new ResponseHandler().generateResponse(
                        "Data tidak Valid",//message
                        HttpStatus.BAD_REQUEST,//httpstatus
                        null,//object
                        "FV-Auth011",
                        request
                );
            } else {
                /*
                        Jika ingin ditambahkan counter untuk mencegah user melakukan refresh token berkali-kali
                        maka validasi nya ditambahkan pengecekan counter dan status banned (tidak dapat melakukan registrasi selama beberapa waktu)
                        otomatis status nya naik sebagai banned
                  */
                usrNext = optionalUsr.get();
                Random random = new Random();
                Integer intRandom = random.nextInt(100000,999999);
                String strToken = String.valueOf(intRandom);
                usrNext.setToken(bCryptPasswordEncoder.encode(strToken));
                usrNext.setModifiedBy(usrNext.getIdUser());
                usrNext.setModifiedDate(new Date());
                String[] strVerify = new String[3];
                strVerify[0] = "Permintaan TOKEN Baru";
                strVerify[1] = usrNext.getNamaLengkap();
                strVerify[2] = strToken;
                new ExecuteSMTP().sendSMTPToken(usrNext.getEmail(),"TOKEN Verifikasi Email",strVerify,"\\data\\ver_token_baru.html");
            }
        }
        catch (Exception e)
        {
            strExceptionArr[1] = "regisVerification(String strUsername,String token, HttpServletRequest request) --- LINE 69 \n"+ RequestCapture.allRequest(request);
            LoggingFile.exceptionStringz(strExceptionArr, e, OtherConfiguration.getFlagLoging());
            LogTable.inputLogRequest(logRequestRepo,strExceptionArr,e,OtherConfiguration.getFlagLogTable());
            return new ResponseHandler().generateResponse(
                    "Data Gagal Disimpan",//message
                    HttpStatus.INTERNAL_SERVER_ERROR,//httpstatus
                    null,//object
                    "FE-Auth011",//errorCode Fail Error modul-code 001 sequence 001 range 001 - 010
                    request
            );
        }

        return new ResponseHandler().generateResponse(
                "Token Baru Berhasil Dikirim",//message
                HttpStatus.CREATED,//httpstatus created
                null,
                null,
                request
        );
    }

    /*
        Fungsional untuk mengisi informasi User ke dalam Token
     */
    public ResponseEntity<Object> authManager(UsrDTO usrDTO, HttpServletRequest request)//RANGE 006-010
    {
        /*
            Untuk memasukkan user ke dalam
         */
        UserDetails userDetails = loadUserByUsername(usrDTO.getUserName());

        if(userDetails==null)
        {
            return new ResponseHandler().generateResponse(
                    "OTENTIKASI GAGAL",//message
                    HttpStatus.UNAUTHORIZED,//httpstatus
                    null,//object
                    "FV-Auth006",
                    request
            );
        }

        /*
            Isi apapun yang perlu diisi disini !!
         */
        Optional<Usr> usr = usrRepo.findByUserName(usrDTO.getUserName());
        Usr usrNext = usr.get();
        Map<String,Object> mapz = new HashMap<>();
        mapz.put("userId",usrNext.getIdUser());
        mapz.put("userName",usrNext.getUserName());
        mapz.put("email",usrNext.getNoHp());
        mapz.put("noHp",usrNext.getNoHp());
        mapz.put("role",usrNext.getAkses().getNamaAkses());
        List<Menu> listMenu = usrNext.getAkses().getListMenu();
        Integer intMenu = listMenu.size();
        String [] strMenu = new String[1];//mencegah agar tidak null
        strMenu[0] = "User Belum memiliki otorisasi untuk platform ini !!";
        Integer intCounter =0;
        if(intMenu!=0)
        {
            strMenu = new String[intMenu];
            for (Menu m :
                    usrNext.getAkses().getListMenu()){
                strMenu[intCounter++]=m.getNamaMenu();
            }

        }
        mapz.put("menuList",strMenu);

        String token =
                jwtUtility.generateToken(userDetails,mapz);

        return new ResponseHandler().generateResponse(
                "Otentikasi Berhasil",//message
                HttpStatus.OK,//httpstatus created
                token,//object
                null,//errorCode diisi null ketika data berhasil disimpan
                request
        );
    }

//    public Map<String,Object> checkAuthorization(String token,String modulName,Integer idxPrivilledge, HttpServletRequest request)
//    {
//        /*
//            Cek otorisasi berdasarkan informasi yang dikirim di JWT
//            lalu dinformasi diambil menjadi object Java Map
//         */
//        Map<String,Object> map =jwtUtility.getApiAuthorization(token,modulName,idxPrivilledge,new HashMap<String,Object>());
//
//        return map;
//    }
    public Map<String,Object> checkAuthorization(String token,String modulName,
                                                 HttpServletRequest request)
    {
        /*
            Cek otorisasi berdasarkan informasi yang dikirim di JWT
            lalu dinformasi diambil menjadi object Java Map
         */
//        Map<String,Object> map =jwtUtility.getApiAuthorization(token,modulName,idxPrivilledge,new HashMap<String,Object>());
        Map<String,Object> map =jwtUtility.getApiAuthorization(token,modulName,new HashMap<String,Object>());
        return map;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = null;
        Optional<Usr> optionalUsr = usrRepo.findByUserNameOrNoHpOrEmail(userName, userName, userName);

        if (optionalUsr.isEmpty()) {
            return user;
        }
        Usr usr = optionalUsr.get();
//        $2a$11$Owf6JAbkUwLBisLGnmmD8u41FRk/Hs5oEt2byIHz9ENOk00oqU4ii
        return new User(usr.getUserName(), usr.getPassword(), new ArrayList<>());
    }
}
