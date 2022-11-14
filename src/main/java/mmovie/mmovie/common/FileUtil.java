package mmovie.mmovie.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class FileUtil {

    /**
     * multipartFile을 File로 변환한다.
     */
    public File multipartFileToFile(MultipartFile file) throws IOException {

        createFolder(".\\contents");

        File convFile = new File(".\\contents\\"+file.getOriginalFilename());
        convFile.createNewFile();
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();

        return convFile;
    }

    /**
     * 파일 삭제 함수
     */
    public void deleteFile (String filepath){

        createFolder(filepath);
        
        File path = new File(filepath);
        File[] fileList = path.listFiles();

        for(int i=0; i<fileList.length; i++){
            if (fileList[i].delete()){
                log.info("삭제성공");
            }else{
                log.info("삭제실패");
            }
        }
    }

    /**
     * 폴더 생성 함수
     */
    public void createFolder(String filepath){
        File folder = new File(filepath);

        if(!folder.exists()){
            try {
                folder.mkdir();
                log.info("폴더 없어서 생성함");
            }catch (Exception e){
                log.info("폴더 생성 에러");
            }
        }else{
            log.info("이미 폴더가 생성되어있음");
        }
    }

}
