package kr.co.demtech.sdap.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import kr.co.demtech.sdap.domain.Folder;

@Mapper
public interface FinderMapper {
  public List<Folder> getFolders(@Param("parentId") int parentId);
}
