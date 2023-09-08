//package com.example.ttcn2etest.controller;
//
//import com.example.ttcn2etest.response.UploadResponse;
//import com.example.ttcn2etest.service.file.MinioAdapter;
//import io.minio.errors.*;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.http.codec.multipart.FilePart;
//import org.springframework.util.MimeTypeUtils;
//import org.springframework.web.bind.annotation.*;
//import reactor.core.publisher.Mono;
//
//import java.io.IOException;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//
//@RestController
//@RequestMapping("/file")
//public class FileController {
//    private final MinioAdapter adapter;
//
//    public FileController(MinioAdapter adapter) {
//        this.adapter = adapter;
//    }
//
//    @PostMapping(path = "/", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Mono<UploadResponse> upload(
//            @RequestPart(value = "files", required = true) Mono<FilePart> files) {
//        return adapter.uploadFile(files);
//    }
//
//    //upload file
//    @PostMapping(path = "/stream", produces = MimeTypeUtils.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public Mono<UploadResponse> uploadStream(
//            @RequestPart(value = "files", required = true) FilePart files, @RequestParam(value = "ttl", required = false) Integer ttl) {
//        return adapter.putObject(files);
//    }
//
//    @GetMapping(path = "/")
//    public ResponseEntity<Mono<InputStreamResource>> download(
//            @RequestParam(value = "filename") String fileName) {
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE).body(adapter.download(fileName));
//    }
//
//    //download file
//    @GetMapping(path = "/download")
//    public ResponseEntity<String> getUrlFileWithExpiredTime(
//            @RequestParam(value = "filename") String fileName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
//        return ResponseEntity.ok().body(adapter.getUrlFileWithExpiredTime(fileName));
//    }
//
//
//
//
//
//
//
//}
