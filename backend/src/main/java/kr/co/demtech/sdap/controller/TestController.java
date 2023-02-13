package kr.co.demtech.sdap.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.demtech.sdap.service.TestService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class TestController {
  private final TestService testService;

  @PostMapping("/test")
  public ResponseEntity<HttpStatus> test() {

    testService.test();
    return new ResponseEntity<>(HttpStatus.OK);
  }
}
