package kr.co.demtech.sdap.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.demtech.sdap.domain.Folder;
import kr.co.demtech.sdap.mapper.FinderMapper;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FinderService {
  @Autowired
  private FinderMapper finderMapper;

  public List<Folder> getFolders(int parentId) {
    List<Folder> folders = finderMapper.getFolders(parentId);
    return folders;
  }

  public Folder FetchFolder(String name) {
    Folder folder = finderMapper.fetchFolder(name);

    return folder;
  }

  public void addFolder(String name, int parentId) {
    finderMapper.addFolder(name, parentId);
  }
}
