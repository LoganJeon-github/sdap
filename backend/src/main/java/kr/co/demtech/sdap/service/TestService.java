package kr.co.demtech.sdap.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.demtech.sdap.mapper.TestMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TestService {
  @Autowired
  private TestMapper fileMapper;

  public void test() {
    log.info("test란다");
    fileMapper.test();
  }

}
