package kr.co.demtech.sdap.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestMapper {
  public void test();

  public void uploadFile(@Param("fileName") String fileName,
      @Param("files") byte[] files);

  public String readCSV(@Param("name") String name);

  public String presto();
}
