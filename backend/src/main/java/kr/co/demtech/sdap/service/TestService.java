package kr.co.demtech.sdap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.demtech.sdap.mapper.TestMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TestService {
  @Autowired
  private TestMapper testMapper;

  public void test() {
    log.info("test란다");
    testMapper.test();
  }

  public void uploadFile(String fileName, byte[] files) {

    // byte[] test = files.getBytes();
    testMapper.uploadFile(fileName, files);
  }

  public String readCSV(String name) {
    var path = "/var/lib/postgresql/data/insertData/" + name;
    String file = testMapper.readCSV(path);
    return file;
  }

}
