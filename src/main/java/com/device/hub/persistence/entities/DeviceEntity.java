package com.device.hub.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "devices")
@Getter
@Setter
@ToString
public class DeviceEntity extends BaseEntity {

  @Column(name = "name")
  private String name;

  @Column(name = "brand")
  private String brand;

}
