package tk.fishfish.easyjava.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * demo Controller
 *
 * @author 奔波儿灞
 * @since 1.0
 */
@RestController
public class DemoController {

    @GetMapping
    public ResponseEntity<String> echo() {
        return ResponseEntity.ok("easy java");
    }

}
