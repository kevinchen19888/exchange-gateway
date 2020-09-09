package com.alchemy.gateway.quotation.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 30分钟
 */
@Entity
@Table(name = "kline_30m")
public class Kline30min extends Kline {
}
