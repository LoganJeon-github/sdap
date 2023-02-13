package kr.co.demtech.sdap.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.co.demtech.sdap.service.FinderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import kr.co.demtech.sdap.domain.Folder;

@RestController
@RequestMapping("/folders")
@RequiredArgsConstructor
@Slf4j
public class FinderController {
  private final FinderService finderService;

  @GetMapping("/{parentId}")
  public List<Folder> getFolders(@PathVariable int parentId) {
    List<Folder> folders = finderService.getFolders(parentId);
    return folders;
  }
  
  @PostMapping("/add-folder")
  public ResponseEntity<HttpStatus> addFolder(@RequestBody Folder param){
    
    Folder folder = finderService.FetchFolder(param.getName());
    // if (folder == null){
      
    // }


    return new ResponseEntity<>(HttpStatus.CREATED);
  }

}
