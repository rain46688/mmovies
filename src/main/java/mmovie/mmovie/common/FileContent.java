package mmovie.mmovie.common;

import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileContent {

    public byte[] convertFileToByte(MultipartFile mfile) throws Exception {
        File file = new File(mfile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(mfile.getBytes());

        byte[] returnValue = null;
        ByteArrayOutputStream baos = null;
        FileInputStream fis = null;

        try {

            baos = new ByteArrayOutputStream();
            fis = new FileInputStream(file);

            byte[] buf = new byte[1024];
            int read = 0;

            while ((read=fis.read(buf,0,buf.length)) != -1){
                baos.write(buf,0,read);
            }

            returnValue = baos.toByteArray();

        } catch (Exception e) {
            throw e;
        } finally {
            if (baos != null) {
                baos.close();
            }
            if (fis != null) {
                fis.close();
            }
        }

        fos.close();
        return returnValue;
    }

}
