package kr.co.demtech.sdap.domain;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class Folder {
  private int id;
  private String name;
  private int parentId;
  private Timestamp createdAt;
  private Timestamp modifiedAt;
  private int type;
}
