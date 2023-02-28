package kr.co.demtech.sdap.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.PathVariable;

import kr.co.demtech.sdap.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@RestController
@RequestMapping("/test")
@Slf4j
public class TestController {
  private final TestService testService;

  @PostMapping(value = "/upload")
  public ResponseEntity<Void> uploadFile(@RequestPart("files") MultipartFile files) {
    // TODO: process POST request

    try {
      byte[] bt = files.getBytes();
      testService.uploadFile(files.getOriginalFilename(), bt);
    } catch (Exception e) {
      log.info("Exception : {}", e);
    }

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/test")
  public ResponseEntity<HttpStatus> test() {

    testService.test();
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping("/read/{name}")
  public List<Object> readCSV(@PathVariable String name) {
    String files = testService.readCSV(name);
    var resultJson = convert_csv_to_json(files.split("\\n"));
    return resultJson;
  }

  private List<Object> convert_csv_to_json(String[] csvFile) {
    var resultObj = new ArrayList<>();
    String[] keys = csvFile[0].split(",");
    for (int rows = 1; rows < csvFile.length; rows++) {
      String[] row = csvFile[rows].split(",");
      var map = new HashMap<>();
      for (int index = 0; index < row.length; index++) {
        map.put(keys[index], row[index]);
      }
      resultObj.add(map);
    }
    return resultObj;
  }

}
