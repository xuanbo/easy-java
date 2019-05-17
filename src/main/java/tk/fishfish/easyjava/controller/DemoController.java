package tk.fishfish.easyjava.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * demo Controller
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@RestController
public class DemoController {

    private static final Logger LOG = LoggerFactory.getLogger(DemoController.class);

    @GetMapping
    public ResponseEntity<String> echo() {
        return ResponseEntity.ok("easy java");
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestPart("file") MultipartFile file) {
        LOG.info("file name: {}", file.getOriginalFilename());
        LOG.info("file size: {}", file.getSize());
        // 下面是保存在本地
        InputStream in = null;
        try {
            Path target = Paths.get("./" + file.getOriginalFilename());
            in = file.getInputStream();
            Files.copy(in, target);
        } catch (IOException e) {
            LOG.error("save file error", e);
            return ResponseEntity.ok("false");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    LOG.warn("close inputStream error", e);
                }
            }
        }
        return ResponseEntity.ok("ok");
    }

    @GetMapping("/download")
    public ResponseEntity<?> download() {
        Path source = Paths.get("./readme.md");
        try {
            String name = source.toFile().getName();
            byte[] bytes = Files.readAllBytes(source);
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition", "attachment;filename=" + name);
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } catch (IOException e) {
            LOG.error("read file error", e);
            return ResponseEntity.ok("false");
        }
    }

}
