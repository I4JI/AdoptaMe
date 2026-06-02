package mx.edu.unpa.apidroid.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UploadController {
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) throws IOException {
        String ruta=System.getProperty("user.dir")+"/src/main/resources/static/images/users/";
        //Verifica si existe directorio
        File directorio=new File(ruta);
        if(!directorio.exists()){
            directorio.mkdir();
        }
        String nombreOriginal=file.getOriginalFilename();
        String extension=nombreOriginal.substring(nombreOriginal.lastIndexOf("."));

        //Nombre unico a la foto
        String nombreFoto="MiFoto"+System.currentTimeMillis()+extension;

        File archivo=new File(ruta+nombreFoto);//Foto
        file.transferTo(archivo);// Guardar Foto
        return ResponseEntity.ok().body(ruta+nombreFoto);
    }
}
